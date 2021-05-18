package com.example.first.api;


import com.example.first.models.DeleteResponse;
import com.example.first.models.LoginResponse;
import com.example.first.models.UpdateResponse;
import com.example.first.models.UsersResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("get_users.php")
    Call<UsersResponse> getUsers();

    @FormUrlEncoded
    @POST("edit_user.php")
    Call<UpdateResponse> updateUser(
          @Field("user_id") int user_id,
          @Field("username") String username,
          @Field("email") String email,
          @Field("phone_number") String phone_number
    );

    @FormUrlEncoded
    @POST("change_password.php")
    Call<UpdateResponse> updatePassword(
          @Field("user_id") int user_id,
          @Field("old_pass") String old_pass,
          @Field("new_pass") String new_pass
    );

    @FormUrlEncoded
    @GET("delete_user.php?user_id={user_id}")
    Call<DeleteResponse> deleteUser(
            @Path("user_id") int user_id
    );
    
//    @DELETE("delete_user.php")
//    Call<DeleteResponse> deleteUser(@Path("id") int id);

//    @FormUrlEncoded
//    @PUT("edit_user.php/{id}")
//    Call<UpdateResponse> updateUser(
//            @Path("id") int id,
//            @Field("username") String username,
//            @Field("email") String email,
//            @Field("phone_number") String phone_number
//    );
}
