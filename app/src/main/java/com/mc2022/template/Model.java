package com.mc2022.template;

import java.util.*;

public class Model extends Observable {

    private String name;
    private boolean next;
    private int[] yes_arr;
    private int[] no_arr;

    public Model(){
        this.name = "";
        this.next = false;
        this.yes_arr = new int[7];
        this.no_arr= new int[7];
    }

    public void setName( String name) {

        this.name = name;
        setChanged();
        notifyObservers();
    }

    public String getName(){

        return this.name;
    }

    public void clearAll(){

        this.name = "";
        this.next = false;
        this.yes_arr = new int[7];
        this.no_arr= new int[7];
    }


    public void setNext(Boolean value){

        this.next = value;
    }

    public void setYesArray(int a){

        this.yes_arr[a] = 1;
    }

    public void setNoArray(int a){

        this.no_arr[a] = 1;
    }

    public void clearYesArray(int a){

        this.yes_arr[a] = 0;
    }

    public void clearNoArray(int a){

        this.no_arr[a] = 0;
    }

    public int getYes(int a ){

        return this.yes_arr[a];
    }

    public String getYesString(int a){

        if(this.yes_arr[a] == 1){

            return "Yes";
        }

        else{

            return "No";
        }
    }

    public int getNo(int a ){

        return this.no_arr[a];
    }

    public int getNumberOfYes(){

        int sum = 0;
        for (int i = 0 ; i < 7 ; ++i){

            sum+= this.yes_arr[i];
        }

        return sum;
    }

    public String getYesArrayString(){

        String yeses = "";
        for ( int i = 0 ; i < this.yes_arr.length ; ++i){

            yeses += Integer.toString(this.yes_arr[i]);
        }

        return yeses;
    }


}
