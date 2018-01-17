package threadPooledServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * socket, bufferedReader pairi
 */
public class socketConnection{
    int portNumber;
    Socket clientSocket = null;
    BufferedReader bufferedReader = null;

    public socketConnection(Socket socket){
        //7789
        this.clientSocket = socket;
        this.portNumber = socket.getPort();
        try {
            this.bufferedReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
