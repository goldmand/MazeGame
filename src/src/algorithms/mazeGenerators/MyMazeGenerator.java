package algorithms.mazeGenerators;
import java.util.*;
/**
 * The MyMazeGenerator class implements an abstract MazeGenerator that
 * generates a maze using Prim Randomized algorithm.
 *
 * @author  Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class MyMazeGenerator extends AMazeGenerator {
    /**
     * This method is used for generating a maze.
     * @param rows This is the first parameter to generate method.
     * @param cols  This is the second parameter to generate method.
     * @return Maze The method returns a complex maze that its dimensions are rows*cols.
     */
    @Override
    public Maze generate(int rows, int cols){
        if (rows <=0 || cols <=0)
            return null;

        Random random = new Random();
        Position startP = new Position(random.nextInt(rows), random.nextInt(cols));
        Position endP = new Position(random.nextInt(rows), random.nextInt(cols));
        Maze m = new Maze(rows, cols, startP, endP);

        //Start with a grid full of walls:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.setCellValue(i, j, 1);
            }
        }
        //Pick a random cell and add its walls to a List.
         LinkedList<int[]> frontiers = new LinkedList<>();
        int x = random.nextInt(rows);
        int y = random.nextInt(cols);
        frontiers.add(new int[]{x,y,x,y});
        //Pick a random wall from the List:
        while ( !frontiers.isEmpty() ){
            // Remove the wall from the list:
            int[] f = frontiers.remove( random.nextInt( frontiers.size() ) );
            x = f[2];
            y = f[3];
            // If the cell on the opposite side isn't in the maze yet:
            if (m.getCellValue(x,y)==1 )
            { // Make the wall a passage and mark the cell on the opposite side as part of the maze:
                m.setCellValue(f[0],f[1],0);
                m.setCellValue(x,y,0);
                // Add the neighboring walls of the cell to the wall list:
                if ( x >= 2 && m.getCellValue(x-2,y)==1 )
                    frontiers.add( new int[]{x-1,y,x-2,y} );
                if ( y >= 2 && m.getCellValue(x,y-2)==1 )
                    frontiers.add( new int[]{x,y-1,x,y-2} );
                if ( x < rows-2 && m.getCellValue(x+2,y)==1)
                    frontiers.add( new int[]{x+1,y,x+2,y} );
                if ( y < cols-2 && m.getCellValue(x,y+2)==1 )
                    frontiers.add( new int[]{x,y+1,x,y+2} );
            }
        }
        // Make sure that the Start and Goal positions are in the frame and not in the same frame.
        // And that there is at list 1 move to each of them.
        m.SetStartandGoal(1);

     return m;
    }
}