package com.buildacomputer.RecyclerView;

// There is almost assuredly a smarter way for how a Recycler can decide on which static intent to use.

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.ViewPartActivity;
import com.buildacomputer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchPartAdapter extends RecyclerView.Adapter<SearchPartAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> name;
    ArrayList<String> imageText;
    ArrayList<Integer> compPartType;

    public SearchPartAdapter(Context context, ArrayList<String> name, ArrayList<Integer> partType, ArrayList<String> imageText) {
        this.context = context;
        this.name = name;
        this.compPartType = partType;
        this.imageText = imageText;
    }

    @NonNull
    @Override
    public SearchPartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.part_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPartAdapter.MyViewHolder holder, final int position) {
        holder.pName.setText(name.get(position));
        Picasso.with(context).load(imageText.get(position))
                .into(holder.pImage);

        holder.myLayout.setOnClickListener(view -> {
            Intent intent = ViewPartActivity.intentFactory(context);
            intent.putExtra("NAME",name.get(position));
            intent.putExtra("PART",compPartType.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pName;
        ImageView pImage;
        ConstraintLayout myLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.name_recy);
            pImage = itemView.findViewById(R.id.image_recy);
            myLayout = itemView.findViewById(R.id.users_list);
        }
    }
}
