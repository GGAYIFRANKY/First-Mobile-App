package com.example.first.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.first.R;
import com.example.first.api.ApiClient;
import com.example.first.models.LoginResponse;
import com.example.first.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements  View.OnClickListener{
    private Button btnLogin;
    private EditText edUsername, edPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.login_btn);
        edUsername = findViewById(R.id.username_lg);
        edPassword = findViewById(R.id.password_lg);

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.reg_btn).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent  = new Intent(this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void userLogin() {
                if(TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())){

                    String message = "All inputs are required for login...";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                }else{

                    String username = edUsername.getText().toString();
                    String password = edPassword.getText().toString();

                    Call<LoginResponse> call = ApiClient
                            .getInstance()
                            .getApi()
                            .login(username,password);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            LoginResponse loginResponse = response.body();

                            if(!loginResponse.isError()){

                                //Toast.makeText(MainActivity.this,loginResponse.getMessage(),Toast.LENGTH_LONG);

                                SharedPrefManager.getInstance(MainActivity.this).saveUser(loginResponse.getUser());
                                Intent intent  = new Intent(MainActivity.this,Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }else{

                                Toast.makeText(MainActivity.this,loginResponse.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            String message = "An error occured. Please try again later...";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                    });
                }
    }

    public void onRegister() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                userLogin();
                break;
            case R.id.reg_btn:
                onRegister();
                break;
        }
    }

//    public void loginUser(LoginRequest loginRequest){
//
//        Call<LoginResponse> loginResponseCall = ApiClient.getService().login(loginRequest);
//
//        loginResponseCall.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                if(response.isSuccessful()){
//                    LoginResponse loginResponse = response.body();
//                    startActivity(new Intent(MainActivity.this, Home.class).putExtra("data", loginResponse));
//                    finish();
//
//                }else{
//
//                    String message = "An error occured. Please try again later...";
//                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//                String message = t.getLocalizedMessage();
//                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}