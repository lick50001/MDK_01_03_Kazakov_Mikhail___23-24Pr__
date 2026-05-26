package com.example.network.datas.basket.network.src.main.java.com.example.network.datas.basket;

import com.example.network.datas.basket.network.src.main.java.com.example.network.domains.apis.MyAsyncTask;
import com.example.network.datas.basket.network.src.main.java.com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.datas.basket.network.src.main.java.com.example.network.domains.common.Settings;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Connection;

public class ProductGet extends MyAsyncTask {
    public ProductGet(MyResponseCallback callback){
        super(callback);
    }

    @Override
    protected String doInBackground(Void... voids){
        try {
            Connection.Response response = Jsoup.connect(Settings.URL + "api/basket/get")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(org.jsoup.Connection.Method.GET)
                    .header("Content-type", "application/json")
                    .execute();

            return response.statusCode() == 200 ? response.body() : "Error: " + response.body();
        }catch (IOException e){
            return "Error: " + e.getMessage();
        }
    }
}
