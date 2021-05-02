package com.example.first;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone_number") String phone_number,
            @Field("password_1") String password_1,
            @Field("password_2") String password_2

    );
}
