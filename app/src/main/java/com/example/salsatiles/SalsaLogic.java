package com.example.salsatiles;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;
import static com.livelife.motolibrary.AntData.LED_COLOR_WHITE;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.concurrent.TimeUnit;


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

        //for side step
        //motoConnection.setTileColor(LED_COLOR_RED, thirdTop);
        //motoConnection.setTileColor(LED_COLOR_GREEN, secondTop);


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

        // FOR TESTING
        if (this.selectedGameType.getName() == "Learn the Basic") {
            if (event == EVENT_PRESS) {
                //salsaBasicStep();
                try {
                    salsaBasicTurn();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

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


    public void salsaBasicStep() {


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

    public void salsaSideStep() throws InterruptedException {


        System.out.println(fcounter % 7);

        if (forward) {
            // One - Right foot
            if (fcounter % 7 == 0) //Right foot - RED - follow
            {
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

            }

            //Two - Left foot
            else if (fcounter % 7 == 1) {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot taps - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


            }

            //Three - Right foot
            else if (fcounter % 7 == 2) {
                motoConnection.setTileColor(LED_COLOR_RED, secondTop); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


            }

            // Turn step same up to here
            //Five - Left foot
            if (fcounter % 7 == 3) {
                motoConnection.setTileColor(LED_COLOR_GREEN, topTile); //Left foot - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);

            }

            //Six
            // PIVOT point - Here light third top and top Violet to signify pivot
            if (fcounter % 7 == 4) {
                motoConnection.setTileIdle(LED_COLOR_RED, bottomTile); //Right foot - RED - follow
                motoConnection.setTileColor(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileColor(LED_COLOR_OFF, topTile);

                TimeUnit.SECONDS.sleep(1);

                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileColor(LED_COLOR_BLUE, secondTop); // BLUE
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileColor(LED_COLOR_BLUE, topTile); // BLUE

            }

            //Seven - Left foot & PIVOT
            if (fcounter % 7 == 5) // To check if topTile tile has been pressed
            {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

                TimeUnit.SECONDS.sleep(1);

                //Left foot - RED - follow
                motoConnection.setTileColor(LED_COLOR_BLUE, thirdTop); //BLUE
                motoConnection.setTileColor(LED_COLOR_BLUE, secondTop); //BLUE
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

            }

            // End in one for TURN
            // One - Right foot
            if (fcounter % 7 == 6) //Right foot - RED - follow
            {
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

            }

        }
        fcounter += 1;
    }

    public void salsaBasicTurn() throws InterruptedException {


        System.out.println(fcounter % 9);

        if (forward) {
            // One - Right foot
            if (fcounter % 9 == 0) //Right foot - RED - follow
            {
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);

            }

            //Two - Left foot
            if (fcounter % 9 == 1) {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot taps - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


            }

            //Three - Right foot
            if (fcounter % 9 == 2) {
                motoConnection.setTileColor(LED_COLOR_RED, secondTop); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


            }

            // Turn step same up to here
            //Five - Left foot
            if (fcounter % 9 == 3) {
                motoConnection.setTileColor(LED_COLOR_GREEN, topTile); //Left foot - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);

            }

            // Fine up to here!

            /*
            // WRONG
            //Six - Right foot and PIVOT
            // Step before pivot
            if (fcounter % 9 == 4) {
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
            }
            */

            // PIVOT point - BLUE BLUE
            if (fcounter % 9 == 5) {
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileColor(LED_COLOR_BLUE, secondTop); // BLUE
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileColor(LED_COLOR_BLUE, topTile); // BLUE
            }

            // --- works up to here ---

            //Seven - Left foot & PIVOT
            // Step before pivot
            if (fcounter % 9 == 6)
            {
                System.out.println("Need LEFT GREEN thrid top");
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot - RED - follow
            }

            // PIVOT point - BLUE BLUE
            if (fcounter % 9 == 7) {
                motoConnection.setTileColor(LED_COLOR_BLUE, thirdTop);
                motoConnection.setTileColor(LED_COLOR_BLUE, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
            }

            // End in one for TURN
            // One - Right foot
            if (fcounter % 9 == 8) //Right foot - RED - follow
            {
                TimeUnit.SECONDS.sleep(1);

                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);

            }

        }
        fcounter += 1;

    }



}