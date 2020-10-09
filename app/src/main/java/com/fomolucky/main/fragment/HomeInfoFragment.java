package com.fomolucky.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fomolucky.main.R;
import com.fomolucky.main.adapter.BtnInfoAdapter;
import com.fomolucky.main.adapter.OptionsAdapter;
import com.fomolucky.main.adapter.RoundInfoAdapter;
import com.fomolucky.main.adapter.TeamAdapter;
import com.fomolucky.main.databinding.FragmentHomeInfoBinding;
import com.fomolucky.main.model.HomeInfoModel;
import com.fomolucky.main.viewModel.HomeInfoViewModel;

public class HomeInfoFragment extends Fragment {

    //data
    private HomeInfoViewModel viewModel;
    private FragmentHomeInfoBinding binding;

    //adapter
    private RoundInfoAdapter roundInfoAdapter;
    private OptionsAdapter optionsAdapter;
    private BtnInfoAdapter btnInfoAdapter;
    private TeamAdapter teamAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeInfoViewModel.class);
        binding.setHomeInfo(viewModel);
        viewModel.getDataBeanLiveData().observe(this, dataBean -> viewModel.setDataBean(dataBean));
        initRecyclerView();
        viewModel.requestHomeInfo();
        super.onActivityCreated(savedInstanceState);
    }

    private void initRecyclerView() {
        roundInfoAdapter = new RoundInfoAdapter();
        optionsAdapter = new OptionsAdapter(number -> viewModel.setNum(number));
        btnInfoAdapter = new BtnInfoAdapter();
        teamAdapter = new TeamAdapter();
        binding.recyclerViewRoundInfo.setAdapter(roundInfoAdapter);
        binding.recyclerViewOptions.setAdapter(optionsAdapter);
        binding.recyclerViewBtnInfo.setAdapter(btnInfoAdapter);
        binding.recyclerViewTeam.setAdapter(teamAdapter);
        viewModel.getRoundInfoLiveData().observe(this, list -> roundInfoAdapter.setList(list));
        viewModel.getOptionsLiveData().observe(this, list -> optionsAdapter.setList(list));
        viewModel.getBtnInfoLiveData().observe(this, list -> {
            GridLayoutManager layoutManager = (GridLayoutManager) binding.recyclerViewBtnInfo.getLayoutManager();
            if (layoutManager != null) {
                layoutManager.setSpanCount(list.size());
                binding.recyclerViewBtnInfo.setLayoutManager(layoutManager);
            } else
                binding.recyclerViewBtnInfo.setLayoutManager(new GridLayoutManager(getActivity(), list.size()));
            btnInfoAdapter.setList(list);
        });
        viewModel.getTeamLiveData().observe(this, list -> {
            GridLayoutManager layoutManager = (GridLayoutManager) binding.recyclerViewTeam.getLayoutManager();
            if (layoutManager != null) {
                layoutManager.setSpanCount(list.size());
                binding.recyclerViewTeam.setLayoutManager(layoutManager);
            } else
                binding.recyclerViewTeam.setLayoutManager(new GridLayoutManager(getActivity(), list.size()));
            teamAdapter.setList(list);
        });
    }

}
