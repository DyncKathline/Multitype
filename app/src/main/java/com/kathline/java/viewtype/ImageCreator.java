package com.kathline.java.viewtype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kathline.java.R;
import com.kathline.multitype.ViewTypeCreator;

public class ImageCreator extends ViewTypeCreator<Integer, ImageCreator.Holder> {

    @Override
    protected Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new Holder(inflater.inflate(R.layout.view_type_image, parent, false));
    }

    @Override
    protected void onBindViewHolder(Holder holder, int position, Integer data) {
        holder.image.setImageResource(data);
    }

    @Override
    public boolean match(Integer data) {
        return false;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        ImageView image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }

}