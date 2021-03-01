package algorithms.search;

/**
 * BestFirstSearch class is an implementation of Best First Search that inherits the solving algorithm from Breadth First Search
 * and giving a priory to the cross moves.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class BestFirstSearch extends BreadthFirstSearch{

    /**
     * Default constructor that sets Priority to true.
     */
    public BestFirstSearch() {
        setPriority(true);
    }

    /**
     * This method is a getter for the search name
     * @return String, returns the search's name
     */
    public String getName(){return "BestFirstSearch";}
}
