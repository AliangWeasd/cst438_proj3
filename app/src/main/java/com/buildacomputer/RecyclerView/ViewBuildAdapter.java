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

public class ViewBuildAdapter extends RecyclerView.Adapter<ViewBuildAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> buildNames;

    public ViewBuildAdapter(Context context, ArrayList<String> buildNames) {
        this.context = context;
        this.buildNames = buildNames;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_build_recy,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.bName.setText(buildNames.get(position));

    holder.bLayout.setOnClickListener(view -> {

    });

    }

    @Override
    public int getItemCount() {
        return buildNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bName;
        ConstraintLayout bLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bName = itemView.findViewById(R.id.actual_build_name);
            bLayout = itemView.findViewById(R.id.build_list);

        }
    }
}
