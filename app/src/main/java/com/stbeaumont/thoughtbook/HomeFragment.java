package com.stbeaumont.thoughtbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment implements NotesAdapter.NoteClickListener {

    BottomAppBar bottomBar;
    FloatingActionButton fab;
    ActionBar actionBar;
    TextView title;
    TextView textEmpty;

    boolean toggle = false;

    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<Notes> notes;
    RecyclerView rvNotes;

    public HomeFragment(ArrayList<Notes> notes) {
        this.notes = notes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //populate notes

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        setHasOptionsMenu(true);

        rvNotes = v.findViewById(R.id.notesRecyclerView);
        textEmpty = v.findViewById(R.id.textEmpty);

        NotesAdapter adapter = new NotesAdapter(notes, this);

        rvNotes.setAdapter(adapter);

        linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        rvNotes.setLayoutManager(toggle ? staggeredGridLayoutManager : linearLayoutManager);


        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvNotes);

        title.setVisibility(View.VISIBLE);

        bottomBar.setNavigationIcon(R.drawable.ic_round_menu);
        bottomBar.replaceMenu(R.menu.home_menu);
        bottomBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_outline_create));
        MainActivity.EDIT_MODE = false;

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            int position = getArguments().getInt("position");
            String[] note = getArguments().getStringArray("note");

            if (note != null) {
                if (position != notes.size()) {
                    notes.set(position, new Notes(note[0], note[1]));
                } else {
                    notes.add(new Notes(note[0], note[1]));
                }
            }

            notes.get(position).setLock(getArguments().getBoolean("locked"));
        }

        if (notes.size() > 0) {
            textEmpty.setVisibility(View.GONE);
            rvNotes.setVisibility(View.VISIBLE);
        } else {
            textEmpty.setVisibility(View.VISIBLE);
            rvNotes.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu_top, menu);
        menu.getItem(0).setIcon(toggle ? R.drawable.ic_card_view : R.drawable.ic_outline_dashboard);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuToggleView) {
            toggle = !toggle;
            rvNotes.setLayoutManager(toggle ? staggeredGridLayoutManager : linearLayoutManager);
            item.setIcon(toggle ? R.drawable.ic_card_view : R.drawable.ic_outline_dashboard);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(int position) {

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putStringArray("note", new String[] {notes.get(position).getNoteTitle(), notes.get(position).getNoteContent()});
        b.putBoolean("locked", notes.get(position).isLocked());

        EditNoteFragment editNoteFragment = new EditNoteFragment();
        editNoteFragment.setUIElements(actionBar, bottomBar, fab, title);
        editNoteFragment.setArguments(b);

        FragmentTransaction transaction = null;
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        }

        if (transaction != null) {
            transaction.replace(R.id.flFragment, editNoteFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void setUIElements(ActionBar actionBar, BottomAppBar bar, FloatingActionButton fab, TextView title) {
        this.actionBar = actionBar;
        this.bottomBar = bar;
        this.fab = fab;
        this.title = title;
    }
}