package com.kathline.java;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kathline.java.datatype.Title;
import com.kathline.java.viewtype.ImageCreator;
import com.kathline.java.viewtype.MainTitleCreator;
import com.kathline.java.viewtype.SubTitleCreator;
import com.kathline.java.viewtype.TextCreator;
import com.kathline.multitype.MultiTypeAdapter;
import com.kathline.multitype.SimpleMultiTypeAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        SimpleMultiTypeAdapter adapter = new SimpleMultiTypeAdapter();
        // image type
        adapter.registerCreator(new ImageCreator());
        // text type
        adapter.registerCreator(new TextCreator());
        // the same bean but different view type
        adapter.registerCreator(new MainTitleCreator(), new SubTitleCreator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(0, 8, 0, 8);
            }
        });
        recyclerView.setAdapter(adapter);
        fillData(adapter);
    }

    private void fillData(SimpleMultiTypeAdapter adapter) {
        for (int i = 0; i < 10; i++) {
            adapter.add(R.drawable.test);
            adapter.add("I am string");
            adapter.add(new Title("I am MainTitle", ""));
            adapter.add(new Title("", "I am SubTitle"));
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
}
