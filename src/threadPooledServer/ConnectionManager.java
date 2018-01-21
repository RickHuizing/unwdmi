package threadPooledServer;

import resources.ExecutorServices;

import java.util.*;
import java.util.concurrent.*;

import static resources.ExecutorServices.WRITER_EXECUTOR;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * holds all open connections, schedules receiving and writing of data
 */
class ConnectionManager {
    private final Set connections = Collections.synchronizedSet(new HashSet());

    ConnectionManager(){}

    void addConnection(SocketConnection connection){
        synchronized (connections) {
            connections.add(connection);
        }
        InputReader reader = new InputReader(connection.bufferedReader, this);
        //800 sockets need to receive 165 lines/minute
        ExecutorServices.SOCKET_READER_EXECUTOR.scheduleAtFixedRate(reader, 10, 5, TimeUnit.MILLISECONDS);
    }
}
