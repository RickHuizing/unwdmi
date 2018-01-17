package threadPooledServer;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * holds all open connections, schedules receiving and writing of data
 */
class connectionManager {
    private final Set connections = Collections.synchronizedSet(new HashSet());
    ExecutorService socketExecutor =
            Executors.newFixedThreadPool(800);
    private ScheduledExecutorService scheduledExecutor =
            Executors.newScheduledThreadPool(4);

    connectionManager(){}

    void addConnection(socketConnection connection){
        synchronized (connections) {
            connections.add(connection);
        }
        inputReader reader = new inputReader(connection.bufferedReader);
        scheduledExecutor.scheduleAtFixedRate(reader, 10, 1750, TimeUnit.MILLISECONDS);
    }
}
