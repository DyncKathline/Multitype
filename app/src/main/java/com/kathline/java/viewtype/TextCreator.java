package com.kathline.java.viewtype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kathline.java.R;
import com.kathline.multitype.ViewTypeCreator;

public class TextCreator extends ViewTypeCreator<String, TextCreator.Holder> {

    @Override
    protected Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new Holder(inflater.inflate(R.layout.view_type_text, parent, false));
    }

    @Override
    protected void onBindViewHolder(Holder holder, int position, String data) {
        holder.text.setText(data);
    }

    @Override
    public boolean match(String data) {
        return false;
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView text;

        public Holder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }

}