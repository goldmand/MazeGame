package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * This class implements a Position in a two-dimensional maze.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
*/
public class Position implements Serializable {

    private int row;
    private int col;

    public Position() {}

    /** This method is a constructor for initializing a position object.
     *
     * @param row determines the coordinate on the x-axis
     * @param col determines the coordinate on the y-axis
     * @return Position is the generated position with the coordinate: (row,col)
     */

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * This method returns the row index of the Position.
     * @return int x-axis index.
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * This method returns the column index of the Position.
     * @return int y0axis index.
     */
    public int getColumnIndex() {
        return col;
    }
    /**
     * This method returns string reprehension of a Position.
     */
    public String toString(){
        return("{"+this.getRowIndex()+","+this.getColumnIndex()+"}");
    }

    /** This method is used to convert a string reprehension of a Position to a Position.
     * @param s determines the coordinate on the x-axis
     * @param p determines the coordinate on the y-axis
     * @return Position is the generated position with the coordinate: (row,col)
     */
    public Position fromString(String s, Position p){
        String[] sarr = s.split(",");
        p.row = Integer.parseInt(sarr[0]);
        p.col = Integer.parseInt(sarr[1]);
        return p;
    }

    /**
     * This method is used to compare two Positions based on their coordinates.
     * @param p1 Position to be compared with this Position
     * @return boolean, returns true if row and col of both Positions are equal, false otherwise.
     */
    public boolean isEqual(Position p1){
        return (this.getColumnIndex()== p1.getColumnIndex()) && (this.getRowIndex()==p1.getRowIndex());
    }

}
