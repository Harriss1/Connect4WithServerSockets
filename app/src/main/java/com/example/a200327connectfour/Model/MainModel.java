package com.example.a200327connectfour.Model;

import android.content.Context;

import com.example.a200327connectfour.Model.Database.Savegame;

public class MainModel {

    Context context;
    Savegame savegame;

    public MainModel(Context context)
    {
        this.context = context;
        savegame = new Savegame(context);
    }

}
