package com.example.salsatiles;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;
import static com.livelife.motolibrary.AntData.LED_COLOR_VIOLET;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
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
import java.util.Timer;
import java.util.TimerTask;

public class setupGame extends AppCompatActivity implements OnAntEventListener {

    TextView tileSetupText;
    SalsaLogic game_object = new SalsaLogic();
    LinearLayout gt_container;
    ImageView image_container;
    MotoConnection connection = MotoConnection.getInstance();
    public int topTile;
    public int secondTop;
    public int thirdTop;
    public int bottomTile;
    Button startGameButton;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        game_object.stopGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpTiles();
        //tileSetupText.setText(String.valueOf("Orient the tile according to the image below"));
        setContentView(R.layout.activity_setup_game);
        connection.registerListener(this);
        //startGameButton = findViewById(R.id.startButton);
        image_container = findViewById(R.id.setup_image);
        gt_container = findViewById(R.id.game_type_container);

//        startGameButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                connection.unregisterListener(setupGame.this);
//                Intent i = new Intent(setupGame.this, SalsaLogic.class);
//                startActivity(i);
//            }
//        });
        for (final GameType gt : game_object.getGameTypes())
        {
            Button b = new Button(this);
            b.setText(gt.getName());
            b.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    game_object.selectedGameType = gt;
                    game_object.startGame();
                }
            });
            gt_container.addView(b);
        }

        game_object.setOnGameEventListener(new Game.OnGameEventListener() {
            @Override
            public void onGameTimerEvent(int i) {

            }

            @Override
            public void onGameScoreEvent(int i, int i1) {

            }

            @Override
            public void onGameStopEvent() {

            }

            @Override
            public void onSetupMessage(String s) {

            }

            @Override
            public void onGameMessage(String s) {

            }

            @Override
            public void onSetupEnd() {

            }
        });

        if (game_object.getName() == "Learn the Basic") {

        }

//        Thread my_thread = new Thread()
//        {
//            int i = rounds - 1; // Loop variable
//
//            String end_message = "Game Over"; // This message is displayed at the end of the last round
//            int round = 1; // Stores the current round number
//
//            @Override
//            public void run()
//            {
//                try
//                {
//                    while (i >= 0)
//                    {
//                        //game_object.generateNextTile();
//                        Thread.sleep(delay);
//
//                        runOnUiThread(new Runnable()
//                        {
//
//                            @Override
//                            public void run()
//                            {
//                                if (i >= 0)
//                                {
//                                    connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
//                                    game_object.generateNextTile();
//                                    //hit_color.setText(colorNames.get(game_object.r_color)); // Updating and displaying a new colour
//                                    //game_object.generateNextTile(); // Updating the colour of the Moto tiles
//                                    //game_round.setText(toString().valueOf(round)); // Updating and displaying a new colour
//                                    round++;
//                                }
//                                else
//                                {
//                                    Timer timer_object  = new Timer(); // Object of the Timer Class
//
//                                    // Displaying the end game message
//                                    timer_object.schedule(new TimerTask()
//                                    {
//                                        @Override
//                                        public void run()
//                                        {
//                                            finish();
//                                        }
//                                    }, 5000);
//
//                                    //hit_color.setText(end_message);
//                                    game_object.onGameEnd();
//                                }
//                                i--;
//                            }
//                        });
//                    }
//                }
//                catch (InterruptedException e)
//                {
//
//                }
//            }
//        };
//
//        my_thread.start();

    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {
        game_object.addEvent(bytes);
    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }

    public void setUpTiles() {
        ArrayList<Integer> connectedTiles = connection.connectedTiles;

        //Assume 4 tiles are connect
        if (connectedTiles.size() != 4) {
            connection.setAllTilesBlink(5, LED_COLOR_RED);
        } else {
            this.topTile = connectedTiles.get(0);
            this.secondTop = connectedTiles.get(1);
            this.thirdTop = connectedTiles.get(2);
            this.bottomTile = connectedTiles.get(3);

            //light tiles for user to organize correctly
            connection.setTileColor(LED_COLOR_RED, topTile);
            connection.setTileColor(LED_COLOR_BLUE, secondTop);
            connection.setTileColor(LED_COLOR_VIOLET, thirdTop);
            connection.setTileColor(LED_COLOR_GREEN, bottomTile);
        }

        game_object.setTileOrientation(topTile, secondTop, thirdTop, bottomTile);

    }


}