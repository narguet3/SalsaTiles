package com.example.salsatiles;

// What Nic had there
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}

// Main Activity basic skeleton

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

public class MainActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection;
    Button pairingButton, startGameButton;
    TextView statusTextView; // To display the number of tiles connected
    boolean is_pairing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection=MotoConnection.getInstance();
        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(36);         // Check the back of your tiles for the RF
        connection.setDeviceId(1);              // Your group number
        connection.registerListener(MainActivity.this);

        statusTextView = findViewById(R.id.statusTextView);
        pairingButton = findViewById(R.id.pairingButton);
        startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                connection.unregisterListener(MainActivity.this);
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        pairingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!is_pairing)
                {
                    connection.pairTilesStart();
                    pairingButton.setText("Stop Paring");
                }
                else
                {
                    connection.pairTilesStop();
                    pairingButton.setText("Start Paring");
                }
                is_pairing = !is_pairing;
            }
        });
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l)
    {

    }

    @Override
    public void onAntServiceConnected()
    {
        connection.setAllTilesToInit();
    }

    @Override
    public void onNumbersOfTilesConnected(final int i)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                statusTextView.setText(i + " connected tiles");

            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        connection.registerListener(MainActivity.this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }

    public void buttonClicked(View view){

        /*
        //class that has a method attached to it, that is called the run method, and is executed when the runnable is executed
        Runnable run = new Runnable() {
            @Override
            public void run() {

            }
        }
         */

    }
}



