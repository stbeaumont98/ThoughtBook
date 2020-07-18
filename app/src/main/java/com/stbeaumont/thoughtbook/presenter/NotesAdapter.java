package com.stbeaumont.thoughtbook.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.stbeaumont.thoughtbook.model.Notes;
import com.stbeaumont.thoughtbook.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private List<Notes> mNotes;
    private NoteClickListener mNoteClickListener;

    public NotesAdapter(List<Notes> notes, NoteClickListener noteClickListener) {
        mNotes = notes;
        mNoteClickListener = noteClickListener;
    }

    /****************************************************************
     * Grabs the different views from the card layout so they can be
     * changed for each individual card
     ****************************************************************/
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView noteTitle;
        public TextView noteContent;
        public ImageView iconStar;
        public ImageView iconLock;
        public ConstraintLayout constraintTitle;

        NoteClickListener noteClickListener;

        public ViewHolder(View v, NoteClickListener noteClickListener) {
            super(v);

            noteTitle = v.findViewById(R.id.note_card_title);
            noteContent = v.findViewById(R.id.note_card_content);
            iconStar = v.findViewById(R.id.iconStar);
            iconLock = v.findViewById(R.id.iconLock);
            constraintTitle = v.findViewById(R.id.constraintTitle);

            this.noteClickListener = noteClickListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            noteClickListener.onNoteClick(getAdapterPosition());
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

        View v = inflater.inflate(R.layout.note_card_layout, parent, false);

        return new ViewHolder(v, mNoteClickListener);
    }

    /***********************************************************************
     * Sets the Views of the card to the information provided by the list
     * of notes
     ***********************************************************************/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes note = mNotes.get(position);

        TextView textTitle = holder.noteTitle;
        TextView textContent = holder.noteContent;
        ImageView iconStar = holder.iconStar;
        ImageView iconLock = holder.iconLock;
        ConstraintLayout constraintTitle = holder.constraintTitle;

        textTitle.setText(note.getNoteTitle());
        textContent.setText(note.getNoteContent());

        if (note.getNoteContent().isEmpty() || note.isLocked()) {
            textContent.setVisibility(View.GONE);
        } else {
            textContent.setVisibility(View.VISIBLE);
        }

        if (note.getNoteTitle().isEmpty() && !note.isLocked() && !note.isStarred()) {
            constraintTitle.setVisibility(View.GONE);
        } else {
            constraintTitle.setVisibility(View.VISIBLE);
        }

        if (note.isLocked()) {
            iconLock.setVisibility(View.VISIBLE);
        } else {
            iconLock.setVisibility(View.GONE);
        }

        if (note.isStarred()) {
            iconStar.setVisibility(View.VISIBLE);
        } else {
            iconStar.setVisibility(View.GONE);
        }
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

    public interface NoteClickListener {
        void onNoteClick(int position);
    }

}
