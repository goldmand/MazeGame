package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.IOException;
import java.net.UnknownHostException;

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
}
