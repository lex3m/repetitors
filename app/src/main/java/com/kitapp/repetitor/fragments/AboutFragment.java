package com.kitapp.repetitor.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kitapp.repetitor.Api;
import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.activities.ContactUsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    private TextView tvAbout;
    private Button bContactUs;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_about, container, false);
        tvAbout = (TextView) v.findViewById(R.id.tvAboutText);
        bContactUs = (Button) v.findViewById(R.id.bContactUs);
        bContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContactUsButton();
            }
        });
        return v;
    }

    private void onContactUsButton() {
        Intent intent = new Intent(getActivity(), ContactUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
