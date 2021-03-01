package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
/**
 * The class ServerStrategyGenerateMaze that implements IServerStrategy.
 * handles clients requests of generating a maze and compressing it.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    /**
     * This function handles a client request to generate a compressed maze.
     * @param inputStream - The input of maze dimensions.
     * @param outputStream - The output of the compressed maze.
     */
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
        ObjectInputStream fromClient = new ObjectInputStream(inputStream);

        int[] dim = (int[])fromClient.readObject();

        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(dim[0], dim[1]); //Generate new maze
        try {
            OutputStream send_compressed = new MyCompressorOutputStream(toClient);
            byte[] maze_rep=maze.toByteArray();
            send_compressed.write(maze_rep);
            send_compressed.flush();
            send_compressed.close();
            toClient.flush();
            toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
