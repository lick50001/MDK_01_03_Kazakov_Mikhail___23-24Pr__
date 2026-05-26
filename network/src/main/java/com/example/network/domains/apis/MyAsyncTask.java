package com.example.network.domains.apis;

import com.example.network.domains.callbacks.MyResponseCallback;
import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    protected MyResponseCallback callback;

    public  MyAsyncTask(MyResponseCallback callback){
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids){
        return "";
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        if (callback != null) {
            if (result != null && !result.startsWith("Error"))
                callback.onCompile(result);
            else
                callback.onError(result);
        }
    }
}
