package com.stbeaumont.thoughtbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Queue;


public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private BottomAppBar bottomBar;
    private FloatingActionButton fab;
    private TextView title;
    private ActionBar actionBar;

    public static boolean EDIT_MODE = false;

    private ArrayList<Notes> notes;

    private Queue<Notes> trash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.textTitle);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);

        notes = new ArrayList<Notes>();

        bottomBar = (BottomAppBar) findViewById(R.id.bar);

        bottomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavMenuFragment navFragment = new BottomNavMenuFragment();
                navFragment.show(getSupportFragmentManager(), navFragment.getTag());
            }
        });

        /*
        bottomBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    default:
                        return false;
                }
            }
        });
        */

        fab = (FloatingActionButton) findViewById(R.id.fab);

        HomeFragment homeFragment = new HomeFragment(notes);

        homeFragment.setUIElements(actionBar, bottomBar, fab, title);

        transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, homeFragment, "home");
        transaction.commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!EDIT_MODE) {

                EditNoteFragment editNoteFragment = new EditNoteFragment();
                editNoteFragment.setUIElements(actionBar, bottomBar, fab, title);

                Bundle b = new Bundle();
                b.putInt("position", notes.size());
                b.putStringArray("note", new String[] {"", ""});

                editNoteFragment.setArguments(b);

                //replace the fragment
                Fragment f = getSupportFragmentManager().findFragmentByTag("edit_note");

                transaction = getSupportFragmentManager().beginTransaction();
                if (f == null) {
                    transaction.replace(R.id.flFragment, editNoteFragment, "edit_note");
                } else {
                    transaction.replace(R.id.flFragment, f);
                }
                transaction.addToBackStack(null);
                transaction.commit();

            } else {

            }
            }
        });
    }
}
