package com.example.tokattanitimapplication;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

public class URUN implements Serializable {
    private String si_urunismi,si_urunAciklama,si_urunmiktar;
    private double si_fiyat;
    Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public URUN(Context context,String urunismi, String urunaciklama, String urunmiktar, double fiyat) {
        this.si_urunismi = urunismi;
        this.si_urunAciklama = urunaciklama;
        this.si_urunmiktar = urunmiktar;
        this.si_fiyat = fiyat;
        this.context = context;

    }

    public String getUrunismi() {
        return si_urunismi;
    }

    public void setUrunismi(String urunismi) {
        this.si_urunismi = urunismi;
    }

    public String getUrunaciklama() {
        return si_urunAciklama;
    }

    public void setUrunaciklama(String urunaciklama) {
        this.si_urunAciklama = urunaciklama;
    }

    public String getUrunmiktar() {
        return si_urunmiktar;
    }

    public void setUrunmiktar(String urunmiktar) {
        this.si_urunmiktar = urunmiktar;
    }

    public double getFiyat() {
        return si_fiyat;
    }

    public void setFiyat(double fiyat) {
        if (fiyat<0){
            if (context!=null){
                Toast.makeText(context,"ürün fiyatı negatif olamaz",Toast.LENGTH_LONG).show();
            }else{
                this.si_fiyat = fiyat;
            }
        }
    }
}
