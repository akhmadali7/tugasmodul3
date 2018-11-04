package com.example.user.modul2kel34.ui.home;

import com.example.user.modul2kel34.data.model.DataCar;

import java.util.List;

public interface HomeView {
    void successShowCar(List<DataCar> dataCars);
    void failedShowCar(String message);

}
