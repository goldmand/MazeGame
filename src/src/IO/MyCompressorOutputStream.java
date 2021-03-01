package IO;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

/**
 * The class MyCompressorOutputStream extends the java OutputStream class.
 * the main function of the class is to compress a maze representation.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream o){
        this.out=o; 
    }

    @Override
    public void write(int b) throws IOException {this.out.write(b);}


    @Override
    public void write(byte[] b) throws IOException {
        /**
         * This method is used to compress a byte array representation of a maze.
         * the first 12 bytes of the byte array represent the maze data (info in Maze class)
         * each next byte represents the decimal value of the next 8 bits of the maze.
         * the compressed byte array is writen to the output stream.
         */
        int rows = (Byte.toUnsignedInt(b[0])*255) + Byte.toUnsignedInt(b[1]);
        int cols = (Byte.toUnsignedInt(b[2])*255) + Byte.toUnsignedInt(b[3]);
        int compressed_size = 12 + (((rows*cols)) / 8);
        if ((rows*cols) % 8 != 0)
            compressed_size++;

        byte[] to_return = new byte[compressed_size];
        System.arraycopy(b,0,to_return,0,12);
        String s1="";
        int j=0;
        int index=12;
        //go through the body of the maze
        for(int i=12; i<b.length; i++){
            //reached end of chunk
            if(j==8){
                byte temp=(byte)Integer.parseInt(s1,2);
                to_return[index]=temp;
                index++;
                s1=Integer.toString(b[i]);
                j=1;
                if(i==b.length-1){
                    //s1+=Integer.toString(b[i]);
                    byte temp1=(byte)Integer.parseInt(s1,2);
                    to_return[index]=temp1;
                }
            }
            else if(j != 8 && i==b.length-1){ //remaining in case doesn't divide in 7 completely
                s1+=Integer.toString(b[i]);
                byte temp=(byte)Integer.parseInt(s1,2); //turns from binary string to byte(0-127)
                to_return[index]=temp;
            }
            else{
                s1+=Integer.toString(b[i]);
                j++;
            }
        }
            try{
            if(this.out instanceof ObjectOutputStream){
                ObjectOutputStream o=(ObjectOutputStream)this.out;
                o.writeObject(to_return);
            }
            else {
                out.write(to_return);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
