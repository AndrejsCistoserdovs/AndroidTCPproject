package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andrei on 10/06/15.
 */
/*
    This class handles new connections
 */
public class ClientHandler {
    // an array of clients connected to a server
    public static ArrayList<Protocol> Clients=null;

    public static void main(String[] args) throws IOException {
        Clients = new ArrayList<Protocol>();
        boolean listening=true;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(2135);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 2135");
            System.exit(-1);
        }
        // listening on the socket to new connections and add new connected clients to the array
        while (listening) {
            Protocol client = new Protocol(serverSocket.accept());
            Clients.add(client);
            client.start(); // starting a thread for new client

        }
        // closing a socket
        serverSocket.close();

    }
      /* sending a message to the client which was connected first.
         This is because first of all we connect a phone to a server and then a MessageSender client
     */
    public static void chooseClient(String message){

        Protocol client= Clients.get(0);

                client.sendMessage(message);


    }

}
