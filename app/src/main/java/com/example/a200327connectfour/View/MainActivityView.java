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
/* 1. list all available inputs in first half of handleInputsAndUpdateView();
   2. route the inputs to id-names  in second half of handleInputsAndUpdateView();
   3. specify the ViewModels action that will be used in userInterfaceActionSwitch(String actionName)
   4. invoke an MainActivityView update of all or just recently changed ui-elements
    (The output and "variable empty" functions are separated into MainActivityDrawer)
   5. This class calls the ViewModel
        (pattern: View(mainactivity plus mainactivityView)->ViewModel->MainModel->ModelParts(SaveGame,Game,Multiplayer))
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

        viewModel = new ViewModel(context);

        mainActivityDrawer = new MainActivityDrawer(context, view, viewModel);


        //handleInputTest();
        handleInputsAndUpdateView();
        //map outputs?

    }


    @Override public void onClick(View view){
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }

    };

    public void setEventAppFirstLoad(){
        userInterfaceActionSwitchAndViewUpdate("AppFirstLoad");
    }
    public void setEventAppOnCreateButInstanceStateExists() {
        userInterfaceActionSwitchAndViewUpdate("AppOnCreateActiveInstance");
    }





    private void handleInputsAndUpdateView(){

        // 1. list functions,
        // 2. map via setOnClicklistener to userInterfaceActionSwitchAndViewUpdate(UIactionID)
        // 3. updateView happens in userInterfaceActionSwitchAndViewUpdate

        //List available Buttons:

        Button restartGameBtn = (Button) view.findViewById(R.id.ButtonRestart);
        Button column1Btn = (Button) view.findViewById(R.id.ButtonColumn1);
        Button column2Btn = (Button) view.findViewById(R.id.ButtonColumn2);
        Button column3Btn = (Button) view.findViewById(R.id.ButtonColumn3);
        Button column4Btn = (Button) view.findViewById(R.id.ButtonColumn4);
        Button column5Btn = (Button) view.findViewById(R.id.ButtonColumn5);
        Button column6Btn = (Button) view.findViewById(R.id.ButtonColumn6);
        Button column7Btn = (Button) view.findViewById(R.id.ButtonColumn7);
        Button column8Btn = (Button) view.findViewById(R.id.ButtonColumn8);
        Button resumeLastGameBtn = (Button) view.findViewById(R.id.ButtonResumeLast);
        Button dismissLastGameBtn = (Button) view.findViewById(R.id.ButtonDismissLast);
        Button switchColourBtn = (Button) view.findViewById(R.id.ButtonSwitchColour);

        Button testLoadBtn = (Button) view.findViewById(R.id.ButtonTestLoad);
        Button testSaveBtn = (Button) view.findViewById(R.id.ButtonTestSave);

        //Map buttons to switchFunction

        restartGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("restartGameClicked");
            }
        });
        column1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column1clicked");
            }
        });
        column2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column2clicked");
            }
        });
        column3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column3clicked");
            }
        });
        column4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column4clicked");
            }
        });
        column5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column5clicked");
            }
        });
        column6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column6clicked");
            }
        });
        column7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column7clicked");
            }
        });

        column8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("column8clicked");
            }
        });

        resumeLastGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("resumeLastGameClicked");
            }
        });
        switchColourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("switchColorClicked");
            }
        });

        dismissLastGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("dismissLastGameClicked");
            }
        });

        testLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("testLoadClicked");
            }
        });
        testSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterfaceActionSwitchAndViewUpdate("testSaveClicked");
            }
        });
    }

    private void userInterfaceActionSwitchAndViewUpdate(String actionName){

        switch (actionName){
            case "column1clicked" : {
                onColumnClicked(1);
                break;
            }
            case "column2clicked" : {
                onColumnClicked(2);
                break;
            }
            case "column3clicked" : {
                onColumnClicked(3);
                break;
            }
            case "column4clicked" :{
                onColumnClicked(4);
                break;
            }
            case "column5clicked" : {
                onColumnClicked(5);
                break;
            }
            case "column6clicked" : {
                onColumnClicked(6);
                break;
            }
            case "column7clicked" : {
                onColumnClicked(7);
                break;
            }
            case "column8clicked" : {
                onColumnClicked(8);
                break;
            }
            case "restartGameClicked" : {
                onRestartGame();
                break;
            }
            case "resumeLastGameClicked":{
                onResumeLastGame();
                break;
            }
            case "switchColorClicked":{
                onSwitchColour();
                break;
            }
            case "dismissLastGameClicked":{
                onDismissLastGame();
                break;
            }
            case "testLoadClicked":{
                onTestLoadClicked();
                break;
            }
            case "testSaveClicked":{
                onTestSaveClicked();
                break;
            }
            case "AppFirstLoad" :{
                onApplicationStart();
                break;
            }
            case "AppOnCreateActiveInstance":{
                onAppRestartWithActiveInstance();
                break;
            }

            default: handleUndefinedAction();
        }

    mainActivityDrawer.updateView();
}

    private void handleUndefinedAction(){

}

    // Connect ViewModel functions in the following rest of the class:

    private void onApplicationStart(){
        //add ability to show "resume old game message here"
        viewModel.setEventAppOnStart();

    }

    private void onAppRestartWithActiveInstance(){
        //every case of orientation change, app killing memory or config change will be handled here
        viewModel.setEventAppOnCreateButSavedInstanceExists();


    }

    private void onRestartGame(){
        Toast.makeText(context,"Restarted Game",Toast.LENGTH_LONG).show();
        viewModel.setEventRestartGame();
        ((Button) view.findViewById(R.id.ButtonRestart)).setVisibility(View.INVISIBLE);

        //add model connection here
    }
    private void onColumnClicked(int column){
        switch (column){
            case 1 : {
                //add model connection here

                //Toast.makeText(context,"implemented button",Toast.LENGTH_LONG).show();

                viewModel.setEventMoveAt(1);
                break;
            }
            case 2: {
                viewModel.setEventMoveAt(2);
                break;
            }
            case 3: {
                viewModel.setEventMoveAt(3);
                break;
            }
            case 4: {
                viewModel.setEventMoveAt(4);
                break;
            }
            case 5:{
                viewModel.setEventMoveAt(5);
                break;
            }
            case 6:{
                viewModel.setEventMoveAt(6);
                break;
            }
            case 7:{
                viewModel.setEventMoveAt(7);
                break;
            }
            case 8:{
                viewModel.setEventMoveAt(8);
                break;
            }
            default:{
                //error - wrong column number given
            }
        }
    }
    private void onResumeLastGame(){

        viewModel.setEventResumeLastGame();
        MainActivityDisplayContainer dc = new MainActivityDisplayContainer();
        dc.setShowResumeLastGameOption(false);
        String testString = viewModel.getSaveTestString();
        dc.setDebugString(testString);
        Toast.makeText(context, "Resumed Last Game", Toast.LENGTH_LONG).show();
        //add model connection here
    }
    private void onSwitchColour(){

        //add model connection here

        //in development mode I load old savegame after app restart with this button
        //has to be put in to on resumelastgame later on
        viewModel.setEventSwitchColor();
        mainActivityDrawer.switchPlayerColor();
        mainActivityDrawer.updateView();

    }
    private void onDismissLastGame(){
        //function does nothing
        viewModel.setEventDismissGame();

        //disable view of buttons
        view.findViewById(R.id.ButtonDismissLast).setVisibility(View.GONE);
        view.findViewById(R.id.ButtonResumeLast).setVisibility(View.GONE);
        view.findViewById(R.id.textViewResumePrompt).setVisibility(View.GONE);
        MainActivityDisplayContainer dc = new MainActivityDisplayContainer();
        dc.setShowResumeLastGameOption(false);
    }

    private void onTestLoadClicked(){
        viewModel.setEventTestLoad();

    }
    private void onTestSaveClicked() {
        viewModel.setEventTestSave();
    }

    public void popupMenuAction(String menuID){
        switch (menuID){
            case "About" : {
                Toast.makeText(context, "By Karl Klotz, September 2020", Toast.LENGTH_LONG).show();
                break;
            }
            case "Restart Game" : {
                userInterfaceActionSwitchAndViewUpdate("restartGameClicked");
                break;
            }
            case "Resume Last Game" :{
                userInterfaceActionSwitchAndViewUpdate("resumeLastGameClicked");
                break;
            }
            case "Developer Mode" :{
                Toast.makeText(context, "Dev Mode Not Implemented", Toast.LENGTH_LONG).show();
            }
        }
    }



    /*
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
*/
}
