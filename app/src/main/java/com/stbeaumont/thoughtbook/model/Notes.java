package com.stbeaumont.thoughtbook.model;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class Notes {

    static int TEXT = 0;

    private String noteTitle;
    private String noteContent;
    private Map<String, Boolean> properties = new HashMap<String, Boolean>();
    private Bitmap image;

    public Notes (String title, String content){
        noteTitle = title;
        noteContent = content;
        properties.put("locked", false);
        properties.put("starred", false);
    }

    public String getNoteTitle(){
        return noteTitle;
    }

    public String getNoteContent(){
        return noteContent;
    }

    public void setLock(Boolean b) {
        properties.put("locked", b);
    }

    public void setStarred(Boolean b) {
        properties.put("starred", b);
    }

    public Boolean isLocked() {
        return properties.get("locked");
    }

    public Boolean isStarred() {
        return properties.get("starred");
    }

}
