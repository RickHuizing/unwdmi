package threadPooledServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * socket, bufferedReader pairi
 */
class SocketConnection {
    BufferedReader bufferedReader = null;

    SocketConnection(Socket socket){
        //7789
        try {
            this.bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + socket.getPort() + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
