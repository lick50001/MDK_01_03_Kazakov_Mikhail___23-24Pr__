package com.example.catalog_kazakov;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalog_kazakov.datas.CategoryContext;
import com.example.catalog_kazakov.presentations.adapters.CategoryAdapter;
import com.example.catalog_kazakov.presentations.adapters.NewsAdapter;
import com.example.catalog_kazakov.presentations.utils.BottomSheetHelper;
import com.example.catalog_kazakov.presentations.utils.ProgressDialogHelper;
import com.example.network.datas.basket.BasketCreate;
import com.example.network.datas.basket.BasketUpdate;
import com.example.network.datas.basket.ProductGet;
import com.example.network.datas.stock.StockGet;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.models.BasketParams;
import com.example.network.domains.models.News;
import com.example.network.domains.models.Product;
import com.example.uicomponents.button.BthCustom;
import com.example.uicomponents.button.BthSmall;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView llCategory;
    RecyclerView llNews;
    LinearLayout llProducts;
    ProgressDialogHelper progressDialogHelper;
    String token = "c230be07-14a7-4ebb-baaa-238776a1c843";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llCategory = findViewById(R.id.llCategory);
        llProducts = findViewById(R.id.llProducts);
        llNews = findViewById(R.id.llnews);

        llNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<News> emptyNews = new ArrayList<>();
        NewsAdapter newsAdapter = new NewsAdapter(this, emptyNews);
        llNews.setAdapter(newsAdapter);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, CategoryContext.allCategory());
        llCategory.setAdapter(categoryAdapter);
        progressDialogHelper = new ProgressDialogHelper(this);
        RequestProductGet();
        RequestNewsGet();
    }

    public void RequestProductGet(){
        progressDialogHelper.progressDialog.show();

        ProductGet RequestProductGet = new ProductGet(new MyResponseCallback() {
            @Override
            public void onCompile(String result) {
                Log.d("PRODUCTS GET", result);
                ArrayList<Product> Products = new GsonBuilder().create().fromJson(
                        result, new TypeToken<ArrayList<Product>>(){}.getType());
                CreateProduct(Products);
            }

            @Override
            public void onError(String error) {
                Log.e("PRODUCTS GET", error);
                progressDialogHelper.progressDialog.hide();
            }
        });
        RequestProductGet.execute();
    }

    public void RequestNewsGet(){
        progressDialogHelper.progressDialog.show();

        StockGet requestNewsGet = new StockGet(new MyResponseCallback() {
            @Override
            public void onCompile(String result) {
                Log.d("NEWS GET", result);
                ArrayList<News> news = new GsonBuilder().create().fromJson(
                        result,
                        new TypeToken<ArrayList<News>>(){}.getType()
                );
                CreateNews(news);
            }

            @Override
            public void onError(String error) {
                Log.e("NEWS GET", error);
                progressDialogHelper.progressDialog.hide();
            }
        });
        requestNewsGet.execute();
    }

    public void BasketCreate(Product product, BthCustom btnAdd) {
        progressDialogHelper.progressDialog.show();

        BasketCreate RequestBasketCreate = new BasketCreate(
                token,
                new BasketParams(product.id, 1),
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("BASKET CREATE", result);
                        ChangeBtnState(btnAdd, true);
                        progressDialogHelper.progressDialog.hide();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("BASKET ERROR", error);
                        progressDialogHelper.progressDialog.hide();
                    }
                }
        );
        RequestBasketCreate.execute();
    }

    public void BasketUpdate(Product product, BthCustom btnAdd){
        progressDialogHelper.progressDialog.show();

        BasketUpdate RequestBasketUpdate = new BasketUpdate(
                token,
                new BasketParams(product.id, 0),
                new MyResponseCallback(){
                    @Override
                    public void onCompile(String result) {
                        Log.d("BASKET UPDATE", result);
                        ChangeBtnState(btnAdd, false);
                        progressDialogHelper.progressDialog.hide();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("BASKET ERROR", error);
                        progressDialogHelper.progressDialog.hide();
                    }
                }
        );
        RequestBasketUpdate.execute();
    }

    public void ChangeBtnState(BthCustom btnAdd, boolean isBasket){
        if (isBasket) btnAdd.init("Убрать", BthCustom.TypeButton.SECONDARY);
        else btnAdd.init("Добавить", BthCustom.TypeButton.PRIMARY);
    }

    public void CreateNews(ArrayList<News> news){
        Log.d("NEWS_DEBUG", "size = " + news.size());
        if (news.size() > 0) {
            Log.d("NEWS_DEBUG", "first name = " + news.get(0).product.name);
        }
        runOnUiThread(() -> {
            NewsAdapter adapter = (NewsAdapter) llNews.getAdapter();
            if (adapter != null) {
                adapter.updateData(news);
            }
            progressDialogHelper.progressDialog.hide();
        });
    }

    public void CreateProduct(ArrayList<Product> products){
        String[] NameCategory = new String[]{"Мужское", "Женское", "Unisex"};

        for (Product product : products){
            View item = LayoutInflater.from(this).inflate(R.layout.item_product, llProducts, false);
            TextView tvName = item.findViewById(R.id.tvName);
            TextView tvCategory = item.findViewById(R.id.tvCategory);
            TextView tvPrice = item.findViewById(R.id.tvPrice);
            BthSmall btnAdd = item.findViewById(R.id.btnAdd);

            tvName.setText(product.name);
            ChangeBtnState(btnAdd, false);

            if (product.gender >= 0 && product.gender <= 2)
                tvCategory.setText(NameCategory[product.gender]);
            else
                tvCategory.setText("Неизвестно");

            tvPrice.setText(product.price + "₽");
            item.setOnClickListener(v -> {
                BottomSheetHelper.Create(this, this, product, btnAdd, progressDialogHelper);
            });

            btnAdd.Bth.setOnClickListener(v -> {
                if (btnAdd.Bth.getText().toString().equals("Добавить"))
                    BasketCreate(product, btnAdd);
                else
                    BasketUpdate(product, btnAdd);
            });

            llProducts.addView(item);
        }
        progressDialogHelper.progressDialog.hide();
    }
}