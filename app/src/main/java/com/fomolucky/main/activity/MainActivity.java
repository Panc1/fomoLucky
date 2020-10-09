package com.fomolucky.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fomolucky.base.utils.StatusbarUtils;
import com.fomolucky.main.R;
import com.fomolucky.main.adapter.TabAdapter;
import com.fomolucky.main.databinding.ActivityMainBinding;
import com.fomolucky.main.fragment.HomeInfoFragment;
import com.fomolucky.main.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    TabAdapter adapter;
    MainViewModel viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusbarUtils.enableTranslucentStatusBar(this, StatusbarUtils.pink);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initFrameLayout();
        initRecyclerView();

    }

    void initFrameLayout() {
        HomeInfoFragment homeFragment = new HomeInfoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment, "home").commit();
    }

    void initRecyclerView() {
        adapter = new TabAdapter(position -> viewModel.changeSelected(position));
        binding.recyclerViewTab.setAdapter(adapter);

        viewModel.getTabLiveData().observe(this, list -> {
            GridLayoutManager layoutManager = (GridLayoutManager) binding.recyclerViewTab.getLayoutManager();
            if (layoutManager != null) {
                layoutManager.setSpanCount(list.size());
                binding.recyclerViewTab.setLayoutManager(layoutManager);
            } else
                binding.recyclerViewTab.setLayoutManager(new GridLayoutManager(this, list.size()));
            adapter.setList(list);
        });
    }


}
