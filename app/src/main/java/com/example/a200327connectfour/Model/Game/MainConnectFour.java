package com.example.a200327connectfour.Model.Game;

public class MainConnectFour {
    GridContainer gridContainer;
    GameLogic gameLogic = new GameLogic(); //maybe use it local only to save memory?
    private boolean checkGameWin=false;

    public MainConnectFour(){

        gridContainer = new GridContainer();

    }
    //check for saved game->should happen in MainModel
    //MainConnectFour should only have a "fill from bulk" function
    //make a second "game itself" class?


    //Interface to the app
    public GridContainer getGridContainer(){
        return gridContainer;
    }
    public String getGameMessage(){
        String gameMessage="";
        String nextPlayersTurn=invertPlayerLabel(gameGetLastMovePlayerLabel());
        gameMessage+= nextPlayersTurn+"'s turn. ";

        gameMessage+= gameLogic.getWinnerLabel(gridContainer);
        //add win message and wrong move message and file corruption message here

        return gameMessage;
    }

    public void testMove(int column, String playerLabel){
        gridContainer.doMoveIntoColumn(column,playerLabel);
    }

    public void restartGame(){
        gridContainer.resetGrid();
    }
    public void replaceGameState(GridContainer newGridContainer){
        //---
        //To implement safe file usage we simply replace the grid container?
        //---
        gridContainer.replaceContainer(newGridContainer);
    }


    public void gameMove(int column){
        if(gameLogic.getWinnerLabel(gridContainer).contains(gridContainer.player1Label)
                ||gameLogic.getWinnerLabel(gridContainer).contains(gridContainer.player2Label)){
            return;
        }

        gridContainer.doMoveIntoColumn(column, invertPlayerLabel(gameGetLastMovePlayerLabel()));
    }

    private void checkForWin(){
        GameLogic gameLogic = new GameLogic();
    }

    public String gameGetLastMovePlayerLabel(){
        int totalMoves = gridContainer.getTotalMoves();
        return gridContainer.getPlayerLabelAtMoveNumber(totalMoves);
    }
    public int gameGetLastMoveY(){
        int totalMoves=gridContainer.getTotalMoves();
        for(int x=1; x<=8; x++){
            for(int y=1; y<=7; y++){
                if(totalMoves==gridContainer.getPositionMoveNumberAt(x,y)){
                    return y;
                }
            }
        }
        return 0;
    }
    public int gameGetLastMoveX(){
        int totalMoves=gridContainer.getTotalMoves();
        for(int x=1; x<=8; x++){
            for(int y=1; y<=7; y++){
                if(totalMoves==gridContainer.getPositionMoveNumberAt(x,y)){
                    return x;
                }
            }
        }
        return 0;
    }
    public String invertPlayerLabel(String playerLabel){
        if(playerLabel.equals(gridContainer.player1Label))return gridContainer.player2Label;
        return gridContainer.player1Label;
    }

    public void gameSetFirstPlayer(String playerLabel){

    }


}
