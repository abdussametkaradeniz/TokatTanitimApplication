package com.example.tokattanitimapplication;

import java.util.ArrayList;

public class Veritabani {
    static ArrayList<URUN> Myurunler = new ArrayList<>();

    public static ArrayList<URUN> getMyurunler() {
        return Myurunler;
    }

    public static void setMyurunler(ArrayList<URUN> myurunler) {
        Myurunler = myurunler;
    }
}
