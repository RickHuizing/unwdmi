package threadPooledServer;

import resources.ExecutorServices;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lenovo T420 on 16-1-2018.
 * runs the main server
 */
public class ThreadPooledServerRunner {
    public static ThreadPooledServer  server = new ThreadPooledServer();
    public static boolean isRunning;

    public static void main(String[] args) throws Exception {
        new Thread(server).start();
        isRunning=true;
    }
}