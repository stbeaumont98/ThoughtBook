package com.stbeaumont.thoughtbook;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class BottomNavMenuFragment extends BottomSheetDialogFragment {

    NavigationView.OnNavigationItemSelectedListener context;

    public BottomNavMenuFragment(NavigationView.OnNavigationItemSelectedListener context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_nav_drawer, container, false);
        NavigationView navigationView = v.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(context);
        return v;
    }
}
