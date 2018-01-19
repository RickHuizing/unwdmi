package threadPooledServer;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * holds all open connections, schedules receiving and writing of data
 */
class ConnectionManager {
    private final Set connections = Collections.synchronizedSet(new HashSet());
    ExecutorService writerExecutor  =
            Executors.newFixedThreadPool(50);
    private ScheduledExecutorService scheduledExecutor =
            Executors.newScheduledThreadPool(10);

    ConnectionManager(){}

    void addConnection(SocketConnection connection){
        synchronized (connections) {
            connections.add(connection);
        }
        InputReader reader = new InputReader(connection.bufferedReader, this);
        scheduledExecutor.scheduleAtFixedRate(reader, 10, 5, TimeUnit.MILLISECONDS);
    }
    ExecutorService getExecutor(){
        return writerExecutor;
    }
}
