package com.stbeaumont.thoughtbook.model;

import java.util.ArrayList;

public class SortNotes {

    public ArrayList<Notes> getLockedNotes(ArrayList<Notes> allNotes) {
        ArrayList<Notes> locked = new ArrayList<Notes>();

        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).isLocked()) {
                locked.add(allNotes.get(i));
            }
        }

        return locked;
    }

    public ArrayList<Notes> getStarredNotes(ArrayList<Notes> allNotes) {
        ArrayList<Notes> starred = new ArrayList<Notes>();

        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).isStarred()) {
                starred.add(allNotes.get(i));
            }
        }

        return starred;
    }

    public void sortNotes(ArrayList<Notes> allNotes) {
        ArrayList<Notes> starred = getStarredNotes(allNotes);

        int i = 0;
        while (i < allNotes.size()) {
            if (allNotes.get(i).isStarred()) {
                allNotes.remove(i);
            } else {
                i++;
            }
        }

        starred.addAll(allNotes);

        allNotes.clear();
        allNotes.addAll(starred);
    }

}
