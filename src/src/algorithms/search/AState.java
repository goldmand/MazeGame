package algorithms.search;

/**
 * AState abstract class represents a state in a searchable platform.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */

public abstract class AState {
    private String state;
    private double cost;
    private AState cameFrom;

    public AState() {}

    /**
     * This method returns the cost to reaching this state.
     * @return double, the cost in double.
     */
    public double getCost() {
        return cost;
    }

    /**
     * This methods sets the value of the field cost to the given parameter.
     * @param cost the value to be inserted to the field "cost".
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * This method sets a new state to this state predecessor.
     * @param cameFrom, the new state that will become this state predecessor.
     */
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    /**
     * This method is a getter for the state field.
     * @return String, returns the string representation this state's Position (row,col)
     */
    public String getState() {return state;}

    /**
     * This method is a getter for the cameFrom field.
     * @return Astate, returns the predecessor of this state.
     */
    public AState getCameFrom() {return cameFrom;}

    public void printState(){}
}
