package algorithms.mazeGenerators;

/**
 * EmptyMazeGenerator class implements an abstract MazeGenerator that generates an empty maze with no walls or cells.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public class EmptyMazeGenerator extends AMazeGenerator {
    /**
     * This method is used for generating a maze.
     * @return Maze, returns empty maze.
     */
    @Override
    public Maze generate(int rows, int cols) {
        Maze newMaze=new Maze(0,0,new Position(),new Position());
        return newMaze;
    }

}
