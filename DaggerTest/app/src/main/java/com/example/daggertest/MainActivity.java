package com.example.daggertest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daggertest.car.Car;
import com.example.daggertest.dagger.CarComponent;
import com.example.daggertest.dagger.DaggerCarComponent;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject Car car1, car2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CarComponent component = DaggerCarComponent.builder()
                .horsePower(100)
                .engineCapacity(4000)
                .build();
        component.inject(this);
        car1.drive();
        car2.drive();
    }
}
