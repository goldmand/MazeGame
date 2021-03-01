package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * this an interface to be implemented by MyModel class with all the required functionally
 * for creating a model for a board game as the model part from MVVM architecture
 * @Author: Daniel Goldman and Dor Levi
 * @since : 25-6-2020
 */


public interface IModel {

      void stopServers();
      void startServers();

      void GenerateMaze(int rows, int cols) throws UnknownHostException;
      void GenerateSolution();

      int getCellValue(int i,int j, Maze m);

      int getGoalRow(Maze m);
      int getGoalCol(Maze m);

      int getStartRow(Maze m);
      int getStartCol(Maze m);

      int getRows(Maze m);
      int getCols(Maze m);

      Maze getMaze();
      Solution getSol();

      boolean inSol(Solution sol, int i, int j);

      int MakeMove(Maze m, int row, int col);
      int getPlayer_row();

      int getPlayer_col();

      void setPlayer_row(int player_row);

      void setPlayer_col(int player_col);

      void saveFile();
      void loadFile();

}
