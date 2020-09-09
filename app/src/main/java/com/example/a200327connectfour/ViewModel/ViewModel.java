package com.example.a200327connectfour.ViewModel;

import android.content.Context;

import com.example.a200327connectfour.Model.Game.GridContainer;
import com.example.a200327connectfour.Model.Logging;
import com.example.a200327connectfour.Model.MainModel;


public class ViewModel {

    Logging log = new Logging("ViewModel.java");

    Context context;
    GridContainer gridContainer;
    MainModel mainModel;
    UIDataContainer uiDataContainer=new UIDataContainer();

    private static boolean checkFirstLoad = true;

    public ViewModel(Context context){
        this.context = context;
        mainModel = new MainModel(context);
        gridContainer = mainModel.getGridContainer();
        displayResumeOptionOnAppStart();
        updateUIData();
    }

    private void updateUIData() {
        String userMessage=mainModel.getGameInfoString();

        userMessage=userMessage.replaceAll(gridContainer.player1Label,"Blue");
        userMessage=userMessage.replaceAll(gridContainer.player2Label, "Red");

        if(userMessage.contains("no Winner")){
            //the game is not won jet
            uiDataContainer.winnerIndicator="none";
        } else {
            //win logic here
            if(userMessage.contains("Blue wins the game.")){
                uiDataContainer.winnerIndicator="Blue";
                userMessage="Blue wins the game.";
            }
            if(userMessage.contains("Red wins the game.")){
                uiDataContainer.winnerIndicator="Red";
                userMessage="Red wins the game.";
            }
        }
        //log.add("updateUIData:userMessage="+userMessage);
        uiDataContainer.userMessage =userMessage;
    }

    private void displayResumeOptionOnAppStart(){
        //if the app gets started or if we have a screen orientation change...
        //problem: detect simple orientation change?
        if(!checkFirstLoad) return;
        else checkFirstLoad = false;


    }

    public void onColumnClick(int column){
        //column 1-7
        if(column < 1 || column > 7) {
            //add possible error message here
            return;
        }

        //add model connection here
    }

    public String getGridContentAt(int x, int y){
        return gridContainer.getPositionContentStringAt(x,y);
    }
    public String getPlayer1Label(){
        return mainModel.getGridContainer().player1Label;
    }
    public String getPlayer2Label(){
        return mainModel.getGridContainer().player2Label;
    }
    public String getPositionEmptyLabel() {
        return mainModel.getGridContainer().positionEmptyLabel;
    }

    public void setEventMoveAt(int yCoord){
        mainModel.setGameEventMoveAt(yCoord);
        updateUIData();
    }


    public UIDataContainer getUIData(){
        UIDataContainer uiDataContainer = new UIDataContainer();

        return uiDataContainer;
    }

    public void setEventResumeLastGame(){
        //mainModel.testSaveGame();

        mainModel.gameResumeOldGame();
        updateUIData();
    }

    public void setEventSwitchColor(){
        //rename later on
        mainModel.gameSwitchColor();
        updateUIData();
    }

    public void setEventAppOnStart(){
        mainModel.gameOnAppStart();
        updateUIData();
    }

    public void setEventAppOnCreateButSavedInstanceExists(){
        //100 ways of Android to kill the app memory even though its still active will just be reloaded here...
        mainModel.gameLoadInProgressGameAfterMemoryLoss();
        updateUIData();
    }

    public String getSaveTestString(){
        return mainModel.testSaveGame();
    }

    public void setEventRestartGame(){
        mainModel.gameRestartGame();
        updateUIData();
    }

    public void setEventTestLoad() {
        mainModel.gameTestLoad();
        updateUIData();
    }

    public void setEventTestSave() {
        mainModel.gameTestSave();
        updateUIData();
    }

    public void setEventDismissGame() {
        //there is actually no model connection needed, only disabling the message in view
        //function does nothing
        mainModel.gameDismissOldGame();
        updateUIData();
    }
    public int getMoveToAnimateY(){
        return mainModel.gameGetLastMoveY();
    }
    public int getMoveToAnimateX(){
        return mainModel.gameGetLastMoveX();
    }
}
