package resources;

import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.*;
import java.util.Properties;

import static Server.Configurations.getSearchingAlgorithm;
import static Server.Configurations.getThreadsInPool;

public class Configurations {

    public Configurations(){};

    public static void setSolver(String solver) {
        Properties properties = new Properties();

        Throwable var3;
        try {
            InputStream in = new FileInputStream(System.getProperty("user.dir") +"\\src\\resources\\config.properties");
            var3 = null;

            try {
                properties.load(in);
            } catch (Throwable var31) {
                var3 = var31;
                throw var31;
            } finally {
                if (in != null) {
                    if (var3 != null) {
                        try {
                            in.close();
                        } catch (Throwable var30) {
                            var3.addSuppressed(var30);
                        }
                    } else {
                        in.close();
                    }
                }

            }
        } catch (IOException var35) {
            var35.printStackTrace();
        }

        properties.setProperty("SearchingAlgorithm", solver);


        try {
            OutputStream out = new FileOutputStream(System.getProperty("user.dir") +"\\src\\resources\\config.properties");
            var3 = null;

            try {
                properties.store(out, (String)null);
            } catch (Throwable var29) {
                var3 = var29;
                throw var29;
            } finally {
                if (out != null) {
                    if (var3 != null) {
                        try {
                            out.close();
                        } catch (Throwable var28) {
                            var3.addSuppressed(var28);
                        }
                    } else {
                        out.close();
                    }
                }

            }
        } catch (IOException var33) {
            var33.printStackTrace();
        }
    }
    public static ISearchingAlgorithm getSearchingAlgorithem() {
        try {
            String s=System.getProperty("user.dir");
            InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\src\\resources\\config.properties");
            Throwable var1 = null;

            BestFirstSearch var4;
            try {
                Properties prop = new Properties();
                prop.load(input);
                String search = prop.getProperty("SearchingAlgorithm");
                if (search.equals("BestFirstSearch")) {
                    var4 = new BestFirstSearch();
                    return var4;
                }

                if (search.equals("BreadthFirstSearch")) {
                    BreadthFirstSearch var21 = new BreadthFirstSearch();
                    return var21;
                }

                if (search.equals("DepthFirstSearch")) {
                    DepthFirstSearch var20 = new DepthFirstSearch();
                    return var20;
                }

                var4 = new BestFirstSearch();
            } catch (Throwable var17) {
                var1 = var17;
                throw var17;
            } finally {
                if (input != null) {
                    if (var1 != null) {
                        try {
                            input.close();
                        } catch (Throwable var16) {
                            var1.addSuppressed(var16);
                        }
                    } else {
                        input.close();
                    }
                }

            }

            return var4;
        } catch (IOException var19) {
            var19.printStackTrace();
            return new BestFirstSearch();
        }
    }



    public static int  getNumOfThreads() {
        try {
            InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\src\\resources\\config.properties");
            Throwable var1 = null;

            int var3;
            try {
                Properties prop = new Properties();
                prop.load(input);
                var3 = Integer.parseInt(prop.getProperty("ThreadsInPool"));
            } catch (Throwable var13) {
                var1 = var13;
                throw var13;
            } finally {
                if (input != null) {
                    if (var1 != null) {
                        try {
                            input.close();
                        } catch (Throwable var12) {
                            var1.addSuppressed(var12);
                        }
                    } else {
                        input.close();
                    }
                }

            }

            return var3;
        } catch (IOException var15) {
            var15.printStackTrace();
            return 5;
        }
    }




}
