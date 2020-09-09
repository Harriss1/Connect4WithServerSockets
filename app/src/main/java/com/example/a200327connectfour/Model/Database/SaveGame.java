package com.example.a200327connectfour.Model.Database;

import android.content.Context;
import android.util.Log;

import com.example.a200327connectfour.Model.Game.GridContainer;
import com.example.a200327connectfour.Model.Logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveGame {
    Logging log=new Logging("SaveGame.java");

    Context context;
    final String fileNameTestSave1= "TestSave1.txt";
    final String fileNameGameSave="InProgressGameSave.txt";
    final String fileNameGameSaveCopyAfterGameStart="CopyAfterGameStartGameSave.txt";
    final String gridEmptyMessage="grid empty";

    public SaveGame(Context context){

        this.context=context;
    }

    public void saveGridContainerToFile(GridContainer gridContainer){
        String gridContainerAsString = bulkReadGridContainerToString(gridContainer);
        saveStringToFile(gridContainerAsString, fileNameTestSave1);
        //log.add("saveGridContainerToFile():fileName="+fileNameTestSave1);
    }

    public void saveGridContainerToFile(GridContainer gridContainer, String fileName){
        String gridContainerAsString = bulkReadGridContainerToString(gridContainer);
        saveStringToFile(gridContainerAsString, fileName);
        //log.add("saveGridContainerToFile(unique filename):fileName="+fileName);
    }

    public GridContainer getGridContainerFromFile(){
        String saveFileContent =  readStringFromFile(fileNameTestSave1);

        //The usage of move numeration allows use the rebuild the grid just like it was in game
        GridContainer gridContainer = new GridContainer();
        gridContainer.replaceContainer(gridContentSafeFileStringToGridContainer(saveFileContent));
        //log.add("getGridContainerFromFile():fileName="+fileNameTestSave1+"\n SAFEFILECONTENT="+saveFileContent);

        return gridContainer;
    }
    public GridContainer getGridContainerFromFile(String fileNameGameSave){
        String saveFileContent =  readStringFromFile(fileNameGameSave);

        //The usage of move numeration allows use the rebuild the grid just like it was in game
        GridContainer gridContainer = new GridContainer();
        gridContainer.replaceContainer(gridContentSafeFileStringToGridContainer(saveFileContent));
        //log.add("getGridContainerFromFile():fileName="+fileNameTestSave1+"\n SAFEFILECONTENT="+saveFileContent);

        return gridContainer;
    }

    public String bulkReadGridContainerToString(GridContainer gridContainer){
        String gridContent="";
        if(gridContainer.getTotalMoves()==0) return gridEmptyMessage;

        //The move numeration will store each move backwards from the last move that happened
        int totalMoves=gridContainer.getTotalMoves();
        gridContent+="TotalMoves="+Integer.toString(totalMoves);
        gridContent+="FirstPlayerTurnLabel="+gridContainer.getFirstTurnIndicator()+"\n";
        for(int moveToProcess=totalMoves; moveToProcess>=0; moveToProcess--) {
            //save pattern: first all moves in descending order, then empty fields
            for (int x = 1; x <= 8; x++) {
                for (int y = 1; y <= 7; y++) {
                    if(moveToProcess==gridContainer.getPositionMoveNumberAt(x,y)){
                        /*
                        String pattern for one position:
                        X,Y,Label,MoveNumber
                        X[1-8]Y[1-7]C[0,1,2]M[0,1-56]
                        example: "#X2Y7C1#"
                        File Start pattern:
                        TotalMoves,FirstPlayerTurnLabel[1/2]
                         */

                        gridContent += "X"+Integer.toString(x)
                                +"Y"+Integer.toString(y)
                                +"C"+gridContainer.getPositionContentStringAt(x,y)
                                +"M"+Integer.toString(moveToProcess)
                                +"\n";

                    }
                }
            }
        }
        return gridContent;
    }

    private GridContainer gridContentSafeFileStringToGridContainer(String grindContentSafeFileString){
        GridContainer gridContainer = new GridContainer();
        int totalMoves=getTotalMovesFromSafeFileString(grindContentSafeFileString);
        //log.add("gridContentSafeFileStringToGridContainer:TOTALMOVES="+totalMoves);
        if(totalMoves <= 0)
            return gridContainer;


        String str =grindContentSafeFileString;
        str = str.replaceAll("[^0-9]+", " ");
        str=str.trim();
        List<String> contents = Arrays.asList(str.split(" "));

        //log.add("toGridContainer: str="+str);
        //log.add("toGridContainer: totalMoves="+totalMoves);
        for(int i=totalMoves; i>=1; i--){
            //log.add("in loop: i="+i);
            int x=0, y=0;
            String playerLabel=gridContainer.positionEmptyLabel;
            x= Integer.parseInt(contents.get(2+4*(i-1)));
            //log.add("in loop: x="+x);
            y= Integer.parseInt(contents.get(3+4*(i-1)));
            if(contents.get((4+4*(i-1))).equals("1"))
                playerLabel=gridContainer.player1Label;
            if(contents.get(4+4*(i-1)).equals("2"))
                playerLabel=gridContainer.player2Label;
            //log.add("toGridContainer: x="+x+" y="+y+" pL="+playerLabel);
            int moveNumber=Integer.parseInt(contents.get(5+4*(i-1)));

            gridContainer.doMoveIntoColumn(x,playerLabel);
            //log.add("toGridContainer:doMove:X="+x+playerLabel);

        }
        return gridContainer;
    }

    private int getTotalMovesFromSafeFileString(String gridContentSafeFileString){
        int totalMoves=0;
        if(gridContentSafeFileString.equals(gridEmptyMessage)){
            return totalMoves;
        }
        //String moveNumberString = gridContentSafeFileString.substring(11,13);
        String moveNumberString;
        String str =gridContentSafeFileString;
        str = str.replaceAll("[^0-9]+", " ");
        str=str.trim();
        List<String> contents = Arrays.asList(str.split(" "));
        moveNumberString=contents.get(0);
        //log.add("getTotalMovesFromSafeFileString():str="+str);
        //log.add("getTotalMovesFromSafeFileString():moveNumberString="+moveNumberString);
        //log.add("getTotalMovesFromSafeFileString():playerNumberString="+contents.get(1));
        if(moveNumberString.equals("")) moveNumberString="0";
        totalMoves=Integer.parseInt(moveNumberString);
        return totalMoves;
    }

    private String getPlayerTurnFromSafeFileString(String gridContentSafeFileString){
        if(gridContentSafeFileString.equals(gridEmptyMessage)){
            return gridEmptyMessage;
        }
        GridContainer gridContainer = new GridContainer();
        String str =gridContentSafeFileString;
        str = str.replaceAll("[^0-9]+", " ");
        str=str.trim();
        List<String> contents = Arrays.asList(str.split(" "));
        if(contents.get(1).equals("1")){
            //log.add("getPlayerTurnFromSafeFileString:playerLabel=1");
            return gridContainer.player1Label;
        } if (contents.get(1).equals("2")){
            //log.add("getPlayerTurnFromSafeFileString:playerLabel=2");
            return gridContainer.player2Label;
        }
        return "0";
    }

    public String testSaveToFile(GridContainer gridContainer){

        int totalMoves=getTotalMovesFromSafeFileString(bulkReadGridContainerToString(gridContainer));

        getPlayerTurnFromSafeFileString(bulkReadGridContainerToString(gridContainer));

        gridContentSafeFileStringToGridContainer(bulkReadGridContainerToString(gridContainer));
        String dataString="a";
        //dataString += readStringFromFile();
        //saveStringToFile(dataString);
        return dataString;
    }

    private void saveStringToFile(String data, String fileName){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readStringFromFile(String fileName){
        String fileContent = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                fileContent = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return fileContent;
    }

    public boolean checkForSaveGameExistence(String fileName){
        return true;
    }
}
