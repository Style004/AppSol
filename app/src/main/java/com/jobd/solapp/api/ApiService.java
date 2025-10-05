package com.jobd.solapp.api;

import com.jobd.solapp.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public class ApiService {
    @POST("/login")
    Call<Usuario> login(@Body LoginRequest loginRequest) {
        return null;
    }
}
