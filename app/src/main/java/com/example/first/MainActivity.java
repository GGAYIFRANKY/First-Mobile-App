package com.example.first;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    Button btnLogin;
    EditText edUsername, edPassword;
    TextView noAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.login_btn);
        edUsername = findViewById(R.id.username_lg);
        edPassword = findViewById(R.id.password_lg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString())){

                    String message = "All inputs are required for registration...";
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


                            startActivity(new Intent(MainActivity.this, Home.class).putExtra("data", loginResponse));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            String message = "An error occured. Please try again later...";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                    });

//                    LoginRequest loginRequest = new LoginRequest();
//
//                    loginRequest.setUsername(edUsername.getText().toString());
//                    loginRequest.setPassword(edPassword.getText().toString());
//
//
//                    loginUser(loginRequest);
                }
            }
        });
    }

    public void onRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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