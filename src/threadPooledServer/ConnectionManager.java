package threadPooledServer;

import resources.ExecutorServices;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * holds all open connections, schedules receiving and writing of data
 */
class ConnectionManager implements Runnable {
    private final Set<SocketConnection> connections = Collections.synchronizedSet(new HashSet<>());
    private final Set<InputReaderTest> inputReaders = Collections.synchronizedSet(new HashSet<>());

    ConnectionManager(){}

    void addConnection(SocketConnection connection){
        synchronized (inputReaders) {
            InputReaderTest reader = new InputReaderTest(connection.bufferedReader);
            inputReaders.add(reader);
            //connections.add(connection);
        }
        //InputReader reader = new InputReader(connection.bufferedReader);
        //800 sockets need to receive 165 lines/minute, receiving 1 line = ca 2500 nanoseconds = 330 milliseconds/second for receiving data
        //reader.setScheduledFuture(ExecutorServices.SOCKET_READER_EXECUTOR.scheduleAtFixedRate(reader, 5, 7, TimeUnit.MILLISECONDS));
    }
    public boolean isFull(){
        return inputReaders.size() >= 800;
    }

    public void run() {
        synchronized (inputReaders) {
            Iterator<InputReaderTest> it = inputReaders.iterator();
            InputReaderTest inputReaderTest = null;
            while(it.hasNext()){
                inputReaderTest = it.next();
                if(!inputReaderTest.isStopped()) {
                    inputReaderTest.run();
                }else{
                    it.remove();
                }

            }
        }
    }
}
