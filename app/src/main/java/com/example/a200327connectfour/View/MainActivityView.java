package com.example.a200327connectfour.View;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a200327connectfour.MainActivity;
import com.example.a200327connectfour.R;

import com.example.a200327connectfour.ViewModel.ViewModel;


//Entry point for handling the VIEW by making the mainActivity.java clean and simple
// MainActivityDrawer handles the output part
// Inputs get handled here and routed to the ViewModel
// Placeholder variables are not set here, only "variable empty" messages or something, PHs into VM

//This class main purpose is to
/* 1. list all available inputs in first half of registerInputsToFunctions()
   2. route the inputs to functions  in second half of registerInputsToFunctions()
   3. specify the ViewModels action that will be used in the last part of the class
   4. The output and "variable empty" functions are separated into MainActivityDrawer
   5. This class calls the ViewModel
        (pattern: View(mainactivity plus mainactivityView)->ViewModel->MainModel->ModelParts(Savegame,Game,Multiplayer))
 */

public class MainActivityView implements View.OnClickListener {

    MainActivity activity;
    Context context;
    View view;
    MainActivityDrawer mainActivityDrawer;
    ViewModel viewModel;

    public MainActivityView(Context context, View view, MainActivity activity){
        this.activity=activity;
        this.context=context;
        this.view=view;
        mainActivityDrawer = new MainActivityDrawer(context, view);

        viewModel = new ViewModel(context);

        handleInputTest();
        registerInputsToFunctions();
        //map outputs?
    }


    @Override public void onClick(View view){
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }

    };
private void handleInputTest(){
    Button testBtn = (Button) view.findViewById(R.id.ButtonResumeLast);
    final TextView testText = (TextView) view.findViewById(R.id.textViewTempGrid);

    testBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context,"implemenatations",Toast.LENGTH_LONG).show();
            testText.setText("wtf!!!");
        }
    });
}

private void registerInputsToFunctions(){

    //List available Buttons:

    Button restartGameBtn = (Button) view.findViewById(R.id.ButtonRestart);
    Button column1Btn = (Button) view.findViewById(R.id.ButtonColumn1);
    Button column2Btn = (Button) view.findViewById(R.id.ButtonColumn2);
    Button column3Btn = (Button) view.findViewById(R.id.ButtonColumn3);
    Button column4Btn = (Button) view.findViewById(R.id.ButtonColumn4);
    Button column5Btn = (Button) view.findViewById(R.id.ButtonColumn5);
    Button column6Btn = (Button) view.findViewById(R.id.ButtonColumn6);
    Button column7Btn = (Button) view.findViewById(R.id.ButtonColumn7);
    Button resumeLastGameBtn = (Button) view.findViewById(R.id.ButtonResumeLast);
    Button dismissLastGameBtn = (Button) view.findViewById(R.id.ButtonDismissLast);
    Button switchColourBtn = (Button) view.findViewById(R.id.ButtonSwitchColour);

    //Map buttons to functions:
    //remember to update View after each button click

    restartGameBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onRestartGame();
        }
    });
    column1Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(1);
        }
    });
    column2Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(2);
        }
    });
    column3Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(3);
        }
    });
    column4Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(4);
        }
    });
    column5Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(5);
        }
    });
    column6Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(6);
        }
    });
    column7Btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onColumnClicked(7);
        }
    });

    resumeLastGameBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onResumeLastGame();
        }
    });
    switchColourBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onSwitchColour();
        }
    });

    dismissLastGameBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onDismissLastGame();
        }
    });

}



    // Connect ViewModel functions in the following rest of the class:

private void onRestartGame(){
    //add model connection here
}
private void onColumnClicked(int column){
    switch (column){
        case 1 : {
            //add model connection here
            break;
        }
        case 2: {
            break;
        }
        case 3: {
            break;
        }
        case 4: {
            break;
        }
        case 5:{
            break;
        }
        case 6:{
            break;
        }
        case 7:{
            break;
        }
        default:{
            //error - wrong column number given
        }
    }
}
private void onResumeLastGame(){

    //add model connection here
}
private void onSwitchColour(){

    //add model connection here
}
private void onDismissLastGame(){

    //add model connection here
}


}
