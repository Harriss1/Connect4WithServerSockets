package com.example.a200327connectfour.ViewModel;

import android.content.Context;

public class ViewModel {

    Context context;

    public ViewModel(Context context){
        this.context = context;

    }

    public void onColumnClick(int column){
        //column 1-7
        if(column < 1 || column > 7) {
            //add possible error message here
            return;
        }

        //add model connection here
    }

}
