package com.kitapp.repetitor.fragments;


import android.content.Context;
import android.content.DialogInterface;
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

import com.kitapp.repetitor.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    private TextView tvAbout;
    private EditText etName;
    private EditText etPhone;
    private EditText etMessage;

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
        // TODO fill tvAbout text field
        etName = (EditText) v.findViewById(R.id.etAboutName);
        etPhone = (EditText) v.findViewById(R.id.etAboutPhone);
        etMessage = (EditText) v.findViewById(R.id.etAboutMessage);
        Button bSend = (Button) v.findViewById(R.id.bAboutSend);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendButton();
            }
        });
        return v;
    }

    private void onSendButton() {
        hideSoftKeyboard();
        if (etName.getText().toString().isEmpty()
                || etPhone.getText().toString().isEmpty()
                || etMessage.getText().toString().isEmpty()) {
            showErrorDialog(getString(R.string.error_unfilled_fields));
            return;
        }
        // TODO
    }

    private void showErrorDialog(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle(R.string.error)
                .setMessage(error)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
