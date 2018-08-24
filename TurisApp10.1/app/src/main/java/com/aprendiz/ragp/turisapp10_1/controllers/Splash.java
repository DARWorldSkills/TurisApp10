package com.aprendiz.ragp.turisapp10_1.controllers;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aprendiz.ragp.turisapp10_1.R;
import com.aprendiz.ragp.turisapp10_1.models.Lugares;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    public static List<Lugares> lugaresList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        inputData();

    }
    //Método para ingresar los datos del WebService al proyecto
    private void inputData() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().build();
        StrictMode.setThreadPolicy(policy);

        String urlLugares ="https://prueba-a473c.firebaseio.com/Lugares.json";
        URL url;
        HttpURLConnection connection;
        try {
            url = new URL(urlLugares);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine="";

            while ((inputLine=reader.readLine())!=null){
                response.append(inputLine);
            }
            String json = response.toString();

            Gson gson = new Gson();
            Type type = new TypeToken<List<Lugares>>(){}.getType();
            lugaresList = gson.fromJson(json,type);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this,MenuT.class);
                    startActivity(intent);
                    finish();
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask,1500);
        }catch (Exception e){
            Log.e("Error en consumo",e.getMessage());
            Toast.makeText(this, "No tienes acceso a internet, por favor conectese a una", Toast.LENGTH_SHORT).show();
            inputData();
        }
    }

}
