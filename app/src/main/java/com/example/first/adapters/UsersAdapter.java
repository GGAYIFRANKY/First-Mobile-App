package com.example.first.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first.R;
import com.example.first.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mCtxt;
    private List<User> UserList;

    public UsersAdapter(Context mCtxt, List<User> userList) {
        this.mCtxt = mCtxt;
        UserList = userList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtxt).inflate(R.layout.recyclerview_users,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = UserList.get(position);

        holder.textViewName.setText(user.getUsername());
        holder.textViewEmail.setText(user.getEmail());
        holder.textViewPhone.setText(user.getPhone_number());


    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewEmail, textViewPhone;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);


        }
    }

}
