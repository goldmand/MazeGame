package algorithms.mazeGenerators;

/**
 * IMazeGenerator interface has two methods for generating a maze and measuring the running time for generating the maze.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public interface IMazeGenerator {
     /**
      * This method generates a maze, each generating algorithm independently.
      * @param rows number of rows of the maze
      * @param cols number of columns of the maze
      * @return Maze, the generated maze.
      */
     Maze generate(int rows, int cols);
     /**
      * This method measures the time it takes for any generating algorithm to generate a maze according to the parameters.
      * @param rows number of rows in the maze
      * @param cols number of cols in the mze
      * @return long, time in ms to generate the maze.
      */
     long measureAlgorithmTimeMillis(int rows, int cols);
}
