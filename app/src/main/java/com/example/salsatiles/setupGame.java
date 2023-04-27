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

public class setupGame extends AppCompatActivity implements OnAntEventListener {

    TextView tileSetupText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_game);

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
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }
}