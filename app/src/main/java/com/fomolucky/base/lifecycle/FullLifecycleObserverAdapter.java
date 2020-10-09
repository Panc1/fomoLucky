package com.fomolucky.base.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


public class FullLifecycleObserverAdapter implements LifecycleObserver {

    private FullLifecycleObeserver obeserver;
    private LifecycleOwner owner;

    public FullLifecycleObserverAdapter(FullLifecycleObeserver obeserver, LifecycleOwner owner) {
        this.obeserver = obeserver;
        this.owner = owner;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        obeserver.onCreate(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        obeserver.onStart(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        obeserver.onResume(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        obeserver.onPause(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        obeserver.onStop(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        obeserver.onDestroy(owner);
    }


}
