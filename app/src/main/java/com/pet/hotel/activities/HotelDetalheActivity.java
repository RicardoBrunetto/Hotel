package com.pet.hotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pet.hotel.R;
import com.pet.hotel.dados.Hotel;

public class HotelDetalheActivity extends AppCompatActivity {

    public static final String EXTRA_HOTEL = "hotel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Hotel hotel = (Hotel)
                intent.getSerializableExtra(EXTRA_HOTEL);

        TextView txtNomeHotel = (TextView) findViewById(R.id.txt_nome_hotel);
        txtNomeHotel.setText(hotel.nome);

        TextView txtEnderecoHotel = (TextView) findViewById(R.id.txt_endereco_hotel);
        txtEnderecoHotel.setText(hotel.endereco);

        RatingBar rbEstrelasHotel = (RatingBar) findViewById(R.id.rtbar_estrelas_hotel);
        rbEstrelasHotel.setRating(hotel.estrelas);
    }

}
