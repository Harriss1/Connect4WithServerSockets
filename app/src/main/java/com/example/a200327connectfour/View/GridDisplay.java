package com.example.a200327connectfour.View;

import android.support.design.animation.Positioning;

import java.util.ArrayList;

public class GridDisplay {

    private String fieldOfPlayerOne="PlayerOne";
    private String fieldOfPlayerTwo="PlayerTwo";
    private String fieldNotOccupied="NotOccupied";

    private class Position{
        int posX;
        int posY;
        String fieldOccupiedBy;
        boolean alreadyDrawn=false;
        boolean drawMoveAnimation=false;
    }

    private ArrayList<Position> playfieldGrid;

    public GridDisplay(){
        firstLoad();
        setPosition(2,3,getFieldOfPlayerTwoString());
        Position test=new Position();
        test.posY=4;
        test.posX=4;
        test.fieldOccupiedBy=fieldOfPlayerTwo;
        playfieldGrid.set(3,test);
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

    public void setPosition(int posX, int posY, String content){
        //check if content valid
        if(!(content.equals(fieldOfPlayerOne))&&!(content.equals(fieldOfPlayerTwo)))
        {
            //error, wrong content input
            return;
        }
        for (Position e:playfieldGrid) {
            if(e.posY==posY && e.posX==posX){
                e.fieldOccupiedBy=getFieldOfPlayerOneString();
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
