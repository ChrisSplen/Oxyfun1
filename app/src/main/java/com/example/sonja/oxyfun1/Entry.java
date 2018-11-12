package com.example.sonja.oxyfun1;

import android.view.ViewDebug;

public class Entry {

    private String name;
    private int id_number;
    private String anzeige;


    public static final Entry[] activities = {new Entry("Aktivität", 1),
            new Entry("Aktivität", 2),
            new Entry("Aktivität", 3),
            new Entry("Aktivität", 4),
            new Entry("Aktivität", 5)};

    private  Entry(String name, int id_number){ //Konstruktor
        this.name = name;
        this.id_number = id_number;
        anzeige = this.name + " " + Integer.toString(id_number);

    }

    public String getName(){
        return name;
    }

    public int getId_number(){
        return id_number;
    }

    public String toString(){ //braucht man für die Anzeige der ListView, was hier drin steht wird für die ListView verwendet
        return anzeige;
    }
}
