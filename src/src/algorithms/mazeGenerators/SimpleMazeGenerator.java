package algorithms.mazeGenerators;
import java.util.Random;
/**
 * The SimpleMazeGenerator class implements an abstract MazeGenerator that
 * generates a simple randomized maze.
 *
 * @author  Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    /**
     * This method is used for generating a maze.
     * @param rows This is the first parameter to generate method.
     * @param cols  This is the second parameter to generate method.
     * @return Maze The method returns a simple maze that its dimensions are rows*cols.
     */
    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 0 || cols <= 0)
            return null;

        Random random = new Random();
        Position startP = new Position(random.nextInt(rows), random.nextInt(cols));
        Position endP = new Position(random.nextInt(rows), random.nextInt(cols));
        Maze m = new Maze(rows, cols, startP, endP);
        // Adding random cells to the maze:
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int random1 = random.nextInt(2);
                m.setCellValue(y, x, random1);
            }
        }
        // Make sure that the Start and Goal positions are in the frame and not in the same frame.
        m.SetStartandGoal(0);
        // Making a path from Start to Goal.
        m.MakePath();
        return m;
    }
}


