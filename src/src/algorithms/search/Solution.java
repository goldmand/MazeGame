package algorithms.search;
/**
 * Solution class represents a Solution for a searchable platform using ArrayList of AStates.
 * @author  Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {

    private ArrayList<AState> solution;

    /**
     * This method is a default constructor, initializes the ArrayList.
     */
    public Solution() {
        this.solution = new ArrayList<AState>();
    }

    /**
     * This method generates the Solution path from the solution field.
     * @return ArrayList<AState>, returns the solution path of the maze, from the starting position to the goal position.
     */
    public ArrayList<AState> getSolutionPath(){
        ArrayList<AState> sol = new ArrayList<>();
        for(int i =solution.size()-1; i>=0;i--)
            sol.add(solution.get(i));
        return sol;
    }

    /**
     * This method adds a state to the solution.
     * @param a AState to be inserted to solution.
     */
    public void insertToSolution(AState a){solution.add(a);}

    /**
     * This method prints all the states in the solution.
     */
    public void print(){
        for(int i=0; i<solution.size();i++){
            solution.get(i).printState();
        }
    }
    public String toString(){
        return Integer.toString(solution.size());
    }

}
