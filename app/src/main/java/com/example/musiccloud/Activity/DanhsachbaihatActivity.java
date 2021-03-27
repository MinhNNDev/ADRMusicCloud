package com.example.musiccloud.Activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccloud.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DanhsachbaihatActivity extends Fragment {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhsachbaihat;
    FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anhxa();
    }

    private void anhxa() {
        coordinatorLayout = coordinatorLayout.findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = collapsingToolbarLayout.findViewById(R.id.collapstringtoolbar);
        toolbar = toolbar.findViewById(R.id.toolbardanhsach);
        recyclerViewDanhsachbaihat = recyclerViewDanhsachbaihat.findViewById(R.id.recycleviewdanhsachbaihat);
        floatingActionButton = floatingActionButton.findViewById(R.id.floatingactionbutton);
    }
}
