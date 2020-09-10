package com.example.a200327connectfour.View;

import android.animation.ObjectAnimator;

import android.content.Context;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.example.a200327connectfour.Model.Logging;
import com.example.a200327connectfour.R;

import java.util.ArrayList;

public class DrawGameBoard {

    private View view;
    private Context context;
    Logging log = new Logging("DrawGameBoard.java");

    private final int gameGridSizeInDP=350;
    private final int pieceDiameterInDP=36;
    private final int marginBetweenPiecesInDP=4;

    private class Position{
        int x=0;
        int y=0;
        boolean checkOccupied=false;
        int imageViewID=0;
        Position(int x, int y, int imageViewID){
            this.x=x;
            this.y=y;
            checkOccupied=true;
            this.imageViewID=imageViewID;
        }
    }
    private ArrayList<Position> gameBoardGridMeshIDs = new ArrayList<Position>();
    private ArrayList<Position> gameBoardDrawHistory = new ArrayList<Position>();

    private int testCoordinateChanger=0;
    private boolean checkAlternateTestImage =true;
    private int testX=3;
    private int testY=4;
    private int constraintIDMesh=R.id.ConstraintForGridMesh;
    private int constraintIDAllPieces=R.id.ConstraintForAllPieces;
    private static boolean drawGridMeshOnceCheck = false;

    public DrawGameBoard(View view, Context context){
        this.view=view;
        this.context=context;

        //if(!drawGridMeshOnceCheck){
            drawGridMeshOnceCheck=true;
            drawGridMesh();
        //}
    }

