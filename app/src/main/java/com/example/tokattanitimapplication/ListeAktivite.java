package com.example.tokattanitimapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListeAktivite extends AppCompatActivity {
    ListView listem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_aktivite);
        listem = findViewById(R.id.liste);
        ArrayList<URUN> urunler = Veritabani.Myurunler;

        // (ArrayList<URUN>)getIntent().getSerializableExtra("urunler");

        Toast.makeText(ListeAktivite.this,urunler.get(0).getUrunismi(),Toast.LENGTH_LONG).show();

        UrunAdapter urunAdapter = new UrunAdapter(
                ListeAktivite.this,
                0,
                urunler);

        listem.setAdapter(urunAdapter);




    }
}