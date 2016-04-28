package com.example.andrei.quiz;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
/**
 * Created by andrei on 11/06/15.
 */

/*
    This class contains multiple methods for interacting with a server,
    handling incoming stream of data and writing data to external memory

 */
public class Communication {


    private Socket s=null;
    private BufferedOutputStream socketOutput = null;
    private PrintWriter printOut = null;
    private Scanner reader = null;
    String Message;
    int counter;



    // connecting to a server and setting up a PrintWriter for reading an incoming stream of data
    public void connect() {
        try {
            s = new Socket("86.24.154.128", 2135);
            socketOutput = new BufferedOutputStream(s.getOutputStream());
            printOut = new PrintWriter(s.getOutputStream(), true);
            reader = new Scanner(s.getInputStream());
        } catch (UnknownHostException e) {
            System.out.println("No such host");
        } catch (IOException e) {
            System.out.println("Wrong input");
        }
        System.out.println("Connected to a server");


    }


    /*listening for an incoming stream of data and registering the message arrival time
        by writing it to external text file
    */
    public String listen(){
        while(true){
            Message = reader.nextLine();

            DateFormat dateFormat = new SimpleDateFormat("mm:ss:ms");
            Date date = new Date();

            // appending the current time in the specified format to the existing file to the end of the line

            try {
                ListenerActivity.osw.append(dateFormat.format(date) + "  ");
                ListenerActivity.osw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            catch (Exception ex) {

                ex.printStackTrace();

            }
            
         //  System.out.println(Message);
            counter=counter+1;
            System.out.println(counter);

        }

    }


}
