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

public class SubTitleCreator extends ViewTypeCreator<Title, SubTitleCreator.Holder> {

    @Override
    protected Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new Holder(inflater.inflate(R.layout.view_type_sub_title, parent, false));
    }

    @Override
    protected void onBindViewHolder(Holder holder, int position, Title data) {
        holder.title.setText(data.getSubTitle());
    }

    @Override
    public boolean match(Title data) {
        return !TextUtils.isEmpty(data.getSubTitle()) && TextUtils.isEmpty(data.getMainTitle());
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.sub_title);
        }
    }

}