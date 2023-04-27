package com.example.salsatiles;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;

import static com.livelife.motolibrary.AntData.*;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SalsaLogic extends Game
{
    MotoConnection motoConnection = MotoConnection.getInstance();
    MotoSound sound = MotoSound.getInstance();
    final int BLINK = 2;
    int random_tile_id;
    int hitColor;
    private ArrayList<Integer> colour_list = new ArrayList<>();
    public int r_color;

    SalsaLogic()
    {
        setName("Hit The Color");


    }

    @Override
    public void onGameStart() {

        super.onGameStart();
        motoConnection.setAllTilesIdle(LED_COLOR_OFF);

    }

    @Override
    public void onGameUpdate(byte[] message) {

        super.onGameUpdate(message);
        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);
        //int colour = AntData.getColorFromPress(message);

        if (event == EVENT_PRESS)
        {
            // Correct tile block
            if (tileId == this.random_tile_id) // To check if the correct tile has been pressed, we check the tile id
            {
                incrementPlayerScore(10, 1); // Adding 10 points if the player presses a correct tile
                this.getOnGameEventListener().onGameTimerEvent(-500); // Player gets 500 ms less to hit the tile in the next round
            }
            else // Incorrect tile block
            {
                incrementPlayerScore(-5,1); // Subtracting 10 points if the player presses a correct tile
                //connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
                this.getOnGameEventListener().onGameTimerEvent(1000); // Player gets 1000 ms more to hit the tile in the next round
            }
            //
            //generateNextTile();
        }
        else // No attempt block
        {
            incrementPlayerScore(0,1); // No change to the score
            this.getOnGameEventListener().onGameTimerEvent(0); // No change to the timing

        }
    }


    public void setUpTiles() {
        ArrayList<Integer> connectedTiles = motoConnection.connectedTiles;
        int topTile;
        int secondTop;
        int thirdTop;
        int bottomTile;

        //Assume 4 tiles are connect
        if (connectedTiles.size() != 4) {
            motoConnection.setAllTilesBlink(5, LED_COLOR_RED);
        } else {
            topTile = connectedTiles.get(0);
            secondTop = connectedTiles.get(1);
            thirdTop = connectedTiles.get(2);
            bottomTile = connectedTiles.get(3);

            //light tiles for user to organize correctly
            motoConnection.setTileColor(LED_COLOR_RED, topTile);
            motoConnection.setTileColor(LED_COLOR_VIOLET, secondTop);
            motoConnection.setTileColor(LED_COLOR_BLUE, thirdTop);
            motoConnection.setTileColor(LED_COLOR_GREEN, bottomTile);
        }

    }

    @Override
    public void onGameEnd() {
        super.onGameEnd();
        //sound.speak("Your score was: " + this.getPlayerScore()[0]);
        motoConnection.setAllTilesIdle(LED_COLOR_OFF);
    }
}
