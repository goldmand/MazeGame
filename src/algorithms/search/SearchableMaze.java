package algorithms.search;
import algorithms.mazeGenerators.Maze;
import java.util.*;

/**
 * SearchableMaze class implements ISearchable interface as an object adapter to a maze.
 * @author  Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public class SearchableMaze implements ISearchable{

     private Maze myMaze;

    /**
     * This method is a constructor for the SearchableMaze object.
     * @param m, Maze to be searched.
     */
     public SearchableMaze(Maze m){this.myMaze=m;}

    /**
     * This method is a getter for the Start state
     * @return AState, returns the start state (starting position of search in the maze)
     */
    @java.lang.Override
    public AState getStartState() {
         if(myMaze==null || myMaze.getStartPosition()==null || myMaze.getStartPosition().getRowIndex()<0 || myMaze.getStartPosition().getColumnIndex()<0)
             return null;
         String s = this.myMaze.getStartPosition().toString();
        return new MazeState(s.substring(1,s.length()-1));
    }

    /**
     * This method is a getter for the Goal state
     * @return AState, returns the goal state (exit position of search in the maze)
     */
    @java.lang.Override
    public AState getGoalState() {

        if(myMaze==null || myMaze.getGoalPosition()==null || myMaze.getGoalPosition().getColumnIndex()<0 || myMaze.getGoalPosition().getRowIndex()<0)
            return null;
        String s = this.myMaze.getGoalPosition().toString();
        return new MazeState(s.substring(1,s.length()-1));
     }

    /**
     * This method returns all the possible states reachable from the given parameter state s
     * @param s, the state which we check all possible states reachable from
     * @return ArrayList<AState>, a list of all the reachable states
     */
    public ArrayList<AState> getAllPossibleStates(AState s) {
         if (s == null)
             return null;
        ArrayList<AState> p = new ArrayList<AState>();
        MazeState ms = (MazeState)s;
        int row = ms.getMstate().getRowIndex();
        int col = ms.getMstate().getColumnIndex();
        boolean up = row - 1 >= 0;
        boolean down = row + 1 < myMaze.getNumofrows();
        boolean right = col + 1 < myMaze.getNumofcols();
        boolean left = col - 1 >= 0;

        if (col < myMaze.getNumofcols() && row < myMaze.getNumofrows() && (col >= 0) && (row >= 0)){
            //upper neighbour
            if  (up && myMaze.getCellValue(row - 1, col) == 0) {
                AState p2 = new MazeState(String.valueOf(row - 1) + "," + String.valueOf(col));
                p2.setCost(10);
                p.add(p2);
            }
            // right up neighbour
            if ((right && up && myMaze.getCellValue(row - 1, col + 1) == 0)
                    && (myMaze.getCellValue(row - 1, col) == 0 || myMaze.getCellValue(row, col + 1) == 0)) {
                AState p2 = new MazeState(String.valueOf(row - 1) + "," + String.valueOf(col + 1));
                p2.setCost(15);
                p.add(p2);
            }
            //right neighbor
            if (right && myMaze.getCellValue(row, col + 1) == 0) {
                AState p2 = new MazeState(String.valueOf(row) + "," + String.valueOf(col + 1));
                p2.setCost(10);
                p.add(p2);
            }
            // right down neighbour
            if ((right && down && myMaze.getCellValue(row + 1, col + 1) == 0)
                    && (myMaze.getCellValue(row + 1, col) == 0 || myMaze.getCellValue(row, col + 1) == 0)) {
                AState p2 = new MazeState(String.valueOf(row + 1) + "," + String.valueOf(col + 1));
                p2.setCost(15);
                p.add(p2);
            }
            //down neighbor
            if (down && myMaze.getCellValue(row + 1, col) == 0) {
                AState p2 = new MazeState(String.valueOf(row + 1) + "," + String.valueOf(col));
                p2.setCost(10);
                p.add(p2);
            }
            // left down neighbour
            if ((left && down && myMaze.getCellValue(row + 1, col - 1) == 0)
                    && (myMaze.getCellValue(row + 1, col) == 0 || myMaze.getCellValue(row, col - 1) == 0)) {
                AState p2 = new MazeState(String.valueOf(row + 1) + "," + String.valueOf(col - 1));
                p2.setCost(15);
                p.add(p2);
            }
            // left neighbor
            if (left && myMaze.getCellValue(row, col - 1) == 0) {
                AState p2 = new MazeState(String.valueOf(row) + "," + String.valueOf(col - 1));
                p2.setCost(10);
                p.add(p2);
            }
            // left up neighbour
            if ((left && up && myMaze.getCellValue(row - 1, col - 1) == 0)
                    && (myMaze.getCellValue(row - 1, col) == 0 || myMaze.getCellValue(row, col - 1) == 0)) {
                AState p2 = new MazeState(String.valueOf(row - 1) + "," + String.valueOf(col - 1));
                p2.setCost(15);
                p.add(p2);
            }
        }
        return p;
    }
}
