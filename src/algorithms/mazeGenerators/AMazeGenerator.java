package algorithms.mazeGenerators;

/**
 * the AMazeGenerator abstract class implements one method from an interface used for generating maze and measuring running time.
 * generate method is implemented on every independent mazeGenerator.
 *@author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     * This method is used to measure the running time of generating a maze.
     * @param rows number of rows of the generated maze.
     * @param cols number of columns of the generated maze.
     * @return long, amount of time taken to generate the maze.
     */
    public long measureAlgorithmTimeMillis(int rows, int cols){
        long before=System.currentTimeMillis();
        Maze maze=generate(rows,cols);
        long after=System.currentTimeMillis();
        return after-before;
    }
}
