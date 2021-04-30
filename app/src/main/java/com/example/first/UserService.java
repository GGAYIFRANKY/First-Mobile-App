package com.example.first;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("register.php")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
}
