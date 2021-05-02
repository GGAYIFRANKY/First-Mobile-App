package com.example.first;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    Button btnReg;
    EditText regUsername, regEmail, regPhone,  regPassword1, regPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = findViewById(R.id.login_btn);
        regUsername = findViewById(R.id.username_reg);
        regEmail  = findViewById(R.id.email_reg);
        regPhone  = findViewById(R.id.phone_reg);
        regPassword1 = findViewById(R.id.password_reg);
        regPassword2 = findViewById(R.id.password_reg2);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(regUsername.getText().toString()) || TextUtils.isEmpty(regEmail.getText().toString()) || TextUtils.isEmpty(regPhone.getText().toString()) || TextUtils.isEmpty(regPassword1.getText().toString())
                || TextUtils.isEmpty(regPassword2.getText().toString())){

                    String message = "All inputs are required for registration...";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

                }else{

                    String username = regUsername.getText().toString();
                    String email = regEmail.getText().toString();
                    String phone_number = regPhone.getText().toString();
                    String password_1 = regPassword1.getText().toString();
                    String password_2 = regPassword2.getText().toString();

                    Call<ResponseBody> call = ApiClient
                            .getInstance()
                            .getApi()
                            .register(username,email,phone_number,password_1,password_2);

                    call.enqueue(new Callback<ResponseBody>() {

                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String message = "Registered Succesfully";
                            Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            String message = "An error occured. Please try again later...";
                            Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                    });

//                    RegisterRequest registerRequest = new RegisterRequest();
//
//                    registerRequest.setUsername(regUsername.getText().toString());
//                    registerRequest.setEmail(regEmail.getText().toString());
//                    registerRequest.setPhone_number(regPhone.getText().toString());
//                    registerRequest.setPassword_1(regPassword1.getText().toString());
//                    registerRequest.setPassword_2(regPassword2.getText().toString());
//
//                    registerUser(registerRequest);
                }

            }
        });

    }

//    public void registerUser(RegisterRequest registerRequest){
//
//
//        Call<RegisterResponse> registerResponseCall = ApiClient.getService().register(registerRequest);
//
//        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//
//                if(response.isSuccessful()){
//
//                    String message = "Registered Succesfully";
//                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                    finish();
//                }else{
//
//                    String message = "An error occured. Please try again later...";
//                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//
//                String message = t.getLocalizedMessage();
//                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}