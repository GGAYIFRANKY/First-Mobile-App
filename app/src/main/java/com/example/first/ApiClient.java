package com.example.first;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://165.22.184.254/nikel_test/api/v1/";
    private static ApiClient mInstance;
    private Retrofit retrofit;

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized  ApiClient getInstance(){
        if(mInstance == null){
            mInstance = new ApiClient();
        }
        return  mInstance;
    }

    public UserService getApi(){
        return  retrofit.create(UserService.class);
    }
}
