package com.stbeaumont.thoughtbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bottomBar;
    FloatingActionButton fab;

    boolean EDIT_MODE = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomBar = (BottomAppBar) findViewById(R.id.bar);

        bottomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationMenu navFragment = new BottomNavigationMenu();
                navFragment.show(getSupportFragmentManager(), navFragment.getTag());
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                        @Override
                        public void onShown(final FloatingActionButton fab) {
                            super.onShown(fab);
                        }

                        @Override
                        public void onHidden(final FloatingActionButton fab) {
                            super.onHidden(fab);
                            if(!EDIT_MODE) {
                                bottomBar.setNavigationIcon(null);
                                bottomBar.replaceMenu(R.menu.edit_menu);
                                bottomBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_round_add));
                                EDIT_MODE = true;
                            } else {
                                bottomBar.replaceMenu(R.menu.home_menu);
                                bottomBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_outline_create));
                                bottomBar.setNavigationIcon(R.drawable.ic_round_menu);
                                EDIT_MODE = false;
                            }
                            fab.show();
                        }
                    }
                );
            }
        });


        RecyclerView rvNotes = (RecyclerView) findViewById(R.id.notesRecyclerView);

        ArrayList<Notes> notesList = new ArrayList<Notes>();

        for(int i = 0; i < 5; i++) {
            notesList.add(new Notes("Note #1", "This is an example of a note!"));
            notesList.add(new Notes("Note #2", "This is an example of a note! I'm making this note bigger to display a different size card."));
            notesList.add(new Notes("Note #3", "This is an example of a note! I'm making this note bigger to display a different size card. And this one is even larger than the other one!"));
        }

        NotesAdapter adapter = new NotesAdapter(notesList);

        rvNotes.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        rvNotes.setLayoutManager(staggeredGridLayoutManager);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvNotes);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


}
