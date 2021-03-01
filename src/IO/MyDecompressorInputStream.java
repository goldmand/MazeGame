package IO;
import algorithms.mazeGenerators.Maze;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class MyDecompressorInputStream extends the java InputStream class.
 * the main function of the class is to decompress a byte array to a maze representation.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;


    public MyDecompressorInputStream(InputStream in) {
        super();
        this.in = in;
    }

    @Override
    public int read() {
        return 0;
    }

    /**
     * This method is used to decompress a byte array to a byte array that represents a maze.
     * the first 12 bytes of the byte array represent the maze data (info in Maze class)
     * each next byte represents the decimal value of the next 8 bits of the maze.
     * the decompressed byte array is written to the input stream.
     */
    @Override
    public int read(byte[] b) {
        if (b == null)
            return 0;
        byte[] c = null;
        try {
            c = new byte[b.length];
            this.in.read(c);
            System.arraycopy(c,0,b,0,12);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rows = (Byte.toUnsignedInt(c[0])*255) + Byte.toUnsignedInt(c[1]);
        int cols = (Byte.toUnsignedInt(c[2])*255) + Byte.toUnsignedInt(c[3]);

        int compressed_size = 12 + (((rows*cols)) / 8);

        if ((rows*cols) % 8 != 0)
            compressed_size++;

        int index_to_insert = 12;

        for (int i = 12; i < compressed_size; i++) {
            int temp = Byte.toUnsignedInt(c[i]); // for example temp is 31
            String binary_temp_rep = Integer.toBinaryString(temp);// now temp is 111111
            // building the correct string represenation for the the decimal number, 31 will be 0111111 and not 111111
            if(((rows*cols)%8==0) || (i != compressed_size-1)){
                while (binary_temp_rep.length() < 8)
                    binary_temp_rep = "0" + binary_temp_rep; // now will become to 0111111
            }
            else{
                while (binary_temp_rep.length() < (rows*cols)%8)
                    binary_temp_rep = "0" + binary_temp_rep;
            }

            for (int j = 0; j < binary_temp_rep.length(); j++) {
                String temp_char_as_string = Character.toString(binary_temp_rep.charAt(j));
                byte temp_byte = (byte) Integer.parseInt(temp_char_as_string);
                try {
                    b[index_to_insert] = temp_byte;
                    index_to_insert++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;
    }
}