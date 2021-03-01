package Server;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * The class Configurations.
 * used to adjust the user configurations to the project.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class Configurations {

    /**
     * This method gets the "ThreadsInPool" value from the config.
     * default value is 5.
     * @return returns the number of maximum threads in the pool.
     */
    public static int getThreadsInPool() {
        try (InputStream input = new FileInputStream(System.getProperty("user.dir")+"\\src\\resources\\config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return Integer.parseInt(prop.getProperty("ThreadsInPool"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 5;
    }
    /**
     * This method gets the "SearchingAlgorithm" value from the config.
     * default value is BestFistSearch
     * @return returns the Searching algorithm.
     */
    public static ISearchingAlgorithm getSearchingAlgorithm() {
        try (InputStream input = new FileInputStream(System.getProperty("user.dir")+"\\src\\resources\\config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            String search = prop.getProperty("SearchingAlgorithm");
            if (search.equals("BestFirstSearch"))
                return new BestFirstSearch();
            else if (search.equals("BreadthFirstSearch"))
                return new BreadthFirstSearch();
            else if (search.equals("DepthFirstSearch"))
                return new DepthFirstSearch();
            return new BestFirstSearch();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BestFirstSearch();
    }
}