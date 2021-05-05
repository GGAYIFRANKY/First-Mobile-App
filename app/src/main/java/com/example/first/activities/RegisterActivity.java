package com.example.first.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.first.R;
import com.example.first.api.ApiClient;
import com.example.first.storage.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity implements View.OnClickListener {

    Button btnReg;
    EditText regUsername, regEmail, regPhone,  regPassword1, regPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        regUsername = findViewById(R.id.username_reg);
        regEmail = findViewById(R.id.email_reg);
        regPhone = findViewById(R.id.phone_reg);
        regPassword1 = findViewById(R.id.password_reg);
        regPassword2 = findViewById(R.id.password_reg2);

        findViewById(R.id.reg_btn).setOnClickListener(this);

    }

    public void userSignup(){

                if(TextUtils.isEmpty(regUsername.getText().toString()) || TextUtils.isEmpty(regEmail.getText().toString()) || TextUtils.isEmpty(regPhone.getText().toString()) || TextUtils.isEmpty(regPassword1.getText().toString())
                || TextUtils.isEmpty(regPassword2.getText().toString())){

                    if(!Patterns.EMAIL_ADDRESS.matcher(regUsername.getText().toString()).matches()){
                        String message = "Invalid email address format";
                        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    }

                    if(regPassword2.getText().toString().length() < 6){
                        String message = "Password length should be more than 6 characters";
                        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    }

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

                            String s = null;

                            try{

                                if(response.code() == 201){
                                    s = response.body().string();
                                }else{
                                    s = response.errorBody().string();
                                }
                                //Toast.makeText(RegisterActivity.this,s, Toast.LENGTH_LONG).show();

                            }catch(IOException e){
                                e.printStackTrace();
                            }

                            if(s != null){

                                try{
                                    JSONObject jsonObject = new JSONObject(s);
                                    Toast.makeText(RegisterActivity.this,jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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

//                    Toast.makeText(RegisterActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                    finish();
                }



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


    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent  = new Intent(this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {

        userSignup();
    }
}