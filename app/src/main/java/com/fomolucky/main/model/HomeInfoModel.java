package com.fomolucky.main.model;

import com.fomolucky.base.BaseModel;
import java.io.Serializable;
import java.util.ArrayList;

public class HomeInfoModel extends BaseModel {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public class DataBean implements Serializable {
        private Jackpot jackpot;
        private ArrayList<RoundInfoItem> myThisRoundInfo;
        private BuyInfo buyInfo;
        private BuyTeam buyTeams;

        public Jackpot getJackpot() {
            return jackpot;
        }

        public ArrayList<RoundInfoItem> getMyThisRoundInfo() {
            return myThisRoundInfo;
        }

        public BuyInfo getBuyInfo() {
            return buyInfo;
        }

        public BuyTeam getBuyTeams() {
            return buyTeams;
        }
    }

    public class Jackpot implements Serializable {
        private String name;
        private String totalMoney;
        private String countTime;

        public String getName() {
            return name;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public String getCountTime() {
            return countTime;
        }
    }

    public class RoundInfoItem implements Serializable {
        private String name;
        private String money;
        private String tips;

        public String getName() {
            return name;
        }

        public String getMoney() {
            return money;
        }

        public String getTips() {
            return tips;
        }
    }

    public class BuyInfo implements Serializable {
        private String name;
        private String remark;
        private String exchangerate;
        private ArrayList<OptionsItem> options;
        private ArrayList<BtnInfoItem> btnInfo;

        public String getName() {
            return name;
        }

        public String getRemark() {
            return remark;
        }

        public String getExchangerate() {
            return exchangerate;
        }

        public ArrayList<OptionsItem> getOptions() {
            return options;
        }

        public ArrayList<BtnInfoItem> getBtnInfo() {
            return btnInfo;
        }
    }

    public class OptionsItem implements Serializable {
        private String name;
        private String num;
        private String symbol;

        public String getName() {
            return name;
        }

        public String getNum() {
            return num;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    public class BtnInfoItem implements Serializable {
        private String name;
        private String btnType;

        public String getName() {
            return name;
        }

        public String getBtnType() {
            return btnType;
        }
    }

    public class BuyTeam implements Serializable {
        private String name;
        private ArrayList<TeamItem> teams;

        public String getName() {
            return name;
        }

        public ArrayList<TeamItem> getTeams() {
            return teams;
        }
    }

    public class TeamItem implements Serializable {
        private String imgUrl;
        private String name;
        private String tips;

        public String getImgUrl() {
            return imgUrl;
        }

        public String getName() {
            return name;
        }

        public String getTips() {
            return tips;
        }
    }
}
