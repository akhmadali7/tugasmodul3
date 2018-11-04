package com.example.user.modul2kel34.ui.detailCar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.user.modul2kel34.R;
import com.example.user.modul2kel34.data.model.DataCar;
import com.example.user.modul2kel34.ui.home.HomeActivity;
import com.example.user.modul2kel34.utility.Constant;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private DataCar car;
    private DetailPresenter detailPresenter;
    private EditText tvNama;
    private EditText tvMerk;
    private EditText tvModel;
    private EditText tvTahun;
    private Button btnPut;
    private Button btnDel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initIntentData();
        initPresenter();
        initData();
        //deleteCar();
        putCar();
    }

    /*private void deleteCar() {
        btnDel.setOnClickListener(v -> {
            detailPresenter.deleteCar();
        });
    }*/

    private void putCar() {
        btnPut.setOnClickListener(v -> {
            detailPresenter.putCar();
        });
    }

    @Override
    public String getName() {
        return tvNama.getText().toString();
    }

    @Override
    public String getMerk() {
        return tvMerk.getText().toString();
    }

    @Override
    public String getModel() {
        return tvModel.getText().toString();
    }

    @Override
    public String getYear() {
        return tvTahun.getText().toString();
    }

    private void initView() {
        tvNama = findViewById(R.id.nameCar);
        tvMerk = findViewById(R.id.merkCar);
        tvModel = findViewById(R.id.modelCar);
        tvTahun = findViewById(R.id.yearCar);
        btnPut = findViewById(R.id.btnPut);
        btnDel = findViewById(R.id.btnDel);
    }

    private void initData() {
        detailPresenter.getCarById(car);
    }

    private void initPresenter() {
        detailPresenter = new DetailPresenter(this);
    }

    private void initIntentData() {
        car = getIntent().getParcelableExtra(Constant.Extra.DATA);
        if (car == null) finish();
    }

    @Override
    public void showErrorCarById(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessCarById(List<DataCar> car) {
        tvNama.setText(car.get(0).getName());
        tvMerk.setText(car.get(0).getMerk());
        tvModel.setText(car.get(0).getModel());
        tvTahun.setText(car.get(0).getYear());
    }
    @Override
    public void successPutCar() {
        Toast.makeText(this, "Berhasil Merubah Mobil", Toast.LENGTH_SHORT).show();
        Intent home = new Intent(DetailActivity.this, HomeActivity.class);
        startActivity(home);
        finish();
    }

    @Override
    public void failedPutCar() {
        Toast.makeText(this, "Gagal Merubah Mobil", Toast.LENGTH_SHORT).show();
    }
}
