package com.example.a200327connectfour.Model.Game;

import com.example.a200327connectfour.Model.Logging;

public class GameLogic {
    Logging log = new Logging("GameLogic.java");

    public GameLogic(){

    }

    public String getWinnerLabel(GridContainer gridContainer){
        String addString=" wins the game.";
        if(checkWinVertical(gridContainer,gridContainer.player1Label).equals(gridContainer.player1Label))
            return gridContainer.player1Label+addString;
        if (checkWinVertical(gridContainer,gridContainer.player2Label).equals(gridContainer.player2Label))
            return gridContainer.player2Label+addString;

        if(checkWinHorizontal(gridContainer,gridContainer.player1Label).equals(gridContainer.player1Label))
            return gridContainer.player1Label+addString;
        if(checkWinHorizontal(gridContainer,gridContainer.player2Label).equals(gridContainer.player2Label)){
            return gridContainer.player2Label+addString;
        }

        if(checkWinDiagonal(gridContainer,gridContainer.player1Label).equals(gridContainer.player1Label))
            return gridContainer.player1Label+addString;
        if(checkWinDiagonal(gridContainer,gridContainer.player2Label).equals(gridContainer.player2Label))
            return gridContainer.player2Label+addString;

        return "no Winner";
    }

    private String checkWinVertical(GridContainer gridContainer, String playerLabel){
        if(playerLabel.equals(gridContainer.player1Label)||playerLabel.equals(gridContainer.player2Label)){
            for(int x=1; x<=8;x++){
                int verticalCounter=0;
                int verticalFirstY=0;
                for (int y=1;y<=7;y++){
                    if(gridContainer.getPositionContentStringAt(x,y).equals(playerLabel)){
                        if(verticalFirstY==0){
                            verticalFirstY=y;
                        } else {
                            if(verticalFirstY==y-1-verticalCounter){
                                verticalCounter++;
                            } else {
                                verticalFirstY=0;
                                verticalCounter=0;
                            }
                        }
                        if(verticalCounter>=3)return playerLabel;
                    }
                }
            }
        }
        return "no Winner";
    }

    private String checkWinHorizontal(GridContainer gridContainer, String playerLabel){
        if(playerLabel.equals(gridContainer.player1Label)||playerLabel.equals(gridContainer.player2Label)) {
            for(int y=1;y<=7;y++){
                int horizontalCounter=0;
                int horizontalFirstX=0;

                for(int x=1;x<=8;x++){
                    if(gridContainer.getPositionContentStringAt(x,y).equals(playerLabel)){
                        if(horizontalFirstX==0){
                            horizontalFirstX=x;
                        } else {
                            if(horizontalFirstX==x-1-horizontalCounter){
                                horizontalCounter++;
                            } else {
                                horizontalFirstX=0;
                                horizontalCounter=0;
                            }
                        }
                    }
                    if(horizontalCounter>0)
                    //log.add("checkWinHorizontal:horizCounter="+Integer.toString(horizontalCounter));
                    if(horizontalCounter>=3)return playerLabel;
                }
            }
        }
        return "no Winner";
    }

    private String checkWinDiagonal(GridContainer gridContainer, String playerLabel){
        if(!(playerLabel.equals(gridContainer.player1Label)||playerLabel.equals(gridContainer.player2Label)))
        return "no Winner";

        for (int y=1; y<=7; y++){
            for (int x=1; x<=8; x++){
                if(
                        gridContainer.getPositionContentStringAt(x,y).equals(playerLabel)
                        && gridContainer.getPositionContentStringAt(x+1,y+1).equals(playerLabel)
                        && gridContainer.getPositionContentStringAt(x+2,y+2).equals(playerLabel)
                        && gridContainer.getPositionContentStringAt(x+3,y+3).equals(playerLabel)
                )
                    return playerLabel;
                else if (
                        gridContainer.getPositionContentStringAt(x,y).equals(playerLabel)
                        && gridContainer.getPositionContentStringAt(x-1,y+1).equals(playerLabel)
                        && gridContainer.getPositionContentStringAt(x-2,y+2).equals(playerLabel)
                        && gridContainer.getPositionContentStringAt(x-3,y+3).equals(playerLabel)
                ) return playerLabel;
            }
        }

        return "no Winner";
    }
}
