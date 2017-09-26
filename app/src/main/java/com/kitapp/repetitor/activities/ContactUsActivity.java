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

import com.kitapp.repetitor.Api;
import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;

public class ContactUsActivity extends AppCompatActivity implements Api.AboutFormResultListener {

    private EditText etName;
    private EditText etPhone;
    private EditText etMessage;
    private Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_contact_us);
        getSupportActionBar().setTitle(R.string.contact_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etName = (EditText) findViewById(R.id.etAboutName);
        etPhone = (EditText) findViewById(R.id.etAboutPhone);
        etMessage = (EditText) findViewById(R.id.etAboutMessage);
        bSend = (Button) findViewById(R.id.bAboutSend);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendButton();
            }
        });
        App.getInstance().getApi().setAboutFormResultListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().getApi().setAboutFormResultListener(null);
    }

    private void onSendButton() {
        hideSoftKeyboard();
        if (etName.getText().toString().isEmpty()
                || etPhone.getText().toString().isEmpty()
                || etMessage.getText().toString().isEmpty()) {
            showErrorDialog(getString(R.string.error_unfilled_fields));
            return;
        }
        bSend.setEnabled(false);
        App.getInstance().getApi().sendAboutFormData(etName.getText().toString(), etPhone.getText().toString(), etMessage.getText().toString());
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

    private void showSuccessDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ContactUsActivity.this.finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showSuccessDialog(getString(R.string.message_sent));
                bSend.setEnabled(true);
            }
        });
    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showErrorDialog(getString(R.string.no_server_connection));
                bSend.setEnabled(true);
            }
        });
    }
}
