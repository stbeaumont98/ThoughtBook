package com.stbeaumont.thoughtbook.presenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stbeaumont.thoughtbook.R;

import java.util.Objects;


public class EditNoteFragment extends Fragment {

    EditText editTitle;
    EditText editContent;
    BottomAppBar bottomBar;
    FloatingActionButton fab;
    TextView title;
    ActionBar actionBar;

    Boolean locked = false;
    Boolean starred = false;

    int position;

    public EditNoteFragment() {
        //Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuLock:
                        locked = !locked;
                        item.setIcon(locked ? R.drawable.ic_round_lock : R.drawable.ic_round_lock_open);
                        return true;
                    case R.id.menuStar:
                        starred = !starred;
                        item.setIcon(starred ? R.drawable.ic_star : R.drawable.ic_star_border);
                        return true;
                    case R.id.menuAddReminder:
                        //add reminder
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_note, container, false);

        setHasOptionsMenu(true);

        title.setVisibility(View.GONE);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        bottomBar.setNavigationIcon(null);
        bottomBar.replaceMenu(R.menu.edit_menu);
        bottomBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        bottomBar.performShow();
        fab.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext(), R.drawable.ic_round_add));
        MainActivity.EDIT_MODE = true;

        editTitle = v.findViewById(R.id.editTextTitle);
        editContent = v.findViewById(R.id.editTextNoteContent);

        MenuItem menuLock = bottomBar.getMenu().findItem(R.id.menuLock);
        MenuItem menuStar = bottomBar.getMenu().findItem(R.id.menuStar);

        if (getArguments() != null) {
            String[] note = getArguments().getStringArray("note");
            position = getArguments().getInt("position");
            locked = getArguments().getBoolean("locked");
            starred = getArguments().getBoolean("starred");

            if (note != null) {
                editTitle.setText(note[0]);
                editContent.setText(note[1]);
                menuLock.setIcon(locked ? R.drawable.ic_round_lock : R.drawable.ic_round_lock_open);
                menuStar.setIcon(starred ? R.drawable.ic_star : R.drawable.ic_star_border);
            }
        }

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu_top_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSave:
                Bundle b = new Bundle();
                b.putInt("position", position);
                b.putStringArray("note", new String[] {editTitle.getText().toString(), editContent.getText().toString()});
                b.putBoolean("locked", locked);
                b.putBoolean("starred", starred);

                Fragment homeFragment = null;
                if (getFragmentManager() != null) {
                    homeFragment = getFragmentManager().findFragmentByTag("home");
                    if (homeFragment != null) {
                        homeFragment.setArguments(b);
                    }
                    getFragmentManager().popBackStack();
                }
                return true;
            case android.R.id.home:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUIElements(ActionBar actionBar, BottomAppBar bar, FloatingActionButton fab, TextView title) {
        this.actionBar = actionBar;
        this.bottomBar = bar;
        this.fab = fab;
        this.title = title;
    }
}