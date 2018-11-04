package com.example.user.modul2kel34.ui.home;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.modul2kel34.LoginActivity;
import com.example.user.modul2kel34.R;
import com.example.user.modul2kel34.adapter.car.CarAdapter;
import com.example.user.modul2kel34.adapter.car.CarListerner;
import com.example.user.modul2kel34.data.model.DataCar;
import com.example.user.modul2kel34.ui.addCar.AddActivity;
import com.example.user.modul2kel34.ui.detailCar.DetailActivity;
import com.example.user.modul2kel34.utility.Constant;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView, CarListerner {

    private HomePresenter homePresenter;
    private CarAdapter carAdapter;
    FloatingActionButton fabHome;
    RecyclerView rvHome;
    SwipeRefreshLayout srlHome;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initPresenter();
        initView();
        initDataPresenter();
        logout();
        addCar();
        refresh();
    }

    private void initView() {
        fabHome = findViewById(R.id.fabHome);
        rvHome = findViewById(R.id.rvHome);
        srlHome = findViewById(R.id.srlHome);
        exit = findViewById(R.id.exit);
    }

    private void initPresenter() {
        homePresenter = new HomePresenter(this);
    }

    private void initDataPresenter() {
        homePresenter.getAllCar();
    }

    private void addCar() {
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this ,AddActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void refresh(){
        srlHome.setOnRefreshListener(() -> initDataPresenter());
    }

    @Override
    public void successShowCar(List<DataCar> dataCars) {
        if (srlHome.isRefreshing()){
            srlHome.setRefreshing(false);
        }
        carAdapter = new CarAdapter(dataCars);
        carAdapter.setAdapterListener(this);
        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setAdapter(carAdapter);
    }

    @Override
    public void failedShowCar(String message) {
        Toast.makeText(this, "Maaf terjadi kesalahan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCarClick(DataCar dataCar) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constant.Extra.DATA, dataCar);
        startActivity(intent);
    }
    private void logout() {
        exit.setOnClickListener(view -> showAlertDialog());
    }

    private void deletePreference() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        preferences.edit().remove("username").commit();
        preferences.edit().remove("password").commit();
    }
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Apa kalian ingin Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deletePreference();
                        Intent login = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
