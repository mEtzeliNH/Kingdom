package com.example.michael.kingdom.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.michael.kingdom.R;
import com.example.michael.kingdom.fragments.GameButtonsFragment;
import com.example.michael.kingdom.fragments.GameScreenFragment;
import com.example.michael.kingdom.fragments.GameStateFragment;
import com.example.michael.kingdom.interfaces.GameStateInterface;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class GameActivity extends FragmentActivity implements GameStateInterface {

    private GameStateFragment gameStateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameStateFragment = new GameStateFragment();
        GameScreenFragment gameScreenFragment = new GameScreenFragment();
        GameButtonsFragment gameButtonsFragment = new GameButtonsFragment();

        Bundle extras = getIntent().getExtras();

        // only try to retrieve data if GameData has been passed (user clicks 'continue game')
        if(extras != null) {
            ArrayList<String> gameData = extras.getStringArrayList("data");

            // set data on GameStateFragment
            gameStateFragment.setData(gameData);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.game_state_fragment, gameStateFragment)
                .replace(R.id.game_screen_fragment, gameScreenFragment)
                .replace(R.id.game_buttons_fragment, gameButtonsFragment)
                .commit();
    }

    @Override
    public void updateFoodPerTurnByAmount(int fpt) {
        gameStateFragment.updateFoodPerTurn(fpt);
    }

    @Override
    public void updateTotalFoodByAmount(int tf) {
        gameStateFragment.updateTotalFood(tf);
    }

    @Override
    public void updateTotalVillagesByAmount(int tv) {
        gameStateFragment.updateTotalVillages(tv);
    }

    @Override
    public void updateTotalSoldiersByAmount(int ts) {
        gameStateFragment.updateTotalSoldiers(ts);
    }

    // save the game state to a file.
    // elements in array are foodPerTurn, totalFood, totalVilllages, totalSoldiers
    // in that order specifically
    public void save(String[] values) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = openFileOutput(getResources().getString(R.string.save_file_name), MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            StringBuilder toastData = new StringBuilder();

            for(String value : values) {
                bufferedWriter.write(value);
                bufferedWriter.newLine();

                toastData.append(value);
                toastData.append(":");
            }

            Toast.makeText(this, "Data has been saved: " + toastData.toString(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onBackPressed() {
        String[] data = gameStateFragment.getDataFromViews();
        save(data);

        // exit app
        Intent homeScreen = new Intent(Intent.ACTION_MAIN);
        homeScreen.addCategory(Intent.CATEGORY_HOME);
        homeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeScreen);
    }
}
