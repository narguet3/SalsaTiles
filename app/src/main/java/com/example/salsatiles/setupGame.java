package com.example.salsatiles;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;
import static com.livelife.motolibrary.AntData.LED_COLOR_VIOLET;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;
import com.livelife.motolibrary.MotoSound;

import java.util.ArrayList;

public class setupGame extends AppCompatActivity implements OnAntEventListener {

    TextView tileSetupText;
    ImageView gt_container;
    MotoConnection connection = MotoConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpTiles();
        //tileSetupText.setText(String.valueOf("Orient the tile according to the image below"));
        setContentView(R.layout.activity_setup_game);
        gt_container = findViewById(R.id.setup_image);

    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }
    public void setUpTiles() {
        ArrayList<Integer> connectedTiles = connection.connectedTiles;
        int topTile;
        int secondTop;
        int thirdTop;
        int bottomTile;

        //Assume 4 tiles are connect
        if (connectedTiles.size() != 4) {
            connection.setAllTilesBlink(5, LED_COLOR_RED);
        } else {
            topTile = connectedTiles.get(0);
            secondTop = connectedTiles.get(1);
            thirdTop = connectedTiles.get(2);
            bottomTile = connectedTiles.get(3);

            //light tiles for user to organize correctly
            connection.setTileColor(LED_COLOR_RED, topTile);
            connection.setTileColor(LED_COLOR_BLUE, secondTop);
            connection.setTileColor(LED_COLOR_VIOLET, thirdTop);
            connection.setTileColor(LED_COLOR_GREEN, bottomTile);
        }

    }

}