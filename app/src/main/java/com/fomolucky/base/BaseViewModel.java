package com.fomolucky.base;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected boolean isCleared = false;


    @Override
    protected void onCleared() {
        super.onCleared();
        isCleared = true;
    }



}
