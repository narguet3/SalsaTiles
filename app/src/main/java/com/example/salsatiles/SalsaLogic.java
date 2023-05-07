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

    int GAME_SPEED = 5;
    boolean beginning_step = true;

    public int topTile;
    public int secondTop;
    public int thirdTop;
    public int bottomTile;

    int fcounter = 0;

    boolean forward;
    boolean backward;

    SalsaLogic()
    {
        setName("Salsa Game");

        GameType gt = new GameType(1, GameType.GAME_TYPE_SPEED, 30,"Learn the Basic",1);
        addGameType(gt);

        GameType gt2 = new GameType(2, GameType.GAME_TYPE_SPEED, 60,"Level 1: Basics",1);
        addGameType(gt2);

        GameType gt3 = new GameType(3, GameType.GAME_TYPE_SPEED, 120,"Level 2: Basic + Turn",1);
        addGameType(gt3);

        GameType gt4 = new GameType(4, GameType.GAME_TYPE_SPEED, 120,"Level 3: Follow Song",1);
        addGameType(gt4);

        forward = true;
        backward = false;


    }

    public void setTileOrientation (int t, int t1, int t2, int b) {
        this.topTile = t;
        this.secondTop = t1;
        this.thirdTop = t2;
        this.bottomTile = b;
    }


    @Override
    public void onGameStart() {
        //System.out.println("pre game start");
        super.onGameStart();
        //System.out.println("post game start");
        //motoConnection.setAllTilesIdle(LED_COLOR_OFF);

        System.out.println(this.selectedGameType.getName() == "Learn the Basic");
//        if (this.selectedGameType.getName() == "Learn the Basic") {
////            salsaBasic();
//            this.GAME_SPEED = 5;
      //  }
        motoConnection.setAllTilesIdle(LED_COLOR_OFF);
        motoConnection.setTileColor(LED_COLOR_WHITE, thirdTop);


    }

    @Override
    public void onGameUpdate(byte[] message) {

        super.onGameUpdate(message);
        //super.onGameUpdate(message);

        System.out.println("update called");


        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);
//        //int colour = AntData.getColorFromPress(message);
//
//        if (this.selectedGameType.getName() == "Learn the Basic") {
//            salsaBasic(message);
//            this.GAME_SPEED = 5;
//
//        }

//        super.onGameUpdate(message);
//        int tileId = AntData.getId(message);
//        int event = AntData.getCommand(message);
//        int gameType = this.getGameId();
//
//        if (event == EVENT_PRESS)
//        {
//            // Correct tile block
//            if (tileId == this.random_tile_id) // To check if the correct tile has been pressed, we check the tile id
//            {
//                incrementPlayerScore(10, 1); // Adding 10 points if the player presses a correct tile
//                this.getOnGameEventListener().onGameTimerEvent(-500); // Player gets 500 ms less to hit the tile in the next round
//            }
//            else // Incorrect tile block
//            {
//                incrementPlayerScore(-5,1); // Subtracting 10 points if the player presses a correct tile
//                //connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
//                this.getOnGameEventListener().onGameTimerEvent(1000); // Player gets 1000 ms more to hit the tile in the next round
//            }
//            //
//            //generateNextTile();
//        }
//        else // No attempt block
//        {
//            incrementPlayerScore(0,1); // No change to the score
//            this.getOnGameEventListener().onGameTimerEvent(0); // No change to the timing
//
//        }

        System.out.println("Game info: ");
        System.out.println(this.selectedGameType.getName());
        System.out.println(event);

        if (this.selectedGameType.getName() == "Learn the Basic") {
            if (event == EVENT_PRESS) {
                salsaBasicStep();

                System.out.println("fuckkkkk");
            }
        }

    }



    @Override
    public void onGameEnd() {
        super.onGameEnd();
        //sound.speak("Your score was: " + this.getPlayerScore()[0]);
        motoConnection.setAllTilesIdle(LED_COLOR_BLUE);
    }


    public void salsaBasicStep()
    {


            System.out.println(fcounter % 6);

            if (forward) {
                // One - Right foot
                if (fcounter % 6 == 0) //Right foot - RED - follow
                {
                    motoConnection.setTileColor(LED_COLOR_RED, bottomTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

                }

                //Two - Left foot
                else if (fcounter % 6 == 1) {
                    motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot taps - GREEN - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


                }

                //Three - Right foot
                else if (fcounter % 6 == 2) {
                    motoConnection.setTileColor(LED_COLOR_RED, secondTop); //Right foot - RED - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


                }


                //Five - Left foot
                if (fcounter % 6 == 3) {
                    motoConnection.setTileColor(LED_COLOR_GREEN, topTile); //Left foot - GREEN - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);

                }

                //Six - Right foot (tap again)
                if (fcounter % 6 == 4) {
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile); //Right foot - RED - follow
                    motoConnection.setTileColor(LED_COLOR_RED, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);


                }

                //Seven - Left foot
                if (fcounter % 6 == 5) // To check if topTile tile has been pressed
                {
                    motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot - RED - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

                }

                fcounter += 1;
            }

        }

    //}

}
