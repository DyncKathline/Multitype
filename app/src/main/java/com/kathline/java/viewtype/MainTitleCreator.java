package com.kathline.java.viewtype;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kathline.java.R;
import com.kathline.java.datatype.Title;
import com.kathline.multitype.ViewTypeCreator;

public class MainTitleCreator extends ViewTypeCreator<Title, MainTitleCreator.Holder> {

    @Override
    protected Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new Holder(inflater.inflate(R.layout.view_type_main_title, parent, false));
    }

    @Override
    protected void onBindViewHolder(Holder holder, int position, Title data) {
        holder.title.setText(data.getMainTitle());
    }

    @Override
    public boolean match(Title data) {
        return !TextUtils.isEmpty(data.getMainTitle()) && TextUtils.isEmpty(data.getSubTitle());
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.main_title);
        }
    }

}