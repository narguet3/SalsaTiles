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
    boolean count0 = false;
    boolean count1 = false;
    boolean count2 = false;
    boolean count3 = false;
    boolean count4 = false;
    boolean count5 = false;
    boolean count6 = true;

    public int topTile;
    public int secondTop;
    public int thirdTop;
    public int bottomTile;

    int fcounter = 0;
    int bcounter = 0;

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


    }
//    public void setUpTiles() {
//        ArrayList<Integer> connectedTiles = motoConnection.connectedTiles;
//
//        //Assume 4 tiles are connect
//        if (connectedTiles.size() != 4) {
//            motoConnection.setAllTilesBlink(5, LED_COLOR_RED);
//        } else {
//            this.topTile = connectedTiles.get(0);
//            this.secondTop = connectedTiles.get(1);
//            this.thirdTop = connectedTiles.get(2);
//            this.bottomTile = connectedTiles.get(3);
//
//            //light tiles for user to organize correctly
//            motoConnection.setTileColor(LED_COLOR_RED, topTile);
//            motoConnection.setTileColor(LED_COLOR_BLUE, secondTop);
//            motoConnection.setTileColor(LED_COLOR_VIOLET, thirdTop);
//            motoConnection.setTileColor(LED_COLOR_GREEN, bottomTile);
//        }
//
//    }

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

//        super.onGameUpdate(message);
//        int tileId = AntData.getId(message);
//        int event = AntData.getCommand(message);

        // for ex: could be if used chose Level 1, GAME_SPEED = 1. I think this would go here?

//        final int GAME_SPEED = 1; // is this 1 sec?

//        boolean beginning_step = true;
//        boolean count0 = false;
//        boolean count1 = false;
//        boolean count2 = false;
//        boolean count3 = false;
//        boolean count4 = false;
//        boolean count5 = false;
//        boolean count6 = false;


//        if (event == EVENT_PRESS)
//        {
//            if(beginning_step && count6) {
//                //start over again
//                beginning_step = false;
//                count0 = true;
//                count1 = false;
//                count2 = false;
//                count3 = false;
//                count4 = false;
//                count5 = false;
//                count6 = false;
//
//            }

//            else if (beginning_step == true) // To check if thirdTop is pressed (aka where both feet start)
//            {
//                //maybe can say GREEN = both feet (Can list the instructions on the tablet, or just seperate like the exercises)
//                motoConnection.setTileColor(LED_COLOR_VIOLET, thirdTop);
//                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
//                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
//                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
//                count0 = true;
//                beginning_step = false;
//                //can give point here, (bc based on what I did you only move on if you hit the right step)
//                // ^^ because of what I said above the for loop idea is better? Or need to think of a better way to give points.
//            }

            System.out.println(counter % 6);

            // One - Right foot
            if ( counter % 6 == 0) //Right foot - RED - follow
            {
                motoConnection.setTileColor(LED_COLOR_RED,bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                count0 = false;
                count1 = true;
                //beginning_step = false;
            }

            //Two - Left foot
            else if (counter % 6 == 1)
            {
                motoConnection.setTileColor(LED_COLOR_GREEN,thirdTop); //Left foot taps - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count1 = false;
                count2 = true;
                //beginning_step = false;

                System.out.print("time in milliseconds = ");
                System.out.println(System.currentTimeMillis());
            }

            //Three - Right foot
            else if (counter % 6 == 2)
            {
                motoConnection.setTileColor(LED_COLOR_RED,secondTop); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count2 = false;
                count3 = true;
                //beginning_step = false;

                System.out.print("time in milliseconds = ");
                System.out.println(System.currentTimeMillis());
            }


            //Five - Left foot
            if (counter % 6 == 3)
            {
                motoConnection.setTileColor(LED_COLOR_GREEN,topTile); //Left foot - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                count3 = false;
                count4 = true;
                //beginning_step = false;
            }

            //Six - Right foot (tap again)
            if (counter % 6 == 4)
            {
                motoConnection.setTileColor(LED_COLOR_OFF,bottomTile); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_RED,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                count4 = false;
                count5 = true;
                //beginning_step = false;

            }

            //Seven - Left foot
            if (counter % 6 == 5) // To check if topTile tile has been pressed
            {
                motoConnection.setTileColor(LED_COLOR_GREEN,thirdTop); //Left foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF,secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF,bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF,topTile);
                count5 = false;
                count6 = true;
                //beginning_step = true;
            }

            counter += 1;


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

    //}

}
