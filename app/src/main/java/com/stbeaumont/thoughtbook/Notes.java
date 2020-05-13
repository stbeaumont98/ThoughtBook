package com.stbeaumont.thoughtbook;

import java.util.ArrayList;

public class Notes {

    static int TEXT = 0;

    private String noteTitle;
    private String noteContent;

    public Notes (String title, String content){
        noteTitle = title;
        noteContent = content;
    }

    public String getNoteTitle(){
        return noteTitle;
    }

    public String getNoteContent(){
        return noteContent;
    }

}
