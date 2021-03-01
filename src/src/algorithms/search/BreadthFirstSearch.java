package algorithms.search;
import java.util.*;

/**
 * BreadthFirstSearch class implements ASearchingAlgorithm for searching by the Breadth First Search known algorithm.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since 2020-15-03
 */
public class BreadthFirstSearch extends ASearchingAlgorithm{

    /**
     * Default constructor that sets Priority to false.
     */
    public BreadthFirstSearch() { setPriority(false); }

    /**
     * This method is a getter for the search name.
     * @return the name of the search in a String format.
     */
    public String getName(){return "BreadthFirstSearch";}

    /**
     * This method solves and finds the shortest path from the starting state of the
     * searchable platform to its goal state using BFS algorithm.
     * @param s instance of searchable platform
     * @return Solution, solution for the parameter s.
     */
    public Solution solve(ISearchable s) {

        Solution sol = new Solution();
        if (s == null || s.getGoalState()==null || s.getStartState()==null)
            return sol;

        // Queue for BFS
        Queue<AState> q;
        if (isPriority()){
            q = new PriorityQueue<>(Comparator.comparingDouble((AState st) -> st.getCost()));
        }
        else {
            q = new LinkedList<>();
        }
        // visited nodes
        setNumberOfVisitedNodes(0);
        HashMap<String,AState> visited = new HashMap<>();
        //init
        AState startS = s.getStartState();
        AState endS = s.getGoalState();
        sol.insertToSolution(startS);
        q.add(startS);
        AState curr = startS;
        boolean solved = false;

        while(!q.isEmpty()){

            curr=q.remove();
            if (!visited.containsKey(curr.toString())){
                visited.put(curr.toString(),curr);
                UpNumberOfVisitedNodes();
            }
            if(curr.equals(endS)) {
                // if found the end position, were done and curr holds the goal state
                solved = true;
                break;
            }
            ArrayList<AState> neighbours = s.getAllPossibleStates(curr);

                    for (int i = 0; i < neighbours.size(); i++) { //go through all neighbours of current node
                        if (!visited.containsKey(neighbours.get(i).toString())){
                            visited.put(neighbours.get(i).toString(),neighbours.get(i));
                            neighbours.get(i).setCameFrom(curr);
                            UpNumberOfVisitedNodes();
                            q.add(neighbours.get(i));
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
