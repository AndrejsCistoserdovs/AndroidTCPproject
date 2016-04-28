package com.example.andrei.quiz;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

// This activity class handles long-term asynchronous task
public class ListenerActivity extends Activity {

    TelephonyManager TelephonManager;   // TelephonyManager provides an access to phone and services
    MyPhoneStateListener MyListener;    // MyPhoneStateListener provides an access to a state of the phone
    public static OutputStreamWriter osw; // OutputStreamWriter sends out a stream of data
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listener_activity);
        this.createFile();

        // registering a phone with a listener
        try {
            MyListener   = new MyPhoneStateListener();
            TelephonManager = (TelephonyManager)this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            TelephonManager.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
        catch (Exception ex) {

            ex.printStackTrace();

        }

        MessageListener Runner = new MessageListener();
        Runner.execute();

    }



    // This class allows to listen to the incoming stream of data without blocking a GUI thread
    private class MessageListener extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

               return MainActivity.mTcpClient.listen();


           }
         @Override
        protected void onPostExecute(String message) {
            // execution of result of Long time consuming operation




            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // this listener gets the signal strength each time the signal strength changes
    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            String value= String.valueOf(signalStrength.getGsmSignalStrength())+"  "; // convert SignalStrength into String
            try{
                osw.append(value);  // append the String to the existing file to the end of the line
                osw.flush();    // flush the buffer
            }
            catch(IOException e) {
                e.printStackTrace();
            }


        }

    }

    //  creating a text file for logs in the phone's external memory

    public void createFile(){
        File sdCard = Environment.getExternalStorageDirectory();    // getting an absolute path of sdCard
        File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");   // setting the path for the file
        directory.mkdirs();
        file = new File(directory, "logs.txt"); // creating a file

        try {
            FileOutputStream fOut = new FileOutputStream(file,true);  // instantiate FileOutputStream
            osw = new OutputStreamWriter(fOut); // instantiate OutputStreamWriter

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
