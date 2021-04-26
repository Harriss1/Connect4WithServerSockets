package com.example.a200327connectfour.View;


//holds variables to display to User in MainActivity

public class MainActivityDisplayContainer {

    private String userMessage;
    private static String debugMesssage="";
    private static boolean showResumeLastGameOption=true;
    private static String winnerIndicator="none";

    public MainActivityDisplayContainer(){

    }

    public void setDebugString(String debugString){
        debugMesssage = debugString;
    }

    public String getDebugMesssage(){
        return debugMesssage;
    }

    public boolean getShowResumeLastGameOption(){
        return showResumeLastGameOption;
    }
    public void setShowResumeLastGameOption(boolean showResumeLastGameOption){
        this.showResumeLastGameOption=showResumeLastGameOption;
    }
    public void setWinnerIndicator(String winner){
        this.winnerIndicator=winner;
    }
    public String getWinnerIndicator(){
        return winnerIndicator;
    }
}
