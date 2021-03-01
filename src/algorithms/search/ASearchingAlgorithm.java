package algorithms.search;

/**
 *The ASearchingAlgorithm abstract class of searching algorithms that implements an interface for general searching algorithem.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    private int NumberOfVisitedNodes;
    private boolean priority;

    /**
     * This method set if the algorithm that extends this class will have priority for the moves
     * @param  priority value to be set to the field priority
     */
    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    /**
     * @return the boolean value in field priority
     */
    public boolean isPriority() {
        return priority;
    }

    /**
     * This method sets a new value into the field "NumberOfVisitedNodes" that holds the amount of node visited during the search.
     * @param numberOfVisitedNodes the new value to be set.
     */
    public void setNumberOfVisitedNodes(int numberOfVisitedNodes) {
        NumberOfVisitedNodes = numberOfVisitedNodes;
    }

    /**
     * This method increases the number of visited nodes by 1.
     */
    public void UpNumberOfVisitedNodes() {
        NumberOfVisitedNodes++;
    }

    /**
     * This method is implmented at every searching algorithm independently
     * @param s instance of searchable platform
     * @return Solution the solution for the given searchable platform
     */
    public Solution solve(ISearchable s) {
        return null;
    }

    /**
     * This method returns the name of the searching algorithm, implmented at every searching algorithm independently.
     * @return String, the searching algorithm name.
     */
    public String getName(){return null;}

    /**
     * This method returns the amount of nodes visited during the search.
     * @return String, the value field "NumberOfVisitedNodes" converted to String format.
     */
    public String getNumberOfNodesEvaluated(){return Integer.toString(NumberOfVisitedNodes);}

    /**
     * This method retrieves the solution from a given state.
     * @param curr an Astate that represents the goal position (if the maze has a solution), random position otherwise.
     * @return Solution, returns the solution by tracing back the current state to its predecessor.
     */
    public Solution getSolfromCurr(AState curr) {
        if(curr==null)
            return null;
        Solution sol = new Solution();
        while (curr != null) {
            sol.insertToSolution(curr);
            curr = curr.getCameFrom();
        }
        return sol;
    }
}
