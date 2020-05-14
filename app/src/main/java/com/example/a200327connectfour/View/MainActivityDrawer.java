package com.example.a200327connectfour.View;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.a200327connectfour.R;

public class MainActivityDrawer {

    View view;
    Context context;
    GridDisplay gridDisplay;

    public MainActivityDrawer(Context context, View view){
        this.context=context;
        this.view=view;

        gridDisplay = new GridDisplay();
        initialDraw();
    }

    private void initialDraw(){
        drawGridAsText();
    }

    private void drawGridAsText(){
        //for development, to set up a simple view first
        TextView testIt = (TextView) view.findViewById(R.id.textViewTempGrid);
        testIt.setText("hallehui");

        gridDisplay.setPosition(6,2,gridDisplay.getFieldOfPlayerTwoString());
        String gridAsText="";
        for (int y=7; y>=1; y--){
            for (int x=1; x<=8; x++){
                String addChar="###";
                if(gridDisplay.getFieldPositionContentAt(x,y).equals(gridDisplay.getFieldNotOccupiedString())){
                    addChar=".";
                }
                if(gridDisplay.getFieldPositionContentAt(x,y).equals(gridDisplay.getFieldOfPlayerOneString()))
                {
                    addChar="B";
                }
                if(gridDisplay.getFieldPositionContentAt(x,y).equals(gridDisplay.getFieldOfPlayerTwoString()))
                {
                    addChar="R";
                }
                gridAsText+=addChar;
                if(x==8)gridAsText+="\n";
            }
        }
        testIt.setText(gridAsText);
    }
    private void updateMainActivityView(){

    }
}
