package com.example.daggertest.dagger;

import com.example.daggertest.car.Driver;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class DriverModule {

    private int age;
    private String sex;

    @Inject
    public DriverModule(int age, String sex) {
        this.age = age;
        this.sex = sex;
    }

    @Provides
    int providerAge() {
        return age;
    }

    @Provides
    String providerSex() {
        return sex;
    }

    @Provides
    Driver provideDriver(Driver driver) {
        return driver;
    }
}
