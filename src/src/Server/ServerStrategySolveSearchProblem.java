package Server;


import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;
/**
 * The class ServerStrategySolveSearchProblem that implements IServerStrategy.
 * handles clients requests of solving a maze.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private static int count = 0;
    /**
     * This function handles a client request to solve a maze.
     * @param inputStream - The maze to solve.
     * @param outputStream - The output of the maze Solution.
     */
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {
        ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
        ObjectInputStream fromClient = new ObjectInputStream(inputStream);

        Maze maze = (Maze) fromClient.readObject();

        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        final String solved_mazes = "Solved_mazes";
        CreateDirectory(solved_mazes);
        final String solutions = "Solutions";
        CreateDirectory(solutions);

        final File maze_file = new File(tempDirectoryPath+"/Solved_mazes"); //holds all mazes files
        final File sol_file = new File(tempDirectoryPath+"/Solutions");

        Solution sol = GetSolution(maze, maze_file, sol_file);

            //if didnt find identical maze, write the maze and the solution TOGETHER!
            if (sol == null) {
                sol = (Configurations.getSearchingAlgorithm()).solve(new SearchableMaze(maze));
                WriteMazeAndSolToFile(maze, maze_file, sol_file); // write maze and sol to files
            }
                try {
                    toClient.writeObject(sol);
                    toClient.flush();
                    toClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    /**
     * This function writes to the files the compressed maze and the solution
     * @param maze - The maze to solve.
     * @param mazeFile - The maze file to write to.
     * @param solFile - The Solution file to write to.
     */
    public void WriteMazeAndSolToFile(Maze maze, File mazeFile, File solFile) throws IOException {
        Solution solution = (Configurations.getSearchingAlgorithm()).solve(new SearchableMaze(maze));
        String temp_maze_file_name = mazeFile.getPath()+count;
        String temp_sol_file_name = solFile.getPath()+count;
        count++;
        File temp_maze_file= new File(temp_maze_file_name);
        File temp_sol_file= new File(temp_sol_file_name);

        ObjectOutputStream write_solution = new ObjectOutputStream(new FileOutputStream(temp_sol_file.getPath()));
        MyCompressorOutputStream write_mazes= new MyCompressorOutputStream(new FileOutputStream(temp_maze_file.getPath()));
        write_mazes.write(maze.toByteArray());
        write_mazes.flush();
        write_solution.writeObject(solution);
        write_solution.flush();
        write_mazes.close();
        write_solution.close();
    }

    /**
     * This function searches if the maze has been solved before.
     * @param maze - The maze to solve.
     * @param mazeFile - The maze file to search from.
     * @param solFile - The Solution file return from.
     * @return Solution of the maze or null if not found.
     */
    public Solution GetSolution(Maze maze, File mazeFile, File solFile) throws IOException, ClassNotFoundException {
        ArrayList<File> check_maze_list = new ArrayList<File>();
        ArrayList<File> check_sol_list = new ArrayList<File>();

        File[] maze_list = mazeFile.listFiles(); //list of all the mazes that are in the file argument
        for (int i = 0; i < maze_list.length; i++)
            check_maze_list.add(maze_list[i]);

        for (int i = 0; i < check_maze_list.size(); i++) {
            ObjectInputStream readMazeObject = new ObjectInputStream(new FileInputStream(check_maze_list.get(i).getPath()));
            byte[] m = (byte[]) readMazeObject.readObject();
            readMazeObject.close();
            //read Object does not return null at end of stream
            if (maze.equals(new Maze(m))) { // need to check if works
                File[] sol_list = solFile.listFiles();
                for (int j = 0; j < sol_list.length; j++)
                    check_sol_list.add(sol_list[i]);
                ObjectInputStream readSolutions = new ObjectInputStream(new FileInputStream(check_sol_list.get(i).getPath()));
                Solution sol = (Solution) readSolutions.readObject();
                readSolutions.close();
                return sol;
            }
        }
        return null;
    }

    /**
     * This function is used to create directories.
     * @param name - the directory name
     */
    public void CreateDirectory(final String name){
        final File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        final File dir = new File(tmpdir, name);
        if (!dir.exists() && !dir.mkdirs())
            System.out.println("Failed to create directory.");
    }
}
