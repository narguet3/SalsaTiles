package com.example.salsatiles;

import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;
import static com.livelife.motolibrary.AntData.LED_COLOR_VIOLET;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnAntEventListener {
    MotoConnection connection;
    Button pairingButton, startGameButton, orientButton;
    TextView statusTextView; // To display the number of tiles connected
    boolean is_pairing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = MotoConnection.getInstance();
        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(36);         // Check the back of your tiles for the RF
        connection.setDeviceId(1);              // Your group number
        connection.registerListener(MainActivity.this);

        statusTextView = findViewById(R.id.statusTextView);
        pairingButton = findViewById(R.id.pairingButton);
        orientButton = findViewById(R.id.orientButton);

        pairingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_pairing) {
                    connection.pairTilesStart();
                    pairingButton.setText("Stop Paring");
                } else {
                    connection.pairTilesStop();
                    pairingButton.setText("Start Paring");
                }
                is_pairing = !is_pairing;
            }
        });

        orientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setUpTiles();
                connection.unregisterListener(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, setupGame.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

    }

    @Override
    public void onAntServiceConnected() {
        connection.setAllTilesToInit();
    }

    @Override
    public void onNumbersOfTilesConnected(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusTextView.setText(i + " connected tiles");

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection.registerListener(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }

}




