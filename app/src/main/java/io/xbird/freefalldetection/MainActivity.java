package io.xbird.freefalldetection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.xbird.freefalldetection.Service.FallDetectionService;
import io.xbird.freefalldetection.database.FallEntity;
import io.xbird.freefalldetection.ui.FallAdapter;
import io.xbird.freefalldetection.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<FallEntity> fallEntities = new ArrayList<>();
    private MainViewModel mainViewModel;
    private FallAdapter mAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Service Called");
        Intent serviceIntent = new Intent(this, FallDetectionService.class);
        startService(serviceIntent);

        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
    }

    private void initViewModel() {
        final Observer<List<FallEntity>> fallsObserver = new Observer<List<FallEntity>>() {
            @Override
            public void onChanged(@Nullable List<FallEntity> fallEntities) {
                MainActivity.this.fallEntities.clear();
                MainActivity.this.fallEntities.addAll(fallEntities);
                if (mAdapter == null) {
                    mAdapter = new FallAdapter(fallEntities, MainActivity.this);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.mFalls.observe(this, fallsObserver);

    }


}


