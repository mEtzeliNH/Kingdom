package com.example.michael.kingdom.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.michael.kingdom.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button button_new_game = findViewById(R.id.button_new_game);
        Button button_continue_game = findViewById(R.id.button_continue_game);

        button_new_game.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // overwrite game data
               overwriteFile();

               // start game
               startNewGame(v);
           }
        });

        button_continue_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // load file data
                ArrayList<String> data = load();
                loadCurrentGame(v, data);
            }
        });
    }

    public void startNewGame(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void loadCurrentGame(View v, ArrayList<String> data) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    public ArrayList<String> load() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = openFileInput(getResources().getString(R.string.save_file_name));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            ArrayList<String> data = new ArrayList<>();

            String line;
            while((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }

            // send file data to GameActivity
            return data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public void overwriteFile() {
        File file = new File(getApplicationContext().getFilesDir(),getResources().getString(R.string.save_file_name));
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
