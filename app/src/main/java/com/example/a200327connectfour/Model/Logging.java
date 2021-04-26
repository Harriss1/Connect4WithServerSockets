package com.example.a200327connectfour.Model;

import java.util.ArrayList;

public class Logging {

    private String sourceDefinition;
    private static ArrayList<String> loggingArray = new ArrayList<String>();

    public Logging(String sourceDefinition){
        this.sourceDefinition=sourceDefinition;
    }

    public void add(String logMessage){

        loggingArray.add(sourceDefinition+" : "+logMessage);
    }

    public String getLogAsString(){
        String logAsString="";

        for(String e : loggingArray){
            logAsString+=e+"\n";
        }

        return logAsString;
    }
}
