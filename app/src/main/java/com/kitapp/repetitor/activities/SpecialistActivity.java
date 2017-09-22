package com.kitapp.repetitor.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.entities.Repetitor;

public class SpecialistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_specialist);
        Repetitor r = App.getInstance().getApi().getRepetotor(getIntent().getIntExtra("ID", 0));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.repetitor);
        ((TextView) findViewById(R.id.tvInfoName)).setText(r.getFio());
        ((TextView) findViewById(R.id.tvInfoAge)).setText(r.getAge() >= 0 ? String.valueOf(r.getAge()) : "-");
        ((TextView) findViewById(R.id.tvInfoSubject)).setText(r.getDiscipline());
        ((TextView) findViewById(R.id.tvInfoPrice)).setText(r.getPrice() >= 0 ? String.valueOf(r.getPrice()) + " грн/" + r.getUnits() : "-");
        ((TextView) findViewById(R.id.tvInfoPhone)).setText(r.getPhone());
        ((TextView) findViewById(R.id.tvInfoCity)).setText(App.getInstance().getApi().getCityById(r.getCity_id()).getName());
        ((TextView) findViewById(R.id.tvInfoExperience)).setText(r.getStage() >= 0 ? String.valueOf(r.getStage()) : "-");
        final Button buttonFav = (Button) findViewById(R.id.bInfoFav);
        Button buttonContact = (Button) findViewById(R.id.bInfoContact);
        if (getIntent().getBooleanExtra("fav", false)) {
            buttonFav.setEnabled(false);
        } else {
            buttonFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getApi().setFavorite(getIntent().getIntExtra("ID", 0), true);
                    Toast.makeText(SpecialistActivity.this, "Добавлен в избранные", Toast.LENGTH_LONG).show();
                    buttonFav.setEnabled(false);
                }
            });
        }
        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContactButton();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onContactButton() {
        Intent intent = new Intent(SpecialistActivity.this, ContactActivity.class);
        intent.putExtra("ID", getIntent().getIntExtra("ID", 0));
        startActivity(intent);
    }
}
