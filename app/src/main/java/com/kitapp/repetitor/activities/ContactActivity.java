package com.kitapp.repetitor.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.kitapp.repetitor.R;

public class ContactActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPhone;
    private EditText etPlace;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contact);
        getSupportActionBar().setTitle(R.string.contact_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPlace = (EditText) findViewById(R.id.etPlace);
        etComment = (EditText) findViewById(R.id.etComment);
        Button bSend = (Button) findViewById(R.id.bContactSend);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonSend();
            }
        });
    }

    private void onButtonSend() {
        hideSoftKeyboard();
        if (etName.getText().toString().isEmpty()
                || etPhone.getText().toString().isEmpty()
                || etPlace.getText().toString().isEmpty()) {
            showErrorDialog(getString(R.string.error_unfilled_fields));
            return;
        }
        // TODO
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showErrorDialog(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
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
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
