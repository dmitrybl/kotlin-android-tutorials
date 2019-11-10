package com.example.daggertest.car;

import android.util.Log;

import javax.inject.Inject;

public class Remote {

    @Inject
    public Remote() {

    }

    public void setListener(Car car) {
        Log.d("myLogs", "Remote connected!");
    }
}
