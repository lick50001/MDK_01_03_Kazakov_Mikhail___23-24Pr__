package com.example.network.datas.users;

import com.example.network.domains.apis.MyAsyncTask;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.common.Settings;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import java.io.IOException;

public class UserGet extends MyAsyncTask {
    String token;

    public UserGet(String token, MyResponseCallback callback) {
        super(callback);
        this.token = token;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Connection connection = Jsoup.connect(Settings.URL + "/api/user/get") // Или другой эндпоинт
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.GET)
                    .header("token", token);

            Connection.Response response = connection.execute();
            return response.statusCode() == 200 ? response.body() : "Error: " + response.body();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}