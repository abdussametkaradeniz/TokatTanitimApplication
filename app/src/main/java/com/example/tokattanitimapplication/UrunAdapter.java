package com.example.tokattanitimapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class UrunAdapter extends ArrayAdapter<URUN> {

    Context context;
    public UrunAdapter(@NonNull Context context, int resource, @NonNull List<URUN> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //main aktivityden oluşmamış bir sınıfın elemanlarına inflater yardımıyla erişiyoruz
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(
                R.layout.urun_satir,
                parent,
                false);

        URUN urun = getItem(position);

        TextView isim = convertView.findViewById(R.id.txturunisim);
        TextView fiyat = convertView.findViewById(R.id.urunfiyat);
        TextView aciklama = convertView.findViewById(R.id.urunaciklama);
        Button siparisver = convertView.findViewById(R.id.btnsiparisver);

        isim.setText(urun.getUrunismi());
        fiyat.setText(String.valueOf(urun.getFiyat()));
        aciklama.setText(urun.getUrunaciklama());
        
        siparisver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        context,
                        urun.getUrunismi()+ " siparişiniz alınmıştır",
                        Toast.LENGTH_SHORT).show();
            }
        });
        
        //return super.getView(position, convertView, parent);

        return convertView;
    }
}
