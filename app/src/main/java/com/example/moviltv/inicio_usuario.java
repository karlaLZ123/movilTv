package com.example.moviltv;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import androidx.appcompat.app.AppCompatActivity;


public class inicio_usuario extends AppCompatActivity {

    private RequestQueue conexionServidor;
    private StringRequest peticionServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_usuario);

    }



}
