package com.buildacomputer.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.R;

import java.util.ArrayList;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> username;
    ArrayList<String> email;

    public AdminUserAdapter (Context context, ArrayList<String> username, ArrayList<String> email) {
        this.context = context;
        this.username = username;
        this.email = email;
    }

    @NonNull
    @Override
    public AdminUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.MyViewHolder holder, final int position) {
        holder.uName.setText(username.get(position));
        holder.mEmail.setText(email.get(position));

        /* holder.myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminEditActivity.intentFactory(context);
                intent.putExtra("USERNAME",username.get(position));
                context.startActivity(intent);
            }
        }); */
    }

    @Override
    public int getItemCount() {
        return username.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView uName;
        TextView mEmail;
        //ConstraintLayout mLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uName = itemView.findViewById(R.id.username_recy);
            mEmail = itemView.findViewById(R.id.email_recy);
        }
    }
}
