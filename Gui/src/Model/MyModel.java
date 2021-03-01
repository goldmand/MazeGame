package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Observable implements IModel {

    private Server MazeGeneratingServer;
    private Server SolveProblemServer;

    private Maze maze;
    private Solution sol;

    boolean Servers = false;

    @Override
    public void startServers(){
        if (!Servers) {
            this.MazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
            this.SolveProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
            this.maze = null;
            this.sol = null;
            MazeGeneratingServer.start();
            SolveProblemServer.start();
            Servers = true;
        }
    }

    public Maze getMaze() {
        return maze;
    }

    public Solution getSol() {
        return sol;
    }

    public boolean inSol(Solution sol, int i, int j){
        return sol.getSolutionPath().contains(new MazeState(i+","+ j));
    }

    public void GenerateMaze(int rows, int cols) throws UnknownHostException {
        try {
            Client client= new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
                        ObjectInputStream fromServer = new ObjectInputStream(inputStream);

                        toServer.flush();
                        //what does the server gets?
                        int[] maze_size= new int[]{rows,cols};
                        toServer.writeObject(maze_size);
                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] deCompreesedMaze=new byte[13+(rows*cols)];
                        is.read(deCompreesedMaze);
                        maze=new Maze(deCompreesedMaze);


                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
        });
        client.communicateWithServer();
        }catch(Exception e){}
    }

    @Override
    public void stopServers(){
        if (Servers) {
            this.MazeGeneratingServer.stop();
            this.SolveProblemServer.stop();
        }
    }


    @Override
    public void GenerateSolution() {
        try{
            Client client=new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    ObjectOutputStream toServer = null;
                    try {
                        toServer = new ObjectOutputStream(outputStream);
                        ObjectInputStream fromServer = new ObjectInputStream(inputStream);
                        toServer.writeObject(maze);
                        toServer.flush();
                        //MyMazeGenerator mg= new MyMazeGenerator();
                         sol=(Solution)fromServer.readObject();

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
            }
            catch (Exception e){}
    }
    @Override
    public int getCellValue(int i,int j, Maze m){ return m.getCellValue(i,j); }

    @Override
    public int getGoalRow(Maze m) {
        return m.getGoalPosition().getRowIndex();
    }

    @Override
    public int getGoalCol(Maze m) {
        return m.getGoalPosition().getColumnIndex();
    }

    @Override
    public int getStartRow(Maze m) {
        return m.getStartPosition().getRowIndex();
    }

    @Override
    public int getStartCol(Maze m) {
        return m.getStartPosition().getColumnIndex();
    }

    @Override
    public int getRows(Maze m) {
        return m.getNumofrows();
    }

    @Override
    public int getCols(Maze m) {
        return m.getNumofcols();
    }

    public int MakeMove(Maze m, int row, int col) {
        try {

            int p = m.getCellValue(row, col);
            if (m.getGoalPosition().getRowIndex() == row && m.getGoalPosition().getColumnIndex() == col) {
                return 1;
            }
            if (p == 0) {
                return 0;
            } else if (p == 1) {
                return -1;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            return -1;
        }
        return 0;
    }
}
