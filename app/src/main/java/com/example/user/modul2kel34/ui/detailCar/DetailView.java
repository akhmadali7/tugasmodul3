package com.example.user.modul2kel34.ui.detailCar;

import com.example.user.modul2kel34.data.model.DataCar;

import java.util.List;

public interface DetailView {
    void showErrorCarById(String message);
    void showSuccessCarById(List<DataCar> car);
    void successPutCar();
    void failedPutCar();
    String getName();
    String getMerk();
    String getModel();
    String getYear();

}
