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
    public int r_color;

    SalsaLogic()
    {
        setName("Salsa Game");

        GameType gt = new GameType(1, GameType.GAME_TYPE_TIME, 30,"Learn the Basic",1);
        addGameType(gt);

        GameType gt2 = new GameType(2, GameType.GAME_TYPE_TIME, 60,"Level 1: Basics",1);
        addGameType(gt2);

        GameType gt3 = new GameType(3, GameType.GAME_TYPE_TIME, 120,"Level 2: Basic + Turn",1);
        addGameType(gt3);

        GameType gt4 = new GameType(3, GameType.GAME_TYPE_TIME, 120,"Level 3: Follow Song",1);
        addGameType(gt4);


    }

    @Override
    public void onGameStart() {

        super.onGameStart();
        //motoConnection.setAllTilesIdle(LED_COLOR_OFF);

        if (this.selectedGameType.getName() == "Learn the Basic") {
            motoConnection.setAllTilesIdle(LED_COLOR_RED);

        }

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



    @Override
    public void onGameEnd() {
        super.onGameEnd();
        //sound.speak("Your score was: " + this.getPlayerScore()[0]);
        motoConnection.setAllTilesIdle(LED_COLOR_OFF);
    }
}
