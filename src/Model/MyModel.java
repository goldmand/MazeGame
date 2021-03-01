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
import javafx.stage.FileChooser;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * this model class implements the IModel interface and creates a functioning model as part
 * of the MVVM architecture
 * @authors: Daniel Goldman and Dor Levy
 */
public class MyModel extends Observable implements IModel {

    private Server MazeGeneratingServer;
    private Server SolveProblemServer;

    private Maze maze;
    private Solution sol;

    private int player_row;
    private int player_col;

    /**
     * this function returns the player current row number
     * @return int, the player current row
     */

    public int getPlayer_row() {
        return player_row;
    }


    /**
     * this function returns the player current column number
     * @return int, the player current column
     */
    public int getPlayer_col() {
        return player_col;
    }


    /**
     * this function sets the player current row to the given parameter
     * @param player_row, the row number to be set as the player current row
     */

    public void setPlayer_row(int player_row) {
        this.player_row = player_row;
    }

    /**
     * this function sets the player current column to the given parameter
     * @param player_col, the row number to be set as the player current column
     */

    public void setPlayer_col(int player_col) {
        this.player_col = player_col;
    }

    boolean Servers = false;

    /**
     * this function initilizies the class server and activates them upon establishing connection
     * and creating a new instance of the game
     */
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

    /**
     * @return the current instance of the board game
     */
    public Maze getMaze() {
        return maze;
    }
    /**
     * @return the solution for the current instance of the board game
     */
    public Solution getSol() {
        return sol;
    }

    /**
     * this functions returns true if the solution path of the
     * @paramter sol contains the designated position (i,j), false otherwise
     * @param sol, certain solution for a given board game
     * @param i, the row index to be checked
     * @param j, the col index to be checked
     * @return bool, true if in the given solution contains the position in the maze of (i,j)
     */
    public boolean inSol(Solution sol, int i, int j){
        return sol.getSolutionPath().contains(new MazeState(i+","+ j));
    }

    /**
     * this functions generates a new maze using the class servers according to prim Algorithm
     * @param rows, Maze width
     * @param cols, Maze Length
     * @throws UnknownHostException in case of an unvalid host
     */

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

    /**
     * this  function stop the servers upon closing connection
     */
    @Override
    public void stopServers(){
        if (Servers) {
            this.MazeGeneratingServer.stop();
            this.SolveProblemServer.stop();
        }
    }

    /**
     * this functions generates a solution for the current instance of the board game in the class
     */
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

    /**
     * returns the value at the cell index (i,j) of the given board game m
     * @param i the row of the desired cell
     * @param j the column of the desired cell
     * @param m the instance of the board game to retrieve the cell value from
     * @return int, the value that the (i,j) cell holds
     */
    @Override
    public int getCellValue(int i,int j, Maze m){ return m.getCellValue(i,j); }

    /**
     * returns the row of the goalPosition
     * @param m,the instance of the board game to retrieve the goalPosition row value from
     * @return int, the row of the goalPosition
     */
    @Override
    public int getGoalRow(Maze m) {
        return m.getGoalPosition().getRowIndex();
    }

    /**
     * returns the column of the goalPosition
     * @param m,the instance of the board game to retrieve the goalPosition column value from
     * @return int, the column of the goalPosition
     */

    @Override
    public int getGoalCol(Maze m) {
        return m.getGoalPosition().getColumnIndex();
    }

    /**
     * returns the row of the startPosition
     * @param m, the instance of the board game to retrieve the startPosition row value from
     * @return int, the row of the startPosition
     */
    @Override
    public int getStartRow(Maze m) {
        return m.getStartPosition().getRowIndex();
    }

    /**
     * returns the column of the startPosition
     * @param m, the instance of the board game to retrieve the startPosition column value from
     * @return int, the column of the startPosition
     */
    @Override
    public int getStartCol(Maze m) {
        return m.getStartPosition().getColumnIndex();
    }

    /**
     * returns the number of rows in the current instance of the board game
     * @param m, the instance of the board game to retrieve the row number value from
     * @return int, number of rows of maze
     */
    @Override
    public int getRows(Maze m) {
        return m.getNumofrows();
    }

    /**
     * returns the number of columns in the current instance of the board game
     * @param m, the instance of the board game to retrieve the row number value from
     * @return int, number of columns of maze
     */
    @Override
    public int getCols(Maze m) {
        return m.getNumofcols();
    }

    /**
     *  this functions checks the desired cell (i,j) and returns the values according to to the cell content
     * @param m, the instance of the board game to make move on
     * @param row, the row index to check move on
     * @param col, the column index to check move on
     * @return int, 1 if the cell (i,j) is the goalPosition
     *              2, if the cell is not a wall (able to move to it)
     *              -1, if the cell is a wall(uable to move to it)
     */
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

    /**
     * this function save the current instance of the board game to a new File file
     * using ObjectOutputStream
     * enables retrieving the saved instance later on
     */

    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(null);

        if(file!=null){
            try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(file))){
                ob.writeObject(maze);
                ob.writeObject(player_row);
                ob.writeObject(player_col);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this functions reads a saved instance from the File MAZE files and
     * loads its to the current board the application is running
     * using ObjectInputStream
     * @throws IOException in case file not found
     * @throws ClassNotFoundException in case the imported class not found
     */

    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try(ObjectInputStream ob = new ObjectInputStream(new FileInputStream(file))){ ;
                maze = (Maze) ob.readObject();
                player_row = (int)ob.readObject();
                player_col = (int)ob.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
