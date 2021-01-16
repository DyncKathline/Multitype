package com.kathline.multitype;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SimpleMultiTypeAdapter extends MultiTypeAdapter {

    private @NonNull List<Object> items;

    public SimpleMultiTypeAdapter() {
        items = new ArrayList<>();
    }

    public Object getData(int position) {
        return items.get(position);
    }

    public void setAll(@NonNull List<Object> list) {
        items = list;
        notifyDataSetChanged();
    }

    public void addAll(@NonNull List<Object> list) {
        int start = items.size();
        items.addAll(list);
        notifyItemRangeChanged(start, items.size());
    }

    public void add(@NonNull Object item) {
        add(items.size(), item);
    }

    public void add(int position, @NonNull Object item) {
        items.add(position, item);
        notifyItemChanged(position);
    }

    @NonNull
    public List<Object> getDatas() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
