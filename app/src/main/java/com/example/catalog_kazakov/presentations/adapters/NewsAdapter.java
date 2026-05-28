package com.example.catalog_kazakov.presentations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catalog_kazakov.R;
import com.example.network.domains.models.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final Context context;
    private final List<News> newsList;
    private static final String BASE_URL = "http://10.111.20.114:5000";

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.news_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);

        // Устанавливаем название
        String name = (news.product != null && news.product.name != null)
                ? news.product.name
                : "Без названия";
        holder.tvName.setText(name);

        // Устанавливаем цену
        holder.tvPrice.setText(news.price + "₽");

        // Загружаем изображение
        if (news.product != null && news.product.img != null && !news.product.img.isEmpty()) {
            String imageUrl = BASE_URL + "/img/" + news.product.img;
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_vaccine) // Пока загружается
                    .error(R.drawable.ic_vaccine)       // Если ошибка
                    .into(holder.ivImage);
        } else {
            holder.ivImage.setImageResource(R.drawable.ic_vaccine);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }

    public void updateData(List<News> newData) {
        this.newsList.clear();
        this.newsList.addAll(newData);
        notifyDataSetChanged();
    }
}