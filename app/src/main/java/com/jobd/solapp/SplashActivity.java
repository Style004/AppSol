package com.jobd.solapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_MS = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Espera SPLASH_MS y abre MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Usa el nombre de la clase directamente, sin el paquete
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish(); // evita volver al splash con el botón de atrás
        }, SPLASH_MS);
    }
}