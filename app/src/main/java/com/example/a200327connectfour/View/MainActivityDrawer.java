package com.example.a200327connectfour.View;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a200327connectfour.Model.Logging;
import com.example.a200327connectfour.R;
import com.example.a200327connectfour.ViewModel.UIDataContainer;
import com.example.a200327connectfour.ViewModel.ViewModel;

public class MainActivityDrawer {

    Logging log = new Logging("MainActivityDrawer.java");

    View view;
    Context context;
    GridDisplayContainer gridDisplayContainer;
    MainActivityDisplayContainer mainActivityDisplayContainer;
    ViewModel viewModel;
    DrawGameBoard drawGameBoard;

    public MainActivityDrawer(Context context, View view, ViewModel viewModel){
        this.context=context;
        this.view=view;
        this.viewModel=viewModel;

        gridDisplayContainer = new GridDisplayContainer();
        mainActivityDisplayContainer = new MainActivityDisplayContainer();
        drawGameBoard = new DrawGameBoard(view, context);

        initialDraw();
    }

    public void updateView(){
        getViewModelInterfaceData();
        getUIdataToDisplayContainers();
        drawDisplayContainerData();
    }
    private void getViewModelInterfaceData(){
        //x->8, y->7
        for (int x=1; x<=8; x++){
            for (int y=1;y<=7;y++){
                String playerLabel = viewModel.getGridContentAt(x,y);
                if(playerLabel.equals(viewModel.getPlayer1Label())){
                    gridDisplayContainer.setPosition(x,y,gridDisplayContainer.getFieldOfPlayerOneString());
                }
                if(playerLabel.equals(viewModel.getPlayer2Label())){
                    gridDisplayContainer.setPosition(x,y,gridDisplayContainer.getFieldOfPlayerTwoString());
                }
                if(playerLabel.equals(viewModel.getPositionEmptyLabel())){
                    gridDisplayContainer.setPosition(x,y,gridDisplayContainer.getFieldNotOccupiedString());
                }
            }
        }
        setAllMoveAnimationFalse();
        setMoveToAnimate();
    }
    public void setAllMoveAnimationFalse(){
        gridDisplayContainer.setDrawAnimationOnAllPosOff();
    }
    private void setMoveToAnimate(){
        int xAnim=viewModel.getMoveToAnimateX();
        int yAnim=viewModel.getMoveToAnimateY();
        gridDisplayContainer.setDrawAnimationToPosition(xAnim,yAnim);
    }

    private void getUIdataToDisplayContainers(){

        UIDataContainer uiDataContainer = new UIDataContainer();
        uiDataContainer = viewModel.getUIData();

        int test = uiDataContainer.testVar1;

    }
    private void drawDisplayContainerData(){
        drawUIelements();
        drawGridAsText();
    }

    private void drawUIelements(){
        UIDataContainer uiDataContainer = new UIDataContainer();
        uiDataContainer = viewModel.getUIData();

        drawGameBoard.drawAllPieces(gridDisplayContainer);

        TextView userMessageWin = (TextView) view.findViewById(R.id.turnOrWinMessage);
        String textOutput=uiDataContainer.userMessage;
        textOutput=textOutput.replaceAll("no Winner"," ");
        userMessageWin.setText(textOutput);

        TextView debugMessage = (TextView) view.findViewById((R.id.debug));


        if(!animateWinnerPiece(uiDataContainer.winnerIndicator)) {
            setNextTurnPieceColor(uiDataContainer.userMessage);

        }

        debugMessage.setMovementMethod(new ScrollingMovementMethod());
        //log.add("drawUIelements:testAdditionToLog");
        debugMessage.setText(log.getLogAsString()+mainActivityDisplayContainer.getDebugMesssage());

        boolean developerCheck=false;

        if(developerCheck && mainActivityDisplayContainer.getShowResumeLastGameOption()){
            view.findViewById(R.id.ButtonDismissLast).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ButtonResumeLast).setVisibility(View.VISIBLE);
            view.findViewById(R.id.textViewResumePrompt).setVisibility(View.VISIBLE);
        } else {
            //disable view of buttons
            view.findViewById(R.id.ButtonDismissLast).setVisibility(View.GONE);
            view.findViewById(R.id.ButtonResumeLast).setVisibility(View.GONE);
            view.findViewById(R.id.textViewResumePrompt).setVisibility(View.GONE);
            view.findViewById(R.id.ButtonTestSave).setVisibility(View.GONE);
        }

        //I can set transparency to buttons programmatically here:
        //(or where the buttons get forwarded in MainActivityView


        //myButton.getBackground().setAlpha(64);  // 25% transparent

    }
    private void initialDraw(){
        updateView();
    }

    private void drawGridAsText(){
        boolean developerCheck=false;
        if(!developerCheck){
            return;
        }
        //for development, to set up a simple view first
        TextView testIt = (TextView) view.findViewById(R.id.textViewTempGrid);
        testIt.setText("hallehui");

        //gridDisplayContainer.setPosition(8,7, gridDisplayContainer.getFieldOfPlayerTwoString());

        String gridAsText="";
        String playerOneColorToStringLabel;
        String playerTwoColorToStringLabel;

        if(gridDisplayContainer.getPlayerOneColor().equals(gridDisplayContainer.colorBlue)){
            playerOneColorToStringLabel="B";
            playerTwoColorToStringLabel="R";
        } else {
            playerOneColorToStringLabel="R";
            playerTwoColorToStringLabel="B";
        }
        for (int y=7; y>=1; y--){
            for (int x=1; x<=8; x++){
                String addChar="###";
                if(gridDisplayContainer.getFieldPositionContentAt(x,y).equals(gridDisplayContainer.getFieldNotOccupiedString())){
                    addChar=".";
                }
                if(gridDisplayContainer.getFieldPositionContentAt(x,y).equals(gridDisplayContainer.getFieldOfPlayerOneString()))
                {
                    addChar=playerOneColorToStringLabel;
                }
                if(gridDisplayContainer.getFieldPositionContentAt(x,y).equals(gridDisplayContainer.getFieldOfPlayerTwoString()))
                {
                    addChar=playerTwoColorToStringLabel;
                }
                gridAsText+=addChar;
                if(x==8)gridAsText+="\n";
            }
        }
        testIt.setText("##GirdStart##\n"+gridAsText+"##GridEnd##");
    }

    public void switchPlayerColor(){
        gridDisplayContainer.switchPlayerColor();
    }

    private boolean animateWinnerPiece(String winnerIndicator){
        if(winnerIndicator.equals("Red")){
            ((ImageView) view.findViewById(R.id.ImageTurnIndicator)).setImageResource(R.drawable.redsmall);
            drawGameBoard.animateWinnerPiece(430);
            ((Button) view.findViewById(R.id.ButtonRestart)).setVisibility(View.VISIBLE);
            return true;
        } else if (winnerIndicator.equals("Blue")){
            ((ImageView) view.findViewById(R.id.ImageTurnIndicator)).setImageResource(R.drawable.bluesmall);
            drawGameBoard.animateWinnerPiece(430);
            ((Button) view.findViewById(R.id.ButtonRestart)).setVisibility(View.VISIBLE);
            return true;
        } else {
            drawGameBoard.animateWinnerPiece(0);
        }
        return false;
    }
    private void setNextTurnPieceColor(String userMessage){
        if(userMessage.contains("Red's turn")){
            ((ImageView) view.findViewById(R.id.ImageTurnIndicator)).setImageResource(R.drawable.redsmall);
        } else if (userMessage.contains("Blue's turn")){
            ((ImageView) view.findViewById(R.id.ImageTurnIndicator)).setImageResource(R.drawable.bluesmall);
        } else {
            //
        }
    }





}
