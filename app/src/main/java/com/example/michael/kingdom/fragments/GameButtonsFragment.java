package com.example.michael.kingdom.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.michael.kingdom.R;
import com.example.michael.kingdom.interfaces.GameStateInterface;

public class GameButtonsFragment extends Fragment {

    private GameStateInterface gameStateInterface;
    private String[] actionSets = new String[] {"villages and food per turn.", "total food and soldiers."};
    private int actionIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_buttons_fragment, container, false);

        final Button leftActionButton = v.findViewById(R.id.button_left_action);
        final Button rightActionButton = v.findViewById(R.id.button_right_action);

        // default actions for the left and right buttons
        mapContextActionsForVillagesAndFoodPerTurn(leftActionButton, rightActionButton);

        // click this button to change what the left and right buttons do
        Button switchActionsButton = v.findViewById(R.id.button_switch_action);

        switchActionsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String action = null;
                switch(actionIndex) {
                   case 0: {
                        actionIndex = 1;
                        mapContextActionsForFoodAndSoldiers(leftActionButton, rightActionButton);
                        action = actionSets[actionIndex];
                        break;
                   }
                    case 1: {
                        actionIndex = 0;
                        mapContextActionsForVillagesAndFoodPerTurn(leftActionButton, rightActionButton);
                        action = actionSets[actionIndex];
                        break;
                    }
                }

                Toast.makeText(getContext(), "The buttons now affect the " + action, Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    public void mapContextActionsForVillagesAndFoodPerTurn(Button leftButton, Button rightButton) {
        // update button labels
        leftButton.setText(R.string.left_action_increase_food_per_turn);
        rightButton.setText(R.string.right_action_increase_villages);

        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gameStateInterface.updateFoodPerTurnByAmount(5);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gameStateInterface.updateTotalVillagesByAmount(1);
            }
        });
    }

    public void mapContextActionsForFoodAndSoldiers(Button leftButton, Button rightButton) {
        // update button labels
        leftButton.setText(R.string.left_action_increase_total_food);
        rightButton.setText(R.string.right_action_increase_total_soldiers);

        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gameStateInterface.updateTotalFoodByAmount(5);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gameStateInterface.updateTotalSoldiersByAmount(50);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof GameStateInterface) {
            gameStateInterface = (GameStateInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GameStateInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        gameStateInterface = null;
    }
}