package com.fomolucky.main.viewModel;

import androidx.lifecycle.MutableLiveData;

import com.fomolucky.base.BaseViewModel;
import com.fomolucky.main.R;
import com.fomolucky.main.model.Tab;

import java.util.ArrayList;

public class MainViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<Tab>> tabLiveData = new MutableLiveData<>();

    public MainViewModel() {
        ArrayList<Tab> tabList = new ArrayList<>();
        tabList.add(new Tab("首页", "home", R.mipmap.ic_home_selected, R.mipmap.ic_home_unselected, true));
        tabList.add(new Tab("我的", "mine", R.mipmap.ic_mine_selected, R.mipmap.ic_mine_unselected, false));
        tabLiveData.setValue(tabList);
    }

    public void changeSelected(int position) {
        ArrayList<Tab> list = tabLiveData.getValue();
        assert list != null;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++)
                list.get(i).setSelected(i == position);
            tabLiveData.setValue(list);
        }
    }

    public MutableLiveData<ArrayList<Tab>> getTabLiveData() {
        return tabLiveData;
    }


}
