package com.example.first.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.first.R;
import com.example.first.activities.Home;
import com.example.first.activities.MainActivity;
import com.example.first.activities.RegisterActivity;
import com.example.first.api.ApiClient;
import com.example.first.models.DeleteResponse;
import com.example.first.models.UpdateResponse;
import com.example.first.models.User;
import com.example.first.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingsFragment extends Fragment  implements  View.OnClickListener {

    private EditText editTextEmail, editTextName, editTextPhone;
    private EditText editTextCurrentPassword, editTextOldPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextUser);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextOldPassword = view.findViewById(R.id.current_password);
        editTextCurrentPassword = view.findViewById(R.id.new_password);

        view.findViewById(R.id.updateUser).setOnClickListener(this);
        view.findViewById(R.id.changePassword).setOnClickListener(this);
        view.findViewById(R.id.logout).setOnClickListener(this);
        view.findViewById(R.id.delete_profile).setOnClickListener(this);

    }

    private void updateProfile(){

        String username = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone_number = editTextPhone.getText().toString().trim();

        if(username.isEmpty() & email.isEmpty() & phone_number.isEmpty()){
            editTextName.setError("Edit at least one of the fields");
            editTextName.requestFocus();
            return;
        }

        if(username.isEmpty()){
            username = null;
        }

        if(email.isEmpty()){
            email = null;
        }

        if(phone_number.isEmpty()){
            phone_number = null;
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        int user_id = user.getId();

        Call<UpdateResponse> call = ApiClient.getInstance().getApi().updateUser(
                user_id,
                username,
                email,
                phone_number
        );



        call.enqueue(new Callback<UpdateResponse>() {

            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {

                if(!response.body().isError()){
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }else{
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {

                Toast.makeText(getActivity(),"An Error occured. Try again",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePassword(){

        String old_pass = editTextOldPassword.getText().toString();
        String new_pass = editTextCurrentPassword.getText().toString();

        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        int user_id = user.getId();

        if(old_pass.isEmpty() | new_pass.isEmpty()){
            editTextName.setError("Input both fields");
            editTextName.requestFocus();
            return;
        }

        Call<UpdateResponse> call = ApiClient.getInstance().getApi().updatePassword(
                user_id,
                old_pass,
                new_pass
        );

        call.enqueue(new Callback<UpdateResponse>() {

            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {

                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"An Error occured. Try again",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void logoutUser(){

        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent  = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteUser(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                User user = SharedPrefManager.getInstance(getActivity()).getUser();

                int user_id = user.getId();
 
                Call<DeleteResponse> call = ApiClient.getInstance().getApi().deleteUser(user_id);

                call.enqueue(new Callback<DeleteResponse>() {
                    @Override
                    public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                        if(!response.body().isError()){

                            SharedPrefManager.getInstance(getActivity()).clear();
                            Intent intent  = new Intent(getActivity(), RegisterActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DeleteResponse> call, Throwable t) {

                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.updateUser:
                updateProfile();
                break;
            case R.id.changePassword:
                updatePassword();
                break;
            case R.id.logout:
                logoutUser();
                break;
            case R.id.delete_profile:
                deleteUser();
                break;

        }
    }
}
