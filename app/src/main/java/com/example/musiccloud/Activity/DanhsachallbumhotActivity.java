package com.example.musiccloud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.musiccloud.R;

public class DanhsachallbumhotActivity extends AppCompatActivity {
    RecyclerView recyclerViewAllAlbum;
    Toolbar toolbaralbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachallbumhot);
        init();
    }

    private void init() {
        toolbaralbum = findViewById(R.id.toolbaralbum);
        recyclerViewAllAlbum = findViewById(R.id.recycleviewAllalbum);
        setSupportActionBar(toolbaralbum);
        getSupportActionBar().setTitle("Tất cả album");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbaralbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}