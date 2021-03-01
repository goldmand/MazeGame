package algorithms.search;

/**
 * ISearchingAlgorithm is an interface for any searching algorithm with standard searching actions
 * @author  Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public interface ISearchingAlgorithm {
    /**
     * This method solves the maze, finds the best path from the starting position to the
     * end position according to the searching algorithm
     * @param s , the instance to search on
     * @return Solution, the solution path to the s Parameter
     */
    Solution solve(ISearchable s);

    /**
     * This method is a getter for the search name.
     * @return the name of the search in a String format.
     */
    String getName();

    /**
     * This method returns the amount of nodes visited during the search.
     * @return String, the value field "NumberOfVisitedNodes" converted to String format.
     */
    String getNumberOfNodesEvaluated();

}
