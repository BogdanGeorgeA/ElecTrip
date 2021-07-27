package com.example.navigation3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarsFragment extends Fragment {
    private View view;
    private ViewPager2 viewPager2;
    private TextView textViewCarModel;
    private TextView textViewCarManufacturer;
    private TextView textViewNumber;
    private List<CarsSliderItem> items;
    private List<Car2> car2s;
    private CarsSliderAdapter carsSliderAdapter;

    public CarsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        viewPager2 = view.findViewById(R.id.viewPagerCars);
        textViewCarModel = view.findViewById(R.id.textViewCarModel);
        textViewCarManufacturer = view.findViewById(R.id.textViewCarManufacturer);
        textViewNumber = view.findViewById(R.id.textViewNumber);
    }

    public void listCars(List<Car2> car2s) {
        this.car2s = car2s;
        items = new ArrayList<>();

        for (Car2 car2 : car2s) {
            items.add(new CarsSliderItem(car2.getImagesData().getImageThumbnail().getUrl(), getContext()));
        }

        carsSliderAdapter = new CarsSliderAdapter(items, viewPager2);

        if (viewPager2 != null) {
            viewPager2.setAdapter(carsSliderAdapter);
            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(3);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(40));
            compositePageTransformer.addTransformer((page, position1) -> {
                float r = 1 - Math.abs(position1);
                page.setScaleY(0.85f + r * 0.15f);
            });

            viewPager2.setPageTransformer(compositePageTransformer);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if (car2s.size() > 0) {
                        textViewCarManufacturer.setText(car2s.get(position).getMake());
                        textViewCarModel.setText(car2s.get(position).toString());
                        textViewNumber.setText((position + 1) + "/" + car2s.size());
                    }
                }
            });
        }
    }

    public String getSelected() {
        if (items.isEmpty()) return null;
        return car2s.get(viewPager2.getCurrentItem()).getId();
    }

    public ViewPager2 getViewPager2() {
        return viewPager2;
    }

    public CarsSliderAdapter getCarsSliderAdapter() {
        return carsSliderAdapter;
    }

    public List<Car2> getCar2s() {
        return car2s;
    }
}
