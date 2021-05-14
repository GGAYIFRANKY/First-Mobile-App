package com.example.first.fragments;

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
import com.example.first.api.ApiClient;
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
        editTextCurrentPassword = view.findViewById(R.id.current_password);
        editTextOldPassword = view.findViewById(R.id.new_password);

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

                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                if(!response.body().isError()){
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {

                Toast.makeText(getActivity(),"An Error occured",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.updateUser:
                updateProfile();
                break;
            case R.id.changePassword:
                break;
            case R.id.logout:
                break;
            case R.id.delete_profile:
                break;

        }
    }
}
