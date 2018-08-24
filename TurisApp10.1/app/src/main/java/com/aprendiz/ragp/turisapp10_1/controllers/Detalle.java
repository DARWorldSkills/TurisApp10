package com.aprendiz.ragp.turisapp10_1.controllers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aprendiz.ragp.turisapp10_1.R;
import com.aprendiz.ragp.turisapp10_1.models.Lugares;
import com.bumptech.glide.Glide;

public class Detalle extends AppCompatActivity {
    public static Lugares lugares = new Lugares();
    TextView txtNombre, txtDescripcion;
    ImageView imgDetalle;
    FloatingActionButton btnUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        inizialite();
        inputData();
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalle.this,Todos.class);
                startActivity(intent);
            }
        });

    }

    private void inizialite() {
        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcionD);
        imgDetalle = findViewById(R.id.imgDetalle);
        btnUbicacion = findViewById(R.id.btnUbicacion);
    }

    private void inputData() {
        lugares = MenuT.lugares;
        txtNombre.setText(lugares.getNombre());
        txtDescripcion.setText(lugares.getDescripcion());
        Glide.with(this).load(lugares.getUrl()).into(imgDetalle);


    }

    @Override
    protected void onResume() {
        super.onResume();
        inizialite();
        inputData();
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalle.this,Todos.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        inizialite();
        inputData();
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalle.this,Todos.class);
                startActivity(intent);
            }
        });
    }
}
