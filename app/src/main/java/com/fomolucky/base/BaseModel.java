package com.fomolucky.base;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

public class BaseModel implements Serializable {

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
