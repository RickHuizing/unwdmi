package resources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Lenovo T420 on 21-1-2018.
 */
public final class ExecutorServices {
    private ExecutorServices(){}
    private static final int MAIN_EXECUTOR_THREAD_POOL_SIZE = 1;
    private static final int WRITER_EXECUTOR_THREAD_POOL_SIZE = 2;
    private static final int SOCKET_READER_EXECUTOR_THREAD_POOL_SIZE = 2;

    public static int connections = 0;

    public static final ScheduledExecutorService MAIN_EXECUTOR =
            Executors.newScheduledThreadPool(MAIN_EXECUTOR_THREAD_POOL_SIZE);


    public static final ExecutorService WRITER_EXECUTOR =
            Executors.newFixedThreadPool(WRITER_EXECUTOR_THREAD_POOL_SIZE);

    public static final ScheduledExecutorService SOCKET_READER_EXECUTOR =
            Executors.newScheduledThreadPool(SOCKET_READER_EXECUTOR_THREAD_POOL_SIZE);
}
