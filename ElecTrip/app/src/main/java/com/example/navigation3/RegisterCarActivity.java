package com.example.navigation3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterCarActivity extends AppCompatActivity {
    public static final String EXTRA_USER_TOKEN = "com.example.unitedcharging.USER_TOKEN";
    private String token;
    private List<Car2> cars;
    private List<List<Car2>> models;
    private List<String> manufacturers;
    private JsonApi jsonApi;
    private UrlToBitmap urlToBitmap;
    private int manufacturer;
    private int model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        showProgress(true);
        init();
    }

    private void init() {

        urlToBitmap = new UrlToBitmap();

        cars = new ArrayList<>();
        models = new ArrayList<>();
        manufacturers = new ArrayList<>();

        manufacturers.add("N/A");

        getData();
    }

    private void getData() {
        String connectURL = "https://uber-electric.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        Call<ApiResponse> call = jsonApi.getCarList(Utilizator.getUtilizatorInstance().getAuthentificationKey());

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    myToast.show();
                    showProgress(false);
                    return;
                }

                if (response.body() != null) {
                    List<Car2> carList = response.body().getCarList();
                    assignCars(carList);
                    addManufacturers();
                    showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Something went wrong please contact the staff.", Toast.LENGTH_LONG);
                myToast.show();
                showProgress(false);
            }
        });
    }

    private void addManufacturers() {
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerManufacturers);
        TextView textViewManufacturer = findViewById(R.id.textViewManufacturer);

        List<CarsSliderItem> items = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            items.add(new CarsSliderItem(models.get(i).get(0).getImagesData().getBrandThumbnail().getUrl(), getApplicationContext()));
        }

        viewPager2.setAdapter(new CarsSliderAdapter(items, viewPager2));

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
                manufacturer = position;
                textViewManufacturer.setText(models.get(position).get(0).getMake() + " (" + models.get(position).size() + ")");
                addModels();
            }
        });
    }

    private void addModels() {
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerModels);
        TextView textViewModel = findViewById(R.id.textViewModel);
        TextView textViewInfo = findViewById(R.id.textViewInfo);

        List<CarsSliderItem> items = new ArrayList<>();
        for (int i = 0; i < models.get(manufacturer).size(); i++) {
            items.add(new CarsSliderItem(models.get(manufacturer).get(i).getImagesData().getImageThumbnail().getUrl(), getApplicationContext()));
        }

        viewPager2.setAdapter(new CarsSliderAdapter(items, viewPager2));

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
                model = position;
                textViewModel.setText(models.get(manufacturer).get(position).toString() + " #" + (position + 1));
                textViewInfo.setText(models.get(manufacturer).get(position).getInfo());
            }
        });
    }

    private void assignCars(List<Car2> carList) {
        cars = carList;

        String prev = "n/a";

        for (int i = 0; i < cars.size(); i++) {
            Car2 car = cars.get(i);

            if (!prev.equals(car.getMake())) {
                manufacturers.add(car.getMake());
                models.add(new ArrayList<>());
                models.get(manufacturers.size() - 2).add(car);
            } else {
                models.get(manufacturers.size() - 2).add(car);
            }

            prev = car.getMake();
        }
    }

    public void clickRegisterCar(View view) {
        showProgress(true);

        Car2 car = models.get(manufacturer).get(model);
        String carId = car.getId();

        Call<ApiResponse> call = jsonApi.addCar(Utilizator.getUtilizatorInstance().getAuthentificationKey(), carId);

        Context context = this;

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    myToast.show();
                    showProgress(false);
                    return;
                }

                Intent intent = new Intent(context, ProfileActivity.class);

                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Something went wrong. Please contact the staff.", Toast.LENGTH_LONG);
                myToast.show();
                showProgress(false);
            }
        });
    }

    private void showProgress(boolean visible) {
        final int VISIBILITY1 = (visible == true ? View.VISIBLE : View.INVISIBLE);
        final int VISIBILITY2 = (visible == true ? View.INVISIBLE : View.VISIBLE);

        ProgressBar progressBar = findViewById(R.id.progressBar);

        ViewGroup viewGroup = findViewById(R.id.root);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            view.setVisibility(VISIBILITY2);
        }

        progressBar.setVisibility(VISIBILITY1);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
