package com.stbeaumont.thoughtbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private List<Notes> mNotes;

    public NotesAdapter(List<Notes> notes) {
        mNotes = notes;
    }

    /****************************************************************
     * Grabs the different views from the card layout so they can be
     * changed for each individual card
     ****************************************************************/
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView noteTitle;
        public TextView noteContent;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);

            noteTitle = (TextView) v.findViewById(R.id.note_card_title);
            noteContent = (TextView) v.findViewById(R.id.note_card_content);
        }
    }

    /************************************************************************
     * Inflates the default card layout and creates a new ViewHolder from
     * the layout
     ************************************************************************/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.note_card_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    /***********************************************************************
     * Sets the Views of the card to the information provided by the list
     * of notes
     ***********************************************************************/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes notes = mNotes.get(position);

        TextView titleTextView = holder.noteTitle;
        TextView contentTextView = holder.noteContent;

        titleTextView.setText(notes.getNoteTitle());
        contentTextView.setText(notes.getNoteContent());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Notes tempNote = mNotes.get(fromPosition);
        mNotes.remove(fromPosition);
        mNotes.add(toPosition, tempNote);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mNotes.remove(position);
        notifyItemRemoved(position);
    }

}
