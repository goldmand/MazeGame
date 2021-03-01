package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.util.ArrayList;

/**
 * this a testing class used to run test on solving maze using different ISearchingAlgorithm instances (BFS,DFS,etc..)
 */
public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new SimpleMazeGenerator();
        Maze maze = mg.generate(55, 44);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }

    /**
     * this method solves the ISearchable instance (Graph, Maze, etc..)
     * using the ISearchingAlgorithm instance(DFS,BFS, etc..) and measures the time taken to solve
     * as well as the number of visited nodes in the process
     * @param domain the searchable platform
     * @param searcher the search algorithm
     */
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
    }
}