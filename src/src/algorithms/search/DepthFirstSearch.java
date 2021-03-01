package algorithms.search;
import java.util.*;

/**
 * DepthFirstSearch class implements ASearchingAlgorithm for searching by the Depth First Search known algorithm.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since 2020-15-03
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() { }

    /**
     * This method is a getter for the search name.
     * @return the name of the search in a String format.
     */
    public String getName() { return "DepthFirstSearch"; }

    /**
     * This method solves and finds the shortest path from the starting state of the
     * searchable platform to its goal state using DFS algorithm.
     * @param s instance of searchable platform
     * @return Solution, solution for the parameter s.
     */
    public Solution solve(ISearchable s) {

        Solution sol = new Solution();
        if (s == null || s.getGoalState()==null || s.getStartState()==null)
            return sol;

        // Create a stack for DFS
        Stack<AState> stack = new Stack<AState>();
        // visited nodes
        setNumberOfVisitedNodes(0);
        HashMap<String,AState> visited = new HashMap<>();
        //init
        AState startS = s.getStartState();
        AState endS = s.getGoalState();
        sol.insertToSolution(startS);
        stack.push(startS);
        AState curr = startS;
        boolean solved = false;

        while (!stack.isEmpty()){
            curr = stack.pop();
            // Stack may contain same vertex twice. So
            // we need to print the popped item only
            // if it is not visited.
            if (!visited.containsKey(curr.toString())){
                visited.put(curr.toString(),curr);
                UpNumberOfVisitedNodes();
            }

            if (curr.equals(endS)) {// if found the end position, were done and curr holds the goal state
                solved = true;
                break;
            }
            // Get all adjacent vertices of the popped vertex s
            // If a adjacent has not been visited, then push it
            // to the stack.
            ArrayList<AState> neighbours = s.getAllPossibleStates(curr);
            for (int i = 0; i< neighbours.size(); i++) {
                if (!visited.containsKey(neighbours.get(i).toString())){
                    visited.put(neighbours.get(i).toString(),neighbours.get(i));
                    UpNumberOfVisitedNodes();
                    neighbours.get(i).setCameFrom(curr);
                    stack.add(neighbours.get(i));
                }
            }

        }
        //cases that maze does't have solution
        if(!solved){
            //return empty solution
            return new Solution();
        }
        sol =  getSolfromCurr(curr);
        return sol;
    }

}
