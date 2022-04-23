package com.example.tokattanitimapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Bitmap SelectedImage;
    EditText txturun_isim,txtUrunFiyat,txtUrunAciklama;
    Spinner si_spinner;
    ArrayList<URUN> urunler;
    Button UrunleriGoster,foto;
    ImageView fotoyeri;
    ActivityResultLauncher<Intent> activityResultLauncher;
    //geriye intent alacağımız için intent tipinde oluşturduk
    //kameraya gitmek için kullanıcaz
    ActivityResultLauncher<String> permissionLauncher;
    //izinlerde geriye string aldığımız için string oluşturduk
    //gittikten sonra napıcaz
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerLauncher();
        txturun_isim = findViewById(R.id.si_txturun_isim);
        txtUrunFiyat = findViewById(R.id.si_etxt_urun_fiyat);
        txtUrunAciklama = findViewById(R.id.si_etxt_urun_aciklama);
        si_spinner = findViewById(R.id.si_spinner);
        foto = findViewById(R.id.btnkamerayibaslat);
        fotoyeri = findViewById(R.id.fotoyeri);
        urunler = new ArrayList<>();
        List<String> spinnerElemanlari = new ArrayList<>();
        spinnerElemanlari.add("kutu");
        spinnerElemanlari.add("kilo");
        spinnerElemanlari.add("adet");
        ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_spinner_item,spinnerElemanlari);
        si_spinner.setAdapter(adapter);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)){
                        Snackbar.make(view,"Kameraya erişim için izin lazım",
                                Snackbar.LENGTH_INDEFINITE).setAction(
                                "izin ver", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //ask permission
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                    }
                                }).show();
                    }else{
                        //ask permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }else{
                    //ask permission
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);                }
            }
        });


        UrunleriGoster = findViewById(R.id.btnUrunListele);
        UrunleriGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListeAktivite.class);
                //intent.putExtra("urunler",urunler);
                Veritabani.Myurunler = urunler;
                startActivity(intent);
            }
        });
    }

    private void registerLauncher(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if (intentFromResult!=null){
                        Uri imageData = intentFromResult.getData();
                        //fotoyeri.setImageURI(imageData);
                        try {
                            if (Build.VERSION.SDK_INT >=28){
                                ImageDecoder.Source source = ImageDecoder.createSource(
                                        MainActivity.this.getContentResolver(),
                                        imageData
                                );
                                Bitmap SelectedImage = ImageDecoder.decodeBitmap(source);
                                fotoyeri.setImageBitmap(SelectedImage);
                            }else{
                                SelectedImage = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(),
                                        imageData);
                                fotoyeri.setImageBitmap(SelectedImage);
                            }

                        }catch (Exception e){
                            Toast.makeText(MainActivity.this,"bir hata ile karşılaşıldı",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                //eğer true ise izin verildi demek
                if (result){
                    //permission granted
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }else{
                    //permission denied
                    Toast.makeText(MainActivity.this,"İzne ihtiyaç var",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void si_urunOlustur(View view) {
        URUN si_ur = new URUN(
                MainActivity.this,
                txturun_isim.getText().toString(),
                txtUrunAciklama.getText().toString(),
                si_spinner.getSelectedItem().toString(),
                Double.parseDouble(
                        txtUrunFiyat.getText().toString()
                )
        );
        si_ur.setFiyat(Double.parseDouble(txtUrunFiyat.getText().toString()));
        urunler.add(si_ur);

        Toast.makeText(
                getApplicationContext(),
                "Urun sayisi " + urunler.size()
                        + "Eklenen :" + urunler.get(
                                urunler.size()-1).getUrunismi(),
                Toast.LENGTH_LONG).show();

    }
}