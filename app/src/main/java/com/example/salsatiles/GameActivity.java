package com.example.salsatiles;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;
import com.livelife.motolibrary.MotoSound;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection = MotoConnection.getInstance();
    SalsaLogic game_object = new SalsaLogic(); // Game object
    LinearLayout gt_container;
    TextView player_score; // TextView variable that displays the score of the player
    TextView game_round;
    TextView hit_color; // TextView variable that displays the color to be hit
    int points_scored = 0; // Variable to store the number of points scored by the player

    int hColor; // Variable to store the color to be pressed
    int delay = 4000; //start the game with the default delay of 4 seconds
    //Stop the game when we exit activity

    int rounds = 7;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        game_object.stopGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        connection.registerListener(this);
        connection.setAllTilesToInit();
        gt_container = findViewById(R.id.game_type_container);

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

        player_score = findViewById(R.id.score_value);
        //hit_color = findViewById(R.id.hit_color);
        //game_round = findViewById(R.id.round_number); // Shows what round the user is on

        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {
            @Override

            public void onGameTimerEvent(int i)
            {
                if (i < 0)
                {
                    if (delay >= 2000)
                    {
                        delay += i;
                    }
                }
                else
                {
                    delay += i;
                }
            }

            @Override
            public void onGameScoreEvent(int i, int i1)
            {
                // Updating the score - The score is sent using incrementPlayerScore() -> see onGameUpdate() in the the game class
                points_scored = i;
                //hColor = color;
                GameActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        player_score.setText(String.valueOf(points_scored));
                        //hit_color.setText(colorNames.get(game_object.r_color));
                    }

                });

            }

            @Override
            public void onGameStopEvent()
            {

            }

            @Override
            public void onSetupMessage(String s)
            {

            }

            @Override
            public void onGameMessage(String s)
            {

            }

            @Override
            public void onSetupEnd()
            {

            }
        });

        // Displaying each colour for a certain period of time (default - 3000 ms)
        Thread my_thread = new Thread()
        {
            int i = rounds - 1; // Loop variable

            String end_message = "Game Over"; // This message is displayed at the end of the last round
            int round = 1; // Stores the current round number

            @Override
            public void run()
            {
                try
                {
                    while (i >= 0)
                    {
                        //game_object.generateNextTile();
                        Thread.sleep(delay);

                        runOnUiThread(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                if (i >= 0)
                                {
                                    connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
                                    //game_object.generateNextTile();
                                    //hit_color.setText(colorNames.get(game_object.r_color)); // Updating and displaying a new colour
                                    //game_object.generateNextTile(); // Updating the colour of the Moto tiles
                                    //game_round.setText(toString().valueOf(round)); // Updating and displaying a new colour
                                    round++;
                                }
                                else
                                {
                                    Timer timer_object  = new Timer(); // Object of the Timer Class

                                    // Displaying the end game message
                                    timer_object.schedule(new TimerTask()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            finish();
                                        }
                                    }, 5000);

                                    //hit_color.setText(end_message);
                                    game_object.onGameEnd();
                                }
                                i--;
                            }
                        });
                    }
                }
                catch (InterruptedException e)
                {

                }
            }
        };

        my_thread.start();


    }

    @Override
    public void onMessageReceived(byte[] bytes, long l)
    {
        game_object.addEvent(bytes);
    }

    @Override
    public void onAntServiceConnected()
    {

    }

    @Override
    public void onNumbersOfTilesConnected(final int i)
    {

    }
}