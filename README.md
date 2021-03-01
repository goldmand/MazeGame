# Maze-game
A project assignment in java that was given by the advanced topics in programming course of 4th semester.

Distinguish between the "environmental" elements of the window (buttons, menus, etc.) and the game board
Which is an independent component attached to our project. We want to have the ability
Pull out the game board as one unit for another project, and easily replace the game board
In our project.
• Also, we configure the functionality of the game board from the outside so that we can customize it as we wish in every project.
• Allow the user to request the creation of a maze according to varying criteria.
• The Maze as a board game - we allow the user to try to solve the maze himself by moving
Character using the keyboard. In order to allow the user to move in all directions) including
Diagonals (Use NumPad:
o In digits 4,6,8,2 for left, right, up, down.
o In digits 7,9,1,3 for diagonals in the upper left, right upper, lower left directions
And bottom right.
• We show the user in some creative way that he was able to solve the maze.
• We allow the user to request a solution to the currently displayed maze. 
The solution will be displayed on top of the maze and will allow
the user to continue playing and get help with the solution.
• We allow the user to save the currently displayed maze to a file on disk.
• We allow the user to load a previously saved maze in the file.
Extras:
• Sounds:
o Background music throughout the game with volume control.
o Play different appropriate music when the maze is solved.
• Pressing the Ctrl key and moving the mouse wheel will zoom in / out to the game board.

Server-Client protocol:
The server receives from the client an int array of size 2 only when
The first cell holds the number of rows in the maze, the second cell the number of columns. 
The server produces a maze according to the parameters, compresses it using MyCompressorOutputStream and sends back
the client a byte array representing the maze created.

The client will receive the array from the server, decrypt it, and be able to use it to display a matching maze.

ServerStrategySolveSearchProblem - The server receives from the client a Maze object representing a maze.
Solves it and returns to the customer a Solution object that holds the solution of the maze.

The generic server code supports multiple clients simultaneously by using a thread pool, and will handle the end case of the neat exit.
The server and client do not maintain a continuous connection but just a question-and-answer session.
The client sends a request, the server responds and then the connection is closed.
For a new request the media must be reopened.
The server saves the solution to the mazes it receives on disk, each solution saved in a separate file.


