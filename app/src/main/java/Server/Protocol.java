package Server;

import android.app.Activity;
import android.content.Context;

import com.example.andrei.quiz.MainActivity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by andrei on 10/06/15.
 */
/*
    This class is a protocol which has a Thread-and-runnable-method architecture
 */
public class Protocol extends Thread {

        private Socket socket = null;
        private PrintWriter printOut = null;
        private Scanner reader = null;
        String Message;


    // constructor
    public Protocol(Socket socket) {
                super("Protocol");
                this.socket = socket;
            try {
                printOut = new PrintWriter(socket.getOutputStream(), true);
                reader = new Scanner(socket.getInputStream());
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }


        /*runnable method which listens on the socket to the incoming stream of data
        and forwards it to another client
        */
        public void run() {

            System.out.println("connected");
            while (true) {
                Message = reader.nextLine();

                ClientHandler.chooseClient(Message);


            }

        }
    // sending out a message
    public void sendMessage(String message){

        printOut.printf("%s\n",message);



    }


}
