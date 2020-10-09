package com.fomolucky.main.viewModel;

import android.text.TextUtils;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fomolucky.main.model.HomeInfoModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeInfoViewModel extends ViewModel {
    public ObservableField<String> num = new ObservableField<>();
    public ObservableField<HomeInfoModel.DataBean> data = new ObservableField<>();
    private MutableLiveData<HomeInfoModel.DataBean> dataBeanLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<HomeInfoModel.RoundInfoItem>> roundInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<HomeInfoModel.OptionsItem>> optionsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<HomeInfoModel.BtnInfoItem>> btnInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<HomeInfoModel.TeamItem>> teamLiveData = new MutableLiveData<>();

    private boolean isFirst = true;

    public void requestHomeInfo() {
        Gson gson = new Gson();
        String msg;
        if (isFirst) {
            msg = "{\n" +
                    "\"code\":\"1\",\n" +
                    "\"msg\":\"ok\",\n" +
                    "\"data\" :{\n" +
                    "    \"jackpot\":{\n" +
                    "        \"name\":\"本轮累计奖金\",\n" +
                    "        \"totalMoney\":\"6666\",\n" +
                    "        \"countTime\":\"23:92:10\"\n" +
                    "    },\n" +
                    "    \"myThisRoundInfo\":[\n" +
                    "        {\n" +
                    "            \"name\":\"本轮拥有的钥匙个数\",\n" +
                    "            \"money\":\"0.0000\",\n" +
                    "            \"tips\":\"总钥匙数量：1000000个\"\n" +
                    "        },\n" +

                    "        {\n" +
                    "            \"name\":\"本轮赢得的EJF\",\n" +
                    "            \"money\":\"0.00000\",\n" +
                    "            \"tips\":\"≈9 USDT\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"buyInfo\":{\n" +
                    "        \"name\":\"立即购买\",\n" +
                    "        \"remark\":\"0.8%的机会在下次购买时获得0.59EJF空投\",\n" +
                    "        \"exchangerate\":\"@ 0.00222288EJF\",\n" +
                    "        \"options\":[\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"1\",\n" +
                    "                \"symbol\":\"keys\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"2\",\n" +
                    "                \"symbol\":\"keys\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"5\",\n" +
                    "                \"symbol\":\"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"10\",\n" +
                    "                \"symbol\":\"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"15\",\n" +
                    "                \"symbol\":\"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"btnInfo\":[\n" +
                    "            {\n" +
                    "                \"name\":\"使用EJF购买\",\n" +
                    "                \"btnType\":\"1\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"buyTeams\":{\n" +
                    "        \"name\":\"选择一个团队\",\n" +
                    "        \"teams\":[\n" +
                    "            {\n" +
                    "                \"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1787277979,212555649&amp;fm=26&amp;gp=0.jpg\",\n" +
                    "                \"name\":\"Snek(蛇)\",\n" +
                    "                \"tips\":\"最多分红\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1757507349,158633179&amp;fm=26&amp;gp=0.jpg\",\n" +
                    "                \"name\":\"Bull(公牛)\",\n" +
                    "                \"tips\":\"平均分配\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"imgUrl\":\"https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1598510113887&amp;di=9385179c110171508caf2f09a67436dc&amp;imgtype=0&amp;src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F90%2F86%2F5aba24472889d_610.jpg\",\n" +
                    "                \"name\":\"Whale(鲸鱼)\",\n" +
                    "                \"tips\":\"奖池最大\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}\n" +
                    "}";
        } else {
            msg = "{\n" +
                    "\"code\":\"1\",\n" +
                    "\"msg\":\"ok\",\n" +
                    "\"data\" :{\n" +
                    "    \"jackpot\":{\n" +
                    "        \"name\":\"本轮累计奖金\",\n" +
                    "        \"totalMoney\":\"4089.0981\",\n" +
                    "        \"countTime\":\"23:92:10\"\n" +
                    "    },\n" +
                    "    \"myThisRoundInfo\":[\n" +
                    "        {\n" +
                    "            \"name\":\"本轮拥有的钥匙个数\",\n" +
                    "            \"money\":\"0.0000\",\n" +
                    "            \"tips\":\"总钥匙数量：1000000个\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\":\"本轮拥有的钥匙个数\",\n" +
                    "            \"money\":\"0.0000\",\n" +
                    "            \"tips\":\"总钥匙数量：1000000个\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\":\"本轮赢得的EJF\",\n" +
                    "            \"money\":\"0.00000\",\n" +
                    "            \"tips\":\"≈9 USDT\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"buyInfo\":{\n" +
                    "        \"name\":\"立即购买\",\n" +
                    "        \"remark\":\"0.8%的机会在下次购买时获得0.59EJF空投\",\n" +
                    "        \"exchangerate\":\"@ 0.00222288EJF\",\n" +
                    "        \"options\":[\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"1\",\n" +
                    "                \"symbol\":\"keys\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"2\",\n" +
                    "                \"symbol\":\"keys\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"5\",\n" +
                    "                \"symbol\":\"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"10\",\n" +
                    "                \"symbol\":\"\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"name\":\"+\",\n" +
                    "                \"num\":\"15\",\n" +
                    "                \"symbol\":\"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"btnInfo\":[\n" +
                    "            {\n" +
                    "                \"name\":\"使用EJF购买\",\n" +
                    "                \"btnType\":\"1\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"buyTeams\":{\n" +
                    "        \"name\":\"选择一个团队\",\n" +
                    "        \"teams\":[\n" +
                    "            {\n" +
                    "                \"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1787277979,212555649&amp;fm=26&amp;gp=0.jpg\",\n" +
                    "                \"name\":\"Snek(蛇)\",\n" +
                    "                \"tips\":\"最多分红\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1757507349,158633179&amp;fm=26&amp;gp=0.jpg\",\n" +
                    "                \"name\":\"Bull(公牛)\",\n" +
                    "                \"tips\":\"平均分配\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"imgUrl\":\"https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1598510113887&amp;di=9385179c110171508caf2f09a67436dc&amp;imgtype=0&amp;src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F90%2F86%2F5aba24472889d_610.jpg\",\n" +
                    "                \"name\":\"Whale(鲸鱼)\",\n" +
                    "                \"tips\":\"奖池最大\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}\n" +
                    "}";
        }
        HomeInfoModel model = gson.fromJson(msg, HomeInfoModel.class);
        dataBeanLiveData.setValue(model.getData());
        roundInfoLiveData.setValue(model.getData().getMyThisRoundInfo());
        optionsLiveData.setValue(model.getData().getBuyInfo().getOptions());
        btnInfoLiveData.setValue(model.getData().getBuyInfo().getBtnInfo());
        teamLiveData.setValue(model.getData().getBuyTeams().getTeams());
        isFirst = false;
    }

    public MutableLiveData<HomeInfoModel.DataBean> getDataBeanLiveData() {
        return dataBeanLiveData;
    }

    public void setDataBean(HomeInfoModel.DataBean data) {
        this.data.set(data);
    }

    public void setNum(String number) {
        if (number != null && !TextUtils.isEmpty(number)) {
            String orgStr = this.num.get();
            if (orgStr != null && !orgStr.isEmpty())
                this.num.set(String.valueOf(Integer.parseInt(this.num.get()) + Integer.parseInt(number)));
            else this.num.set(number);
        } else this.num.set("");
    }

    public MutableLiveData<ArrayList<HomeInfoModel.RoundInfoItem>> getRoundInfoLiveData() {
        return roundInfoLiveData;
    }

    public MutableLiveData<ArrayList<HomeInfoModel.OptionsItem>> getOptionsLiveData() {
        return optionsLiveData;
    }

    public MutableLiveData<ArrayList<HomeInfoModel.BtnInfoItem>> getBtnInfoLiveData() {
        return btnInfoLiveData;
    }

    public MutableLiveData<ArrayList<HomeInfoModel.TeamItem>> getTeamLiveData() {
        return teamLiveData;
    }
}
