package com.example.navigation3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class CarsSliderAdapter extends RecyclerView.Adapter<CarsSliderAdapter.CarsSliderViewHolder> {
    private List<CarsSliderItem> items;
    private ViewPager2 viewPager;

    public CarsSliderAdapter(List<CarsSliderItem> items, ViewPager2 viewPager) {
        this.items = items;
        this.viewPager = viewPager;
    }

    public List<CarsSliderItem> getItems() {
        return items;
    }

    @NonNull
    @Override
    public CarsSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarsSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_car_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CarsSliderViewHolder holder, int position) {
        holder.setImage(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CarsSliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        CarsSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageCarsSlide);
        }

        void setImage(CarsSliderItem item) {
            if (item != null) {
                imageView.setImageBitmap(item.getImage());
            } else {
                imageView.setImageResource(android.R.color.transparent);
            }
        }
    }

    public ViewPager2 getViewPager() {
        return viewPager;
    }
}
