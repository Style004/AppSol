package com.jobd.solapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5000/") // ⚠️ Si usas emulador
                    // Si usas celular físico: usa la IP de tu PC, ej: http://192.168.1.8:5000/
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
