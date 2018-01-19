package threadPooledServer;

/**
 * Created by Lenovo T420 on 16-1-2018.
 * runs the main server
 */
public class ThreadPooledServerRunner {

    public static void main(String[] args) throws Exception {
        ThreadPooledServer server = new ThreadPooledServer(7789);
        new Thread(server).start();
    }
}