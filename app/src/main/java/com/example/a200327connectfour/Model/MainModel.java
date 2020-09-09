package com.example.a200327connectfour.Model;

import android.content.Context;

import com.example.a200327connectfour.Model.Database.SaveGame;
import com.example.a200327connectfour.Model.Game.GridContainer;
import com.example.a200327connectfour.Model.Game.MainConnectFour;

public class MainModel {

    Logging log = new Logging("MainModel.java");
    Context context;
    SaveGame savegame;
    MainConnectFour mainConnectFour;

    private final String fileNameInProgressSaveGame = "CurrentGameSave.txt";
    private final String fileNameOldSaveGame= "OldGameSave.txt";

    public MainModel(Context context)
    {
        this.context = context;
        savegame = new SaveGame(context);
        mainConnectFour = new MainConnectFour();

    }

    public GridContainer getGridContainer(){
        return mainConnectFour.getGridContainer();
    }
    public void setGameEventMoveAt(int yCoord){
        //naming not really OOP or Clean, gameDoMoveAt is private but we get readability issues...
        gameDoMoveAt(yCoord);
    }

    private void gameDoMoveAt(int yCoord){
        //mainConnectFour.testMove(yCoord, mainConnectFour.getGridContainer().player2Label);
        mainConnectFour.gameMove(yCoord);
        //The in progress game is saved every turn
        savegame.saveGridContainerToFile(mainConnectFour.getGridContainer(), fileNameInProgressSaveGame);
    }

    private void saveGridContainerToFile(){
    }

    public void gameLoadInProgressGameAfterMemoryLoss(){
        mainConnectFour.replaceGameState(savegame.getGridContainerFromFile(fileNameInProgressSaveGame));
    }

    public void gameOnAppStart(){
        //if the android application gets freshly started we put the existing old InProgressSaveGame
        //into another file
        //doing this via the constructor would also swap file contents in the wrong occasions.

        //check if an in progress savegame exists:
        if(savegame.checkForSaveGameExistence(fileNameInProgressSaveGame)){
            log.add("game:onAppStart:checkForSaveGameExistence=true");
            //then load save from inProgress to  "oldsave.txt"
            savegame.saveGridContainerToFile(
                    savegame.getGridContainerFromFile(fileNameInProgressSaveGame),
                    fileNameOldSaveGame);
            //to prevent the old savegame of being loaded on accident we override the "in progress"
            mainConnectFour.restartGame();
            savegame.saveGridContainerToFile(getGridContainer(),fileNameInProgressSaveGame);
        }


    }

    public String testSaveGame(){
        savegame.testSaveToFile(getGridContainer());
        return savegame.bulkReadGridContainerToString(getGridContainer());
    }

    public void gameRestartGame(){
        mainConnectFour.restartGame();
    }

    public void gameTestLoad() {
        //loads old save manually, in dev mode we use TestSaveButton for it
        int test=0;
        test=savegame.getGridContainerFromFile().getTotalMoves();
        log.add("gameTestLoad:totalMovesInFileGridContainer="+test);
        mainConnectFour.replaceGameState(savegame.getGridContainerFromFile());

    }

    public void gameTestSave() {
        savegame.saveGridContainerToFile(getGridContainer());
    }

    public void gameDismissOldGame() {

    }

    public void gameResumeOldGame() {
        //in development its trigger via onswitchcolorbutton...
        mainConnectFour.replaceGameState(savegame.getGridContainerFromFile(fileNameOldSaveGame));
    }

    public String getGameInfoString() {
        return mainConnectFour.getGameMessage();
    }

    public void gameSwitchColor() {

    }
    public int gameGetLastMoveY(){
        return mainConnectFour.gameGetLastMoveY();
    }
    public int gameGetLastMoveX(){
        return mainConnectFour.gameGetLastMoveX();
    }
}
