package algorithms.search;
import java.util.*;

/**
 *ISearchable interface represents a searchable platform to conduct search on (Maze, Graph, etc..)
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since 2020-15-03
 */

public interface ISearchable {

    /**
     * This method is a getter for the Start state
     * @return AState, returns the start state of the searchable platform
     */
    AState getStartState();

    /**
     * This method is a getter for the Goal state
     * @return AState, returns the end state of the searchable platform
     */
    AState getGoalState();

    /**
     * This method returns all the possible states reachable from the given parameter state s
     * @param s, state to search available states from
     * @return ArrayList<AState>, all states reachable.
     */
    ArrayList<AState> getAllPossibleStates(AState s);
}
