package algorithms.search;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;

/**
 * MazeState class extends the abstract state class into a maze state which will be used in maze searching platform
 * uses Position to adapt to maze searching algorithm
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public class MazeState extends AState implements Serializable {

    private Position mstate;

    /**
     * This method is default constructor to create an object of MazeState
     * @return MazeState object.
     */
    public MazeState() {
        Position p = new Position();
        mstate = p.fromString(this.getState(),p);
    }

    /**
     * This method is a constructor to create an object of MazeState.
     * @param state, String to be set as the state
     * @return MazeState object with the string state.
     */
    public MazeState(String state) {
        Position p = new Position();
        mstate = p.fromString(state,p);
    }

    /**
     * This method is a constructor to create an object of MazeState.
     * @param p, Position to be set as the state
     * @return MazeState object with the string state.
     */
    public MazeState(Position p) {
        mstate = p;
    }

    /**
     * This method is an override for the comparator of AState, compares two Mazes states according to Positions
     * @param obj MazeState to be compared with this state
     * @return boolean if the two positions of the two states are equal.
     */
    @Override
    public boolean equals(Object obj) {
        MazeState other= (MazeState) obj;
        return other.getMstate().isEqual(this.getMstate());
    }


    /**
     * This method is a getter of the mstate field
     * @return Position of this MazeState
     */
    public Position getMstate() {
        return mstate;
    }

    /**
     * This method is used to get a representation of this state Position
     * @return String, String in {row,column} format.
     */
    @Override
    public String toString(){
        return("{"+this.mstate.getRowIndex()+","+this.mstate.getColumnIndex()+"}");
    }

    /**
     * This method is used to print a state in [row,column] format.
     */
    @Override
    public void printState() {
        System.out.println("["+this.mstate.getRowIndex()+","+this.mstate.getColumnIndex()+"]");
    }


}