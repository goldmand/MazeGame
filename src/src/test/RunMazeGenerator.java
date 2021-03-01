package test;

import algorithms.mazeGenerators.*;

/**
 * this a testing class used to run test on different maze generating method using different
 * AmazeGenerator instances that implements IMazeGenerator
 */
public class RunMazeGenerator {
    public static void main(String[] args) {
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
        testMazeGenerator(new EmptyMazeGenerator());
    }

    /**
     * this method test the maze generators in terms of  maze generating time
     * @param mazeGenerator, the algorithm implementation to generate a maze
     */
    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(1000/*rows*/,1000/*columns*/)));
        // generate another maze
        Maze maze = mazeGenerator.generate(10/*rows*/, 10/*columns*/);

        // prints the maze
        maze.print();

        // get the maze entrance
        Position startPosition = maze.getStartPosition();

        // print the position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"

        // prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}