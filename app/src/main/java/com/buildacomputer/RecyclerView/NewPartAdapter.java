package com.buildacomputer.RecyclerView;

// This adapter is used in the new build page.
// This adapter is used while in the "main hub" of Create Build.
// The selection of parts in the build point to their respective selection of part types.

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

import com.buildacomputer.BuildPartsRecyclerActivity;
import com.buildacomputer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewPartAdapter extends RecyclerView.Adapter<NewPartAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> name;
    ArrayList<String> imageText;
    ArrayList<String> notSetYet;
    ArrayList<Integer> partID;
    ArrayList<Integer> compPartType;

    public NewPartAdapter(Context context,
                          ArrayList<String> name,
                          ArrayList<Integer> partID,
                          ArrayList<Integer> partType,
                          ArrayList<String> imageText,
                          ArrayList<String> notSetYet) {
        this.context = context;
        this.name = name;
        this.partID = partID;
        this.compPartType = partType;
        this.imageText = imageText;
        this.notSetYet = notSetYet;
    }

    @NonNull
    @Override
    public NewPartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.part_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewPartAdapter.MyViewHolder holder, final int position) {
        if(name.get(position).equals("none")) {
            holder.pNotSetYet.setText(notSetYet.get(position));
        }
        else {
            holder.pName.setText(name.get(position));
            Picasso.with(context).load(imageText.get(position))
                    .into(holder.pImage);
        }

        holder.myLayout.setOnClickListener(view -> {
            Intent intent = BuildPartsRecyclerActivity.intentFactory(context);
            intent.putExtra("PART_TYPE", compPartType.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pName;
        TextView pNotSetYet;
        ImageView pImage;
        ConstraintLayout myLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.name_recy);
            pNotSetYet = itemView.findViewById(R.id.build_recy);
            pImage = itemView.findViewById(R.id.image_recy);
            myLayout = itemView.findViewById(R.id.users_list);
        }
    }
}
