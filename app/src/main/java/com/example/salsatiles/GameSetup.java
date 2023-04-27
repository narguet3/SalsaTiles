package com.example.salsatiles;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
// Colors for setup. Idk if this is how you get the colors, since I remember they had numbers assigned to them
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_VIOLET;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;

import android.util.Log; // from Ex 6 prob don't need it
import java.util.Random; // from Ex 6 prob don't need it

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

// Pair the tiles by stepping on them
// Idea is that you'll set up the tiles then similar to before they'll flash red
// So maybe you set them up then send the signal to the tiles
// But I'm thinking it's better to flash the tile, then have the user put it in a certain position

public class GameSetup extends Game {
    MotoConnection connection = MotoConnection.getInstance();

    // Declared private to have access to them through the rest of the class
    private int bottomTileId;
    private int secondTileId;
    private int thirdTileId;
    private int topTileId;

    // Method to get the tile IDs
    public void getTileIds() {
        bottomTileId = connection.getTileId(1); // Tile number 1 (bottom tile)
        secondTileId = connection.getTileId(2); // Tile number 2 (second from bottom)
        thirdTileId = connection.getTileId(3); // Tile number 3 (second from top)
        topTileId = connection.getTileId(4); // Tile number 4 (top tile)
    }

    // Set the LED colors for user to put titles in correct order
    connection.setTileColor(AntData.LED_COLOR_BLUE, bottomTileId);
    connection.setTileColor(AntData.LED_COLOR_VIOLET, bottomTileId);
    connection.setTileColor(AntData.LED_COLOR_GREEN, bottomTileId);
    connection.setTileColor(AntData.LED_COLOR_RED, bottomTileId);

    // connection.setLedColor(bottomTileId, MotoConnection.BLUE); <-- I don't think this setup was right

}



