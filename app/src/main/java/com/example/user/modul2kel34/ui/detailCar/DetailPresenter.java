package com.example.user.modul2kel34.ui.detailCar;

import android.util.Log;

import com.example.user.modul2kel34.data.model.DataCar;
import com.example.user.modul2kel34.data.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

//import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {

    private final DetailView detailView;

    public DetailPresenter(DetailView detailView) {
        this.detailView = detailView;
    }

    public void getCarById(DataCar car) {
        RetrofitClient.getInstance()
                .getApi()
                .GetCarById(car.getId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            JsonObject body = response.body();
                            JsonArray array = body.get("result").getAsJsonArray();
                            Type type = new TypeToken<List<DataCar>>(){}.getType();
                            List<DataCar> dataCars = new Gson().fromJson(array, type);
                            detailView.showSuccessCarById(dataCars);
                        } else {
                            detailView.showErrorCarById("Error gan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("DATA", t.getMessage());
                        detailView.showErrorCarById(t.getMessage());
                    }
                });

    }
    public void putCar() {
        final String tag = "Put-putCar";
        String name = detailView.getName();
        String merk = detailView.getMerk();
        String model = detailView.getModel();
        String year = detailView.getYear();

        RetrofitClient.getInstance()
                .getApi()
                .putCar(name, merk, model, year)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            detailView.successPutCar();
                            Log.e(tag, response.body().toString());
                        } else {
                            detailView.failedPutCar();
                            Log.e(tag, response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        detailView.failedPutCar();
                        Log.e(tag, t.getMessage().toString());
                    }
                });
    }
    /*public void deleteCar(DataCar car){
        RetrofitClient.getInstance()
                .getApi()
                .deleteCar(car.getId())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            JsonObject body = response.body();
                            JsonArray array = body.get("result").getAsJsonArray();
                            Type type = new TypeToken<List<DataCar>>(){}.getType();
                            List<DataCar> dataCars = new Gson().fromJson(array, type);
                            detailView.showSuccessCarById(dataCars);
                        } else {
                            detailView.showErrorCarById("Error gan");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("DATA", t.getMessage());
                        detailView.showErrorCarById(t.getMessage());
                    }
                });
    }*/

}

