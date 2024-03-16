package com.example.m8uf2avaluable2radio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<Integer, String> radioURL = new HashMap<>();
    private HashMap<Integer, String> radioNombre = new HashMap<>();
    private HashMap<Integer, String> radioDescripcion = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMaps();
    }

    public void actionButton(View view){
        Intent intent = new Intent(this.getApplicationContext(), SecondActivity.class);
        intent.putExtra("url", radioURL.get(view.getId()));
        intent.putExtra("nombre", radioNombre.get(view.getId()));
        intent.putExtra("descripcion", radioDescripcion.get(view.getId()));
        this.startActivity(intent);
    }

    private void initMaps() {
        radioURL.put(R.id.imageButton, "https://27833.live.streamtheworld.com/LOS40_SC");
        radioNombre.put(R.id.imageButton, "Los 40");
        radioDescripcion.put(R.id.imageButton, "Descripcion de la radio");

        radioURL.put(R.id.imageButton2, "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40_CLASSIC_SC");
        radioNombre.put(R.id.imageButton2, "Los 40 Classic");
        radioDescripcion.put(R.id.imageButton2, "Descripcion de la radio");

        radioURL.put(R.id.imageButton3, "https://playerservices.streamtheworld.com/api/livestream-redirect/LOS40_DANCE_SC");
        radioNombre.put(R.id.imageButton3, "Los 40 dance");
        radioDescripcion.put(R.id.imageButton3, "Descripcion de la radio");



    }

}
