package com.example.daggertest.car;

import android.util.Log;

import javax.inject.Inject;

public class Car {

    private Driver driver;
    private Engine engine;
    private Wheels wheels;

    @Inject
    public Car(Driver driver, Engine engine, Wheels wheels) {
        this.driver = driver;
        this.engine = engine;
        this.wheels = wheels;
    }

    public void drive() {
        engine.start();
        Log.d("myLogs", driver + " drives " + this);
    }

    // С помощью аннотации Inject можно указать, что method будет вызываться
    // после вызова конструктора
    @Inject
    public void enableRemote(Remote remote) {
        remote.setListener(this);
    }
}
