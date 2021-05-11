package com.buildacomputer.RecyclerView;

// This adapter is used in the new build page.
// This adapter is used during the selecting of a part type during build creation.
// The selection of parts will point back to the new build page.

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

import com.buildacomputer.NewBuildRecyclerActivity;
import com.buildacomputer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BuildPartAdapter extends RecyclerView.Adapter<BuildPartAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> name;
    ArrayList<String> imageText;
    ArrayList<Integer> partID;
    ArrayList<Integer> compPartType;

    public BuildPartAdapter(Context context, ArrayList<String> name, ArrayList<Integer> partID, ArrayList<Integer> partType, ArrayList<String> imageText) {
        this.context = context;
        this.name = name;
        this.partID = partID;
        this.compPartType = partType;
        this.imageText = imageText;
    }

    @NonNull
    @Override
    public BuildPartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.part_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildPartAdapter.MyViewHolder holder, final int position) {
        holder.pName.setText(name.get(position));
        Picasso.with(context).load(imageText.get(position))
                .into(holder.pImage);

        holder.myLayout.setOnClickListener(view -> {
            Intent intent = NewBuildRecyclerActivity.intentFactory(context);
            intent.putExtra("NAME",name.get(position));
            intent.putExtra("ID",partID.get(position));
            intent.putExtra("TYPE",compPartType.get(position));
            intent.putExtra("PICTURE",imageText.get(position));
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
