package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategyStringReverser implements IServerStrategy {

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter toClient = new PrintWriter(outputStream);

        try {
            String phrase = fromClient.readLine();
            String reversedPhrase;
            reversedPhrase = new StringBuilder(phrase).reverse().toString();
            toClient.write(reversedPhrase + "\r\n");
            toClient.flush();
            toClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}