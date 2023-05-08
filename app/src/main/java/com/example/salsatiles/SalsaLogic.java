package com.example.salsatiles;

import static com.livelife.motolibrary.AntData.*;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;

public class SalsaLogic extends Game
{
    MotoConnection motoConnection = MotoConnection.getInstance();
    final int BLINK = 2;
    int random_tile_id;
    int hitColor;
    public int r_color;

    int GAME_SPEED = 5;
    boolean beginning_step = true;
    int fcounter = 0;
    public int topTile;
    public int secondTop;
    public int thirdTop;
    public int bottomTile;

    int basicCounter = 0;
    int oneCounter = 0;

    int gamePressedCounter = 0;
    double [] timeLit = new double[18];
    double [] timeStep = new double[18];
    boolean gameOneInitial = true;

    boolean forward;
    boolean backward;

    ArrayList<Double> basicSteps = new ArrayList<Double>();
    ArrayList<Double> turnSteps = new ArrayList<Double>();

    SalsaLogic()
    {
        setName("Salsa Game");

        GameType gt = new GameType(0, GameType.GAME_TYPE_SPEED, 30,"Play Level 1: Basics",1);
        addGameType(gt);

        GameType gt1 = new GameType(1, GameType.GAME_TYPE_SPEED, 30,"Play Level 1: Basic + Turn",1);
        addGameType(gt1);

        GameType gt2 = new GameType(2, GameType.GAME_TYPE_SPEED, 60,"Learn the Basic",1);
        addGameType(gt2);

        GameType gt3 = new GameType(3, GameType.GAME_TYPE_SPEED, 120,"Learn the Basic + Turn",1);
        addGameType(gt3);

    }

    public void setTileOrientation (int t, int t1, int t2, int b) {
        this.topTile = t;
        this.secondTop = t1;
        this.thirdTop = t2;
        this.bottomTile = b;
    }


    @Override
    public void onGameStart() {
        super.onGameStart();

        motoConnection.setAllTilesIdle(LED_COLOR_OFF);
        motoConnection.setTileColor(LED_COLOR_WHITE, thirdTop);
    }

    @Override
    public void onGameUpdate(byte[] message) {

        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);

        System.out.println("Game info: ");
        System.out.println(this.selectedGameType.getName());
        System.out.println(event);

        double score = 0;
        if (event == EVENT_PRESS) {
            if (this.selectedGameType.getName() == "Play Level 1: Basics") {
                if (basicSteps.size() == 18) {
                    score = pointSystem(basicSteps);
                    incrementPlayerScore((int)score, 1);
                    System.out.println(score);

                    basicSteps.clear();
                    onGameEnd();
                } else {
                    salsaBasicStep();
                    basicSteps.add((double) System.currentTimeMillis());
                }
            }
            else if(this.selectedGameType.getName() == "Play Level 1: Basic + Turn") {
                if (turnSteps.size() == 18) {
                    score = pointSystem(turnSteps);
                    incrementPlayerScore((int)score, 1);
                    System.out.println(pointSystem(turnSteps));

                    turnSteps.clear();
                    onGameEnd();
                } else {
                    salsaBasicTurn();
                    turnSteps.add((double) System.currentTimeMillis());

                    System.out.println("Size of basic turn array: " + turnSteps.size());
                }
            }
            else if (this.selectedGameType.getName() == "Learn the Basic") {
                if (gamePressedCounter == 17 ) {
                    timeStep[gamePressedCounter] = System.currentTimeMillis();
                    gamePressedCounter = 0;
                    //System.out.println(pointSystem(this.timeLit, this.timeStep));
                } else {
                    timeStep[gamePressedCounter] = System.currentTimeMillis();
                }
                System.out.println("Number of times pressed: " + gamePressedCounter);
                gamePressedCounter++;
            } else if (this.selectedGameType.getName() =="Learn the Basic + Turn") {
                if (gamePressedCounter == 17 ) {
                    timeStep[gamePressedCounter] = System.currentTimeMillis();
                    gamePressedCounter = 0;
                    //System.out.println(pointSystem(this.timeLit, this.timeStep));
                } else {
                    timeStep[gamePressedCounter] = System.currentTimeMillis();
                }
                System.out.println("Number of times pressed: " + gamePressedCounter);
                gamePressedCounter++;
            }
        }
    }



    @Override
    public void onGameEnd() {
        super.onGameEnd();
        //sound.speak("Your score was: " + this.getPlayerScore()[0]);
        motoConnection.setAllTilesIdle(LED_COLOR_BLUE);
    }

    public void setLitArray(double[] litArray) {
        this.timeLit = litArray;
    }


    public void salsaBasicStep() {

            if (basicCounter % 6 == 0) //Right foot - RED - follow
            {
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

            }

            //Two - Left foot
            else if (basicCounter % 6 == 1) {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot taps - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


            }

            //Three - Right foot
            else if (basicCounter % 6 == 2) {
                motoConnection.setTileColor(LED_COLOR_RED, secondTop); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);


            }


            //Five - Left foot
            if (basicCounter % 6 == 3) {
                motoConnection.setTileColor(LED_COLOR_GREEN, topTile); //Left foot - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);

            }

            //Six - Right foot (tap again)
            if (basicCounter % 6 == 4) {
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile); //Right foot - RED - follow
                motoConnection.setTileColor(LED_COLOR_RED, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);


            }

            //Seven - Left foot
            if (basicCounter % 6 == 5) // To check if topTile tile has been pressed
            {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);

            }

            basicCounter += 1;
    }


    public void salsaBasicTurn() {


        System.out.println(fcounter % 9);

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

            // PIVOT point - BLUE BLUE
            if (fcounter % 9 == 5) {
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileColor(LED_COLOR_BLUE, secondTop); // BLUE
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileColor(LED_COLOR_BLUE, topTile); // BLUE
            }

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
                System.out.println("two blue on");
            }

            // End in one for TURN
            // One - Right foot
            if (fcounter % 9 == 8) //Right foot - RED - follow
            {


                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);

            }

        fcounter += 1;

    }

        public double pointSystem(ArrayList<Double> timeSteps) {

            double [] points = new double[18];


                double totalPoints = 0;
                //int[] myIntArray = new int[practice*counts];

                for(int i=1;i<18;i++) {
                    totalPoints =+ (timeSteps.get(i) - timeSteps.get(i-1));
                }

                return 10000/totalPoints;
            }


}
