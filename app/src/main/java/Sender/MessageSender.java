package Sender;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by andrei on 17/03/16.
 */
/*
This is a client which sends out messages to a server
 */
public class MessageSender {
    private Socket s=null;
    private BufferedOutputStream socketOutput = null;
    private PrintWriter printOut = null;
    String Message;

    public static void main(String[] args) throws IOException {
        MessageSender sender = new MessageSender();
        sender.connect();
        sender.sendMessages();
    }

    // connecting to a server and instantiating PrintWriter to write out stream of data
    public void connect() {
        try {
            s = new Socket("192.168.0.7", 2135);
            socketOutput = new BufferedOutputStream(s.getOutputStream());
            printOut = new PrintWriter(s.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.out.println("No such host");
        } catch (IOException e) {
            System.out.println("Wrong input");
        }
        System.out.println("Connected to a server");
    }

    // sending messages to a server
    public void sendMessages(){
        for (int i=1;i<101;i++){
            //Message = Integer.toString(i);
            Message="hello";
            printOut.printf("%s\n", Message);   // format of the message
            System.out.println(i);

            // specifying a delay between the message dispatch
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }


        }


    }



}
