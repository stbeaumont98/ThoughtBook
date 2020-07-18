package com.stbeaumont.thoughtbook.presenter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.stbeaumont.thoughtbook.R;

public class LoginDialog extends AppCompatDialogFragment {

    private Context context;

    LoginDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.login_layout, null);

        SignInButton signInButton = v.findViewById(R.id.gms_sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        builder.setView(v);

        return builder.create();
    }
}
