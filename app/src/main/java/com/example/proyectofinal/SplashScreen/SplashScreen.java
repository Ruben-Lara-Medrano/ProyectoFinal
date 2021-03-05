package com.example.proyectofinal.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.proyectofinal.Login;
import com.example.proyectofinal.R;

public class SplashScreen extends AppCompatActivity {
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad Principal de la aplicación
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            };
        }, 7000);
    }
}