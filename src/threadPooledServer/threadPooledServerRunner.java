package threadPooledServer;

/**
 * Created by Lenovo T420 on 16-1-2018.
 * runs the main server
 */
public class threadPooledServerRunner {

    public static void main(String[] args) throws Exception {
        threadPooledServer server = new threadPooledServer(7789);
        new Thread(server).start();
    }
}