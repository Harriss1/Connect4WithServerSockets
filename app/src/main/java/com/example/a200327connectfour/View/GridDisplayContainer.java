package com.example.a200327connectfour.View;

import android.support.design.animation.Positioning;

import com.example.a200327connectfour.Model.Logging;

import java.util.ArrayList;

public class GridDisplayContainer {

    Logging log = new Logging("GridDisplayContainer.java");

    private String fieldOfPlayerOne="PlayerOne";
    private String fieldOfPlayerTwo="PlayerTwo";
    private String fieldNotOccupied="NotOccupied";
    private String playerOneColor;
    private String playerTwoColor;
    public final String colorBlue = "Blue";
    public final String colorRed = "Red";

    private class Position{
        int posX;
        int posY;
        String fieldOccupiedBy;
        boolean alreadyDrawn=false;
        boolean drawMoveAnimation=true;
    }

    private ArrayList<Position> playfieldGrid;

    public GridDisplayContainer(){
        firstLoad();
        playerOneColor=colorBlue;
        playerTwoColor=colorRed;
        //setPosition(2,3,getFieldOfPlayerOneString());
    }

    private void firstLoad(){
        playfieldGrid = new ArrayList<Position>();
        //our grid is 8 long and 7 high
        for(int x=1; x<=8;x++){
            for(int y=1; y<=7;y++){
                Position field=new Position();
                field.fieldOccupiedBy=fieldNotOccupied;
                field.posX=x;
                field.posY=y;
                playfieldGrid.add(field);
            }
        }
    }

    public void switchPlayerColor(){
        if(playerOneColor==colorBlue){
            playerOneColor=colorRed;
            playerTwoColor=colorBlue;
        } else {
            playerOneColor=colorBlue;
            playerTwoColor=colorRed;
        }
    }
    public String getPlayerOneColor(){ return playerOneColor;}
    public String getPlayerTwoColor(){ return playerTwoColor;}

    public String getFieldOfPlayerOneString(){
        return fieldOfPlayerOne;
    }
    public String getFieldOfPlayerTwoString(){
        return fieldOfPlayerTwo;
    }
    public String getFieldNotOccupiedString(){
        return fieldNotOccupied;
    }

    public String getFieldPositionContentAt(int x, int y){
        for (Position e:playfieldGrid
        ) {
            if(e.posY==y && e.posX==x){
                return e.fieldOccupiedBy;
            }
        }
        return fieldNotOccupied;
    }
    public boolean getFieldPositionAnimation(int x, int y){
        for (Position e:playfieldGrid
        ) {
            if(e.posY==y && e.posX==x){
                //log.add("getFieldPositionAnimation():"+Boolean.toString(e.drawMoveAnimation));
                return e.drawMoveAnimation;

            }
        }
        return true;
    }

    public void setPosition(int posX, int posY, String content){
        //check if content valid
        if((!(content.equals(fieldOfPlayerOne)))&&(!(content.equals(fieldOfPlayerTwo)))&&(!(content.equals(fieldNotOccupied))))
        {
            //error, wrong content input
            return;
        }
        if(posX<1 || posX >8 || posY < 1 || posY >7){
            //error, position out of bounds
            return;
        }
        for (Position e:playfieldGrid) {
            if(e.posY==posY && e.posX==posX){
                e.fieldOccupiedBy=content;
            }
        }
    }

    public void setDrawAnimationToPosition(int posX, int posY){
        setDrawAnimationOnAllPosOff();
        for (Position e:playfieldGrid) {
            if(e.posY==posY && e.posX==posX){
                e.drawMoveAnimation=true;
            }
        }
    }
    public void setDrawAnimationOnAllPosOff(){
        for (Position e:playfieldGrid) {
            e.drawMoveAnimation=false;
        }
    }
// static element array - get initialised with moves not set at start
//
// setElementAsNewMove (one time usage), setElementFromHistory (for savegame loading),
// setMoveAnimationOnElement(only pos, not colour - must already be set),
// setMoveAnimationOnLatestMove (true/false, sets to true without argument),
//
// getElementArray, getNewestElement, getElementColour, getElementPos


}
