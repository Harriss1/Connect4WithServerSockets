package com.example.a200327connectfour.Model.Game;

import com.example.a200327connectfour.Model.Logging;

import java.util.ArrayList;

public class GridContainer {
    //this class handles the grid variables of an in progress game; 7 y-rows and 8 x-columns
    //neither does it save the chronology or any user messages, nor does it invoke any file actions
    //the color is also view related, and the usernames are multiplayer or savegame related
    //it's only the grid and nothing else
    //update: later on I added in the move numbers as history, it ensures to copy games step by step
    //the safe check for corrupt data is, that the container can only be filled by doMoveIntoColumn()

    //interface:
    /*
    doMoveIntoColumn -> returns true to parent class to enable secure storage in SaveGame
    getPositionContentStringAt
    "Labels for ContentStrings"
    resetGrid
    checkColumnFree -> could be used to easily check whether or not the grid is completely empty
     */

    Logging log = new Logging("GridContainer.java");

    public final String player1Label="player1";
    public final String player2Label="player2";
    public final String positionEmptyLabel="positionEmpty";
    public final String positionNotInitializedLabel="positionNotInitialized";

    private String firstTurnIndicator=positionNotInitializedLabel;
    private boolean checkFirstTurnSet=false;
    private int totalMoves;

    private class Position{
        int posX=0;
        int posY=0;
        int moveNumber=0;
        String positionContent=positionNotInitializedLabel;
        boolean drawMovementAnimationAsLatestTurn=false;
    }

    private ArrayList<Position> playFieldGrid;

    public GridContainer(){

        initializeEmptyGrid();
    }

    private void initializeEmptyGrid(){
        playFieldGrid = new ArrayList<Position>();
        for(int x=1; x<=8;x++){
            for(int y=1; y<=7;y++){
                Position position=new Position();
                position.positionContent=positionEmptyLabel;
                position.posX=x;
                position.posY=y;
                position.drawMovementAnimationAsLatestTurn=false;
                playFieldGrid.add(position);
            }
        }
    }

    public void resetGrid(){
        playFieldGrid.clear();
        initializeEmptyGrid();
        checkFirstTurnSet=false;
        firstTurnIndicator=positionNotInitializedLabel;
        totalMoves=0;
    }

    public void replaceContainer(GridContainer newGridContainer){
        resetGrid();
        int totalMovesNew=newGridContainer.getTotalMoves();

        //log.add("replaceContainer:totalMovesNew="+totalMovesNew);
        for(int i=1; i<=totalMovesNew; i++){
            doMoveIntoColumn(
                    newGridContainer.getPositionAtMoveNumber(i).posX,
                    newGridContainer.getPositionAtMoveNumber(i).positionContent
            );
            //log.add("replaceContainer:positionX="+newGridContainer.getPositionAtMoveNumber(i).posX+" posContent="+newGridContainer.getPositionAtMoveNumber(i).positionContent);
        }
    }

    private Position getPositionAtMoveNumber(int moveNumber){
        //log.add("getPositionAtMoveNumber:moveNumber="+moveNumber);
        Position position=new Position();
        for(int x=1;x<=8;x++){
            for(int y=1;y<=7;y++){
                if(getPositionElementAt(x,y).moveNumber==moveNumber){
                    position=getPositionElementAt(x,y);
                    //log.add("getPositionAtMoveNumber:POS X="+position.posX+" Y="+position.posY+" L="+position.positionContent);
                }
            }
        }
        return position;
    }
    public String getPlayerLabelAtMoveNumber(int moveNumber){
        return getPositionAtMoveNumber(moveNumber).positionContent;
    }

    private Position getPositionElementAt(int xCoord, int yCoord){

        if(xCoord>=1 && xCoord<=8 && yCoord>=1 && yCoord<=7){
            for(Position positionElement : playFieldGrid){
                for(int x=1; x<=8;x++){
                        if(positionElement.posX==xCoord){
                            for(int y=1; y<=7;y++){
                                if(positionElement.posY==yCoord)
                                {
                                    return positionElement;
                                }
                            }
                        }
                }
            }
        }
        Position position = new Position();
        return position;
    }
    public String getPositionContentStringAt(int x, int y){
        String positionContent=positionEmptyLabel;

        positionContent=getPositionElementAt(x,y).positionContent;

        return positionContent;
    }
    public int getPositionMoveNumberAt(int x, int y){
        return getPositionElementAt(x,y).moveNumber;
    }
    public String getFirstTurnIndicator(){
        return firstTurnIndicator;
    }
    public int getTotalMoves(){
        return totalMoves;
    }

    private void setPositionContentStringAt(int x, int y, String contentLabel){
        if(contentLabel.equals(positionEmptyLabel)
                ||contentLabel.equals(player1Label)
                ||contentLabel.equals(player2Label)) {
            getPositionElementAt(x, y).positionContent = contentLabel;
        }
    }
    private boolean setMoveAt(int x, int y, String contentLabel){
        if(totalMoves==0 && !checkFirstTurnSet){
            firstTurnIndicator=contentLabel;
            checkFirstTurnSet=true;
        }
        if(totalMoves>0 && !checkFirstTurnSet)
            return false;

        if(getPositionElementAt(x,y).moveNumber==0){
            setPositionContentStringAt(x,y,contentLabel);
            totalMoves++;
            getPositionElementAt(x,y).moveNumber=totalMoves;
        }
        if(totalMoves==0 && checkFirstTurnSet)
            return false;
        return true;

    }

    public boolean checkColumnFree(int xCoord){
        int columnHeight=0;
        for(int y=1; y<=7;y++){
            String check = getPositionContentStringAt(xCoord,y);
            if(check.equals(player1Label)||check.equals(player2Label)){
                columnHeight++;
            }
        }
        if(columnHeight>=7)
            return false;
        else return true;
    }
    public boolean doMoveIntoColumn(int xCoord, String playerLabel){
        if(!checkColumnFree(xCoord))
            return false;
        int yCoord=getTopmostFreeSpotInColumn(xCoord);
        if (yCoord==0) //my error handling is "code does nothing if wrong value given"
            return false;
        else {
            boolean success = setMoveAt(xCoord,yCoord, playerLabel);
            if (!success) return false;
        }

        return true; //parent class intended to store successful move into SaveGame chronologically
    }
    private int getTopmostFreeSpotInColumn(int xCoord){
        int topmostFreeYCoord=0;
        //this function is laid out to work if the column has at least one empty spot
        if (!checkColumnFree(xCoord)) return 0;

        int topmostOccupiedYCoord=0;
        for(int y=1; y<=7; y++){
            String check = getPositionContentStringAt(xCoord,y);
            if(check.equals(player1Label)||check.equals(player2Label)){
                topmostOccupiedYCoord++;
            }
        }
        if (topmostOccupiedYCoord>=0||topmostOccupiedYCoord<7)
            topmostFreeYCoord=topmostOccupiedYCoord+1;
        if (topmostOccupiedYCoord>=7){
            return 0;
        }
        return topmostFreeYCoord;
    }

}
