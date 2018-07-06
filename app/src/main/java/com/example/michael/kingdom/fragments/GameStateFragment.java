package com.example.michael.kingdom.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.michael.kingdom.R;

import java.util.ArrayList;

public class GameStateFragment extends Fragment {

    private TextView foodPerTurn;
    private TextView totalFood;
    private TextView totalVillages;
    private TextView totalSoldiers;
    private ArrayList<String> data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_state_fragment, container, false);

        foodPerTurn = v.findViewById(R.id.food_per_turn_value);
        totalFood = v.findViewById(R.id.total_food_value);
        totalVillages = v.findViewById(R.id.total_villages_value);
        totalSoldiers = v.findViewById(R.id.total_soldiers_value);

        if(data != null) {
            setViewsWithData(data);
        }

        return v;
    }

    public void updateFoodPerTurn(int fpt) {
        int currentFPT = Integer.parseInt(foodPerTurn.getText().toString());
        foodPerTurn.setText(String.valueOf(currentFPT + fpt));
    }

    public void updateTotalFood(int tf) {
        int currentTF = Integer.parseInt(totalFood.getText().toString());
        totalFood.setText(String.valueOf(currentTF + tf));
    }

    public void updateTotalVillages(int tv) {
        int currentTV = Integer.parseInt(totalVillages.getText().toString());
        totalVillages.setText(String.valueOf(currentTV + tv));
    }

    public void updateTotalSoldiers(int ts) {
        int currentTS = Integer.parseInt(totalSoldiers.getText().toString());
        totalSoldiers.setText(String.valueOf(currentTS + ts));
    }

    public String[] getDataFromViews() {
        String[] data = new String[4];
        data[0] = foodPerTurn.getText().toString();
        data[1] = totalFood.getText().toString();
        data[2] = totalVillages.getText().toString();
        data[3] = totalSoldiers.getText().toString();

        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void setViewsWithData(ArrayList<String> data) {
        int dataIndex = 0;
        while(dataIndex < data.size()) {
            switch(dataIndex) {
                case 0: {
                    foodPerTurn.setText(data.get(dataIndex));
                    dataIndex ++;
                    break;
                }
                case 1: {
                    totalFood.setText(data.get(dataIndex));
                    dataIndex ++;
                    break;
                }
                case 2: {
                    totalVillages.setText(data.get(dataIndex));
                    dataIndex ++;
                    break;
                }
                case 3: {
                    totalSoldiers.setText(data.get(dataIndex));
                    dataIndex ++;
                    break;
                }
            }
        }
    }
}