    private int dpToPx(int dp){
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void drawAllPieces(GridDisplayContainer gridDisplayContainer){
        for(int x=1; x<=8; x++){
            for(int y=1; y<=7; y++){
                if(gridDisplayContainer.getFieldPositionContentAt(x,y).equals(gridDisplayContainer.getFieldOfPlayerOneString())||
                        gridDisplayContainer.getFieldPositionContentAt(x,y).equals(gridDisplayContainer.getFieldOfPlayerTwoString())){
                    //log.add("drawAllpieces(): drawing piece at x="+x+"y="+y);
                    drawPiece(
                            x,
                            y,
                            gridDisplayContainer.getFieldPositionContentAt(x, y),
                            gridDisplayContainer.getFieldPositionAnimation(x,y)
                    );
                } else {
                    removePieceDrawingIfOccupied(x,y);
                    //log.add("drawAllPieces(): empty field");
                }
            }
        }
    }

    private boolean checkPieceAlreadyDrawn(int x, int y){
        for(Position element : gameBoardDrawHistory){
            if(element.x==x && element.y==y){
                if(element.checkOccupied) return true;
            }
        }
        return false;
    }
    private void drawPiece(int x, int y, String playerLabel, boolean animate){
        if(!checkPieceAlreadyDrawn(x,y)){
            addPieceImageViewRelativeXY(x,y,playerLabel, animate);

        } else {
            //log.add("drawPiece: piece already drawn");
        }

        bringGridMeshToFront();

    }

    private void setPieceIsDrawn(int x, int y, int imageID) {
        int testCounterMemLeak=0;
        for(Position element : gameBoardDrawHistory) {
            testCounterMemLeak++; //for 7*8=56 there should only be 56 elements in the array
            if(testCounterMemLeak>56) log.add("BUG setPieceIsDrawn(): overflow of gameBoardDrawHistory");

            if (element.x == x && element.y == y) {
                if (element.checkOccupied) return;
                else {
                    element.checkOccupied = true;
                    element.imageViewID=imageID;
                    return;
                }
            } else {
                /*
                log.add("setPieceIsDrawn(): element=["

                        +Integer.toString(element.x)+","
                        +Integer.toString(element.y)+"-"
                        +Integer.toString(element.imageViewID)+"Drawn="
                        +Boolean.toString(element.checkOccupied)+"]");
                */
                //Position pos = new Position(x, y, imageID);
                //gameBoardDrawHistory.add(pos);
                //return;
            }
        }
        //log.add("setPieceIsDrawn(): new Element");
        Position pos = new Position(x, y, imageID);
        gameBoardDrawHistory.add(pos);
    }

    private void addPieceImageViewRelativeXY(int x, int y, String playerLabel, boolean animate){
        //log.add("addPieceImageRelativeXY()");
        ImageView imageview = new ImageView(context);
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(constraintIDAllPieces);
        imageview.setId(ViewCompat.generateViewId());


        GridDisplayContainer gridDisplayContainer = new GridDisplayContainer();
        if(playerLabel.equals(gridDisplayContainer.getFieldOfPlayerOneString())){
            imageview.setImageResource(R.drawable.bluesmall);
        } else if (playerLabel.equals(gridDisplayContainer.getFieldOfPlayerTwoString())){
            imageview.setImageResource(R.drawable.redsmall);
        } else if (playerLabel.equals(gridDisplayContainer.getFieldNotOccupiedString())) {
            return;
        }
        /*
        else { //development code is not used anymore
            {
                log.add("error in addPieceImageViewRelativeXY():using alternating Test Image");
                if (checkAlternateTestImage) {
                    imageview.setImageResource(R.drawable.bluesmall);
                    checkAlternateTestImage = false;
                } else {
                    imageview.setImageResource(R.drawable.redsmall);
                    checkAlternateTestImage = true;
                }
            }
        }
        */
        int topConstraintOfPiece=dpToPx(calcVerticalPiecePositionAsDP(y))+marginBetweenPiecesInDP;
        int topConstraintOfPieceAtStart=0;
        if(!animate){
            topConstraintOfPieceAtStart=topConstraintOfPiece;
        }

        ConstraintSet set = new ConstraintSet();

        constraintLayout.addView(imageview);
        set.clone(constraintLayout);
        set.connect(imageview.getId(), ConstraintSet.TOP,constraintIDAllPieces, ConstraintSet.TOP, topConstraintOfPieceAtStart);
        set.connect(imageview.getId(), ConstraintSet.LEFT, constraintIDAllPieces, ConstraintSet.LEFT, dpToPx(calcHorizontalPiecePositionAsDP(x))+marginBetweenPiecesInDP);
        set.constrainHeight(imageview.getId(), dpToPx(pieceDiameterInDP));
        set.constrainWidth(imageview.getId(), dpToPx(pieceDiameterInDP));

        set.applyTo(constraintLayout);

        setPieceIsDrawn(x, y, imageview.getId());

        if(animate)
        animatePiece(imageview.getId(), topConstraintOfPiece);
    }
    private void animatePiece(int viewID, int topConstraintOfPiece){
        float topConstraint=topConstraintOfPiece;
        ImageView imageView = (ImageView) view.findViewById(viewID);
        ObjectAnimator textViewAnimator = ObjectAnimator.ofFloat(imageView, "translationY",0f,topConstraint);
        textViewAnimator.setDuration(1000);
        //textViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        textViewAnimator.setInterpolator(new AccelerateInterpolator());
        textViewAnimator.start();
    }

    public void animateWinnerPiece(int topConstraintOfPieceInDP){
        if(topConstraintOfPieceInDP!=0) {

            //detect if we have landscape by checking window height
            int heightInPx = Resources.getSystem().getDisplayMetrics().heightPixels;
            int maxHeightInPx = dpToPx(500); //the rough screen size is around 420dp, some models have 500?
            log.add("animateWinnerPiece():heightInPx=" + Integer.toString(heightInPx) + "max=" + Integer.toString(maxHeightInPx));
            if (heightInPx <= maxHeightInPx) {
                topConstraintOfPieceInDP = 190;
            }
        }
        float topConstraint=dpToPx(topConstraintOfPieceInDP);
        ImageView imageView = (ImageView) view.findViewById(R.id.ImageTurnIndicator);
        ObjectAnimator textViewAnimator = ObjectAnimator.ofFloat(imageView, "translationY",0f,topConstraint);
        textViewAnimator.setDuration(2000);
        textViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        textViewAnimator.start();
    }

    private int calcHorizontalPiecePositionAsDP(int x){
        int piecePosition=0;

        int relativePiecePosition=(x-1)*pieceDiameterInDP+(x-1)*marginBetweenPiecesInDP;
        piecePosition=relativePiecePosition; //the grid drawing starts left and goes to right

        return piecePosition;
    }
    private int calcVerticalPiecePositionAsDP(int y){
        int piecePosition=0;

        int relativePiecePosition=(y-1)*pieceDiameterInDP+(y-1)*marginBetweenPiecesInDP;
        piecePosition=( pieceDiameterInDP*7
                        +marginBetweenPiecesInDP*7
                        -relativePiecePosition);

        return piecePosition;
    }

    private void removePieceDrawingIfOccupied(int x, int y){
        int testCounterMemLeak=0;
        for(Position elementPosition : gameBoardDrawHistory){

            testCounterMemLeak++; //for 7*8=56 there should only be 56 elements in the array
            if(testCounterMemLeak>56) log.add("BUG setPieceIsDrawn(): overflow of gameBoardDrawHistory");

            if(elementPosition.x==x && elementPosition.y==y){
                int imgID = elementPosition.imageViewID;
                //log.add("remove:id="+imgID);
                ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(constraintIDAllPieces);
                ImageView imgDel = new ImageView(context);
                imgDel.setId(imgID);
                imgDel.setImageDrawable(null);
                //((ImageView) view.findViewById(imgID)).setImageDrawable(null);
                constraintLayout.removeView(view.findViewById(imgID));
                elementPosition.checkOccupied=false;
                elementPosition.imageViewID=0;
            }
        }
    }

    private void drawGridMesh(){
        //log.add("drawGridMesh(): Drawing board - again?");
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(constraintIDMesh);
        for( int x=1; x<=8; x++){
            for( int y=1; y<=7; y++){
                ImageView imageview = new ImageView(context);
                imageview.setId(ViewCompat.generateViewId());
                imageview.setImageResource(R.drawable.gridmeshpart);
                ConstraintSet set = new ConstraintSet();

                constraintLayout.addView(imageview);
                set.clone(constraintLayout);
                set.connect(imageview.getId(), ConstraintSet.TOP, constraintIDMesh, ConstraintSet.TOP, dpToPx(calcVerticalPiecePositionAsDP(y)));
                set.connect(imageview.getId(), ConstraintSet.LEFT, constraintIDMesh, ConstraintSet.LEFT, dpToPx(calcHorizontalPiecePositionAsDP(x)));
                set.constrainHeight(imageview.getId(), dpToPx(pieceDiameterInDP+marginBetweenPiecesInDP));
                set.constrainWidth(imageview.getId(), dpToPx(pieceDiameterInDP+marginBetweenPiecesInDP));
                imageview.bringToFront();
                set.applyTo(constraintLayout);
                Position pos = new Position(x,y,imageview.getId());
                gameBoardGridMeshIDs.add(pos);
            }
        }
    }
    private void bringGridMeshToFront(){
        for(Position positionElement : gameBoardGridMeshIDs){
            ImageView imageView = (ImageView) view.findViewById(positionElement.imageViewID);
            imageView.bringToFront();
        }
    }


}
