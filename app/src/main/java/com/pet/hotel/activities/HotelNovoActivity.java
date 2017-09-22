package com.pet.hotel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.pet.hotel.R;
import com.pet.hotel.dados.Hotel;

public class HotelNovoActivity extends AppCompatActivity {

    Button btAdd;
    EditText etNome;
    EditText etEndereco;
    RatingBar rbStarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_novo);

        // HOME BUTTON
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNome = (EditText)findViewById(R.id.etNome);
        etEndereco = (EditText)findViewById(R.id.etEndereco);
        rbStarts = (RatingBar)findViewById(R.id.rbStars);

        btAdd = (Button)findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hotel novoHotel = new Hotel(etNome.getText().toString(), etEndereco.getText().toString(), rbStarts.getRating());
                MainActivity.db.inserir(novoHotel);
                setResult(MainActivity.RESULT_UPDATE_LIST);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean r = super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return r;
    }
}
