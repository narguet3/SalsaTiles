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
        System.out.println("pre game start");
        super.onGameStart();
        System.out.println("post game start");
        //motoConnection.setAllTilesIdle(LED_COLOR_OFF);

        System.out.println(this.selectedGameType.getName() == "Learn the Basic");
        if (this.selectedGameType.getName() == "Learn the Basic") {
            motoConnection.setAllTilesBlink(5, 2);
            System.out.println("red");

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
        motoConnection.setAllTilesIdle(LED_COLOR_BLUE);
    }


    public void Salsa_basic(byte[] message)
    {
        super.onGameUpdate(message);
        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);

        // for ex: could be if used chose Level 1, GAME_SPEED = 1. I think this would go here?

        final int GAME_SPEED = 1; // is this 1 sec?

        boolean beginning_step = true;
        boolean count1;
        boolean count2;
        boolean count3;
        boolean count5;
        boolean count6;
        boolean count7;

        if (event == EVENT_PRESS)
        {

            if (tileId == setupGame. && beginning_step == true) // To check if thirdTop is pressed (aka where both feet start)
            {
                //maybe can say GREEN = both feet (Can list the instructions on the tablet, or just seperate like the exercises)
                motoConnection.setTileColorCountdown(LED_COLOR_VIOLET,thirdTop,this.GAME_SPEED);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                motoConnection.setTileIdle(LED_COLOR_OFFsecondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count0 = true;
                beginning_step = false;
                //can give point here, (bc based on what I did you only move on if you hit the right step)
                // ^^ because of what I said above the for loop idea is better? Or need to think of a better way to give points.
            }

            // One - Right foot
            if (tileId == this.bottomTile && count0 == true) //Right foot - RED - follow
            {
                motoConnection.setTileColorCountdown(LED_COLOR_RED,bottomTile,this.GAME_SPEED);
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                count1 = true;
                beginning_step = false;
            }

            //Two - Left foot
            if (tileId == this.thirdTop && count1 == true)
            {
                motoConnection.setTileColorCountdown(LED_COLOR_GREEN,thirdTop,this.GAME_SPEED); //Left foot taps - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count2 = true;
                beginning_step = false;
            }

            //Three - Right foot
            if (tileId == this.secondTop && count2 == true)
            {
                motoConnection.setTileColorCountdown(LED_COLOR_RED,secondTop,this.GAME_SPEED); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count3 = true;
                beginning_step = false;
            }


            //Five - Left foot
            if (tileId == this.topTile && count3 == true)
            {
                motoConnection.setTileColorCountdown(LED_COLOR_GREEN,topTile,this.GAME_SPEED); //Left foot - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count4 = true;
                beginning_step = false;
            }

            //Six - Right foot (tap again)
            if (tileId == this.bottomTile && count4 == true)
            {
                motoConnection.setTileColorCountdown(LED_COLOR_RED,bottomTile,this.GAME_SPEED); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                count5 = true;
                beginning_step = false;

            }

            //Seven - Left foot
            if (tileId == this.thirdTop && count5 == true) // To check if topTile tile has been pressed
            {
                motoConnection.setTileColorCountdown(LED_COLOR_GREEN,thirdTop,this.GAME_SPEED); //Left foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                count6 = true;
                beginning_step = false;
            }


            //And then could replay to one and leave the user in starting position in the thirdTop tile with both feet.
            // Or could flash again violet to mean that both feet ont he thirdTop and you are back at the beginning

            // IDEAS to make sequence work:
            // putting them in if statements inside each other
            // for loops?
            // maybe can do try catch as well


            // IDEA:
            // Actually can do a for loop displaying the color pattern to be followed
            // Then what use what's above to check if they hit correctly and give a point. maybe can use getcolor inside the if statement to check.
            // Then if the getID = red when pressed point is given

        }

    }

}
