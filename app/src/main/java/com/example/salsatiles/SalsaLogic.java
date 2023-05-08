package com.example.salsatiles;

import static com.livelife.motolibrary.AntData.*;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import java.util.concurrent.TimeUnit;

import java.util.Arrays;


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

    int basicCounter = 0;
    int oneCounter = 0;

    int gameOnePressedCounter = 0;
    int gameOneLitCounter = 0;
    double [] timeLit = new double[6];
    double [] timeStep = new double[6];
    boolean gameOneInitial = true;

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
        } else if (this.selectedGameType.getName() == "Level 1: Basics") {
            try {
                salsaBasicInitial();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (gameOnePressedCounter == 6){
                timeStep[gameOnePressedCounter] = System.currentTimeMillis();
                gameOnePressedCounter = 0;


                    System.out.println("Total points: ");

                    System.out.println(pointSystem());


                Arrays.fill(timeLit, 0.0);
                Arrays.fill(timeStep, 0.0);

            } else {
                timeStep[gameOnePressedCounter] = System.currentTimeMillis();
                gameOnePressedCounter += 1;
            }
            System.out.println("Game one selected");
        }

    }



    @Override
    public void onGameEnd() {
        super.onGameEnd();
        //sound.speak("Your score was: " + this.getPlayerScore()[0]);
        motoConnection.setAllTilesIdle(LED_COLOR_BLUE);
    }


    public void salsaBasicInitial() throws InterruptedException {

        if (gameOneInitial) {
            gameOneInitial = false;

            for (int i = 0; i < 6; i++) {
                timeLit[i] = System.currentTimeMillis();

                System.out.println("the current step: " + i);

                if (i == 0) //Right foot - RED - follow
                {
                    System.out.println("print step one red ");
                    motoConnection.setTileColor(LED_COLOR_RED, bottomTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    TimeUnit.SECONDS.sleep(1);
                }

                //Two - Left foot
                else if (i == 1) {
                    motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot taps - GREEN - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                    TimeUnit.SECONDS.sleep(1);
                }

                //Three - Right foot
                else if (i == 2) {
                    motoConnection.setTileColor(LED_COLOR_RED, secondTop); //Right foot - RED - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                    TimeUnit.SECONDS.sleep(1);
                }

                //Five - Left foot
                if (i == 3) {
                    motoConnection.setTileColor(LED_COLOR_GREEN, topTile); //Left foot - GREEN - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                    TimeUnit.SECONDS.sleep(1);

                }

                //Six - Right foot (tap again)
                if (i == 4) {
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile); //Right foot - RED - follow
                    motoConnection.setTileColor(LED_COLOR_RED, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    TimeUnit.SECONDS.sleep(1);

                }
                //Seven - Left foot
                if (i == 5) // To check if topTile tile has been pressed
                {
                    motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot - RED - follow
                    motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                    motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                    motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                    TimeUnit.SECONDS.sleep(1);
                    gameOneInitial = true;
                }
//                TimeUnit.SECONDS.sleep(1);
            }

        }
    }

    public void salsaBasicStep() {

        System.out.println(basicCounter % 6);

        if (forward) {
            // One - Right foot
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
    }


    public void salsaBasicOne() {

        System.out.println(oneCounter % 6);

        if (forward) {
            // One - Right foot
            if (oneCounter % 6 == 0) //Right foot - RED - follow
            {
                motoConnection.setTileColor(LED_COLOR_RED, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                //wait(waitTime);

            }

            //Two - Left foot
            else if (oneCounter % 6 == 1) {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot taps - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                //wait(waitTime);

            }

            //Three - Right foot
            else if (oneCounter % 6 == 2) {
                motoConnection.setTileColor(LED_COLOR_RED, secondTop); //Right foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                //wait(waitTime);

            }


            //Five - Left foot
            if (oneCounter % 6 == 3) {
                motoConnection.setTileColor(LED_COLOR_GREEN, topTile); //Left foot - GREEN - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                //wait(waitTime);

            }

            //Six - Right foot (tap again)
            if (oneCounter % 6 == 4) {
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile); //Right foot - RED - follow
                motoConnection.setTileColor(LED_COLOR_RED, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, thirdTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                //wait(waitTime);

            }

            //Seven - Left foot
            if (oneCounter % 6 == 5) // To check if topTile tile has been pressed
            {
                motoConnection.setTileColor(LED_COLOR_GREEN, thirdTop); //Left foot - RED - follow
                motoConnection.setTileIdle(LED_COLOR_OFF, secondTop);
                motoConnection.setTileIdle(LED_COLOR_OFF, bottomTile);
                motoConnection.setTileIdle(LED_COLOR_OFF, topTile);
                //wait(waitTime);
            }

            oneCounter += 1;
        }
    }
        // --- CODE STARTS HERE ---

// When the game starts what can be done is that the player hits the start button. The user stands in the starting tile for three seconds,
// then the game actually starts when they hit the first count

        public double pointSystem() {

            double [] points = new double[6];

            //int numPractice = 5;
            //int[][] scores = new int[6][numPractice]; // initialize the array to store scores


                // Lighting the tiles according to FOLLOW steps

                // The user is going to tap in the same tile with both feet, will have to let them know this in the instructions
                // We won't use this time in the matrix
//                motoConnection.setTileColorCountdown(LED_COLOR_VIOLET,thirdTop,this.GAME_SPEED); // Start - Both feet
//                startingposition_lit = System.currentTimeMillis();
//
//                motoConnection.setTileColorCountdown(LED_COLOR_RED,bottomTile,this.GAME_SPEED); //ONE: Right foot - RED
//                one_lit = System.currentTimeMillis();
//
//                motoConnection.setTileColorCountdown(LED_COLOR_GREEN,thirdTop,this.GAME_SPEED); //TWO: Left foot taps - GREEN
//                two_lit = System.currentTimeMillis();
//
//                motoConnection.setTileColorCountdown(LED_COLOR_RED,secondTop,this.GAME_SPEED); //THREE: Right foot - RED
//                three_lit = System.currentTimeMillis();
//
//                motoConnection.setTileColorCountdown(LED_COLOR_GREEN,topTile,this.GAME_SPEED); //FIVE: Left foot - GREEN
//                five_lit = System.currentTimeMillis();
//
//                motoConnection.setTileColorCountdown(LED_COLOR_RED,secondTop,this.GAME_SPEED); //SIX: Right foot - RED  // TELL NIC OF THIS
//                six_lit = System.currentTimeMillis();
//
//                motoConnection.setTileColorCountdown(LED_COLOR_GREEN,thirdTop,this.GAME_SPEED); //SEVEN: Left foot - GREEN
//                seven_lit = System.currentTimeMillis();


        /*
        Ignore what I said below because it's a for loop
        // Let's have them end on ONE - to signify the end
        motoConnection.setTileColorCountdown(LED_COLOR_RED,bottomTile,this.GAME_SPEED); //ONE: Right foot - RED
        one_lit = SystemSystem.nanoTime();
        */

                // Need to make an array to store the scores otherwise it'll override it.
                // If not, can simply make it like this and then call the Point_system function three times from the Main or Salsa logic

                double totalPoints = 0;
                //int[] myIntArray = new int[practice*counts];

                for(int i=0;i<6;i++) {
                    totalPoints =+ (timeStep[i] - timeLit[i]) / GAME_SPEED;
                }

//                player_delay_c1 = one_pressed - one_lit;
//                player_delay1_c2 = two_pressed - two_lit;
//                player_delay1_c3 = three_pressed - three_lit;
//                player_delay1_c5 = five_pressed - five_lit;
//                player_delay1_c6 = six_pressed - six_lit;
//                player_delay1_c7 = seven_pressed - seven_lit;

                // normalize by level (AKA GAME_SPEED) by dividing by the game speed
//                double timeDiff1 = (double) (player_delay1_c2) / gameSpeed;
//                double timeDiff2 = (double) (player_delay1_c2) / gameSpeed;
//                double timeDiff3 = (double) (player_delay1_c3) / gameSpeed;
//                double timeDiff5 = (double) (player_delay1_c5) / gameSpeed;
//                double timeDiff6 = (double) (player_delay1_c6) / gameSpeed;
//                double timeDiff7 = (double) (player_delay1_c7) / gameSpeed;

                // int score_c1 = (int) (1000 / timeDiff1); // diving by 1000 since that's the max for points received
                // int score_c2 = (int) (1000 / timeDiff2);
                // int score_c3 = (int) (1000 / timeDiff3);
                // int score_c5 = (int) (1000 / timeDiff5);
                // int score_c6 = (int) (1000 / timeDiff6);
                // int score_c7 = (int) (1000 / timeDiff7);

                // diving by 1000 since that's the max for points received
//                scores[0][practice] = (int) (1000 / timeDiff1);
//                scores[1][practice] = (int) (1000 / timeDiff2);
//                scores[2][practice] = (int) (1000 / timeDiff3);
//                scores[3][practice] = (int) (1000 / timeDiff5);
//                scores[4][practice] = (int) (1000 / timeDiff6);
//                scores[5][practice] = (int) (1000 / timeDiff7);

                // TO DO:
                //include error handling for cases where the user does not tap on the correct tile or takes too long to tap on a tile!!!
                // if they hit it perfetly will need an if statement so that there is not system error

                return totalPoints;
            }

            // Calculate total scores for each practice round
//            int[] totalScores = new int[numPractice];
//            for (int practice = 0; practice < numPractice; practice++)
//            {
//                for (int count = 0; count < 6; count++)
//                {
//                    totalScores[practice] += scores[count][practice];
//                }
//            }
//
//            // Print the matrix and total scores for each practice round
//            for (int count = 0; count < 6; count++)
//            {
//                for (int practice = 0; practice < numPractice; practice++)
//                {
//                    System.out.print(scores[count][practice] + " ");
//                }
//                System.out.println();
//            }

            // for (int practice = 0; practice < numPractice; practice++)
            // {
            //     System.out.println("Total score for practice round " + (practice + 1) + ": " + totalScores[practice]);
            // }
    
// --- CODE ENDS HERE ---


    //}

}
