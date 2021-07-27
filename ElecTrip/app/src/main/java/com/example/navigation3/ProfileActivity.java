package com.example.navigation3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    private String token;
    private List<Car2> car2s;
    private List<String> stations;
    private List<Payment> payments;
    private String username;
    private String location;
    private String paypal;
    private JsonApi jsonApi;
    private ProfileTabsAdapter profileTabsAdapter;
    public static final String EXTRA_USER_TOKEN = "com.example.unitedcharging.USER_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        showProgress(true);
        init();
    }

    private void init() {


        car2s = new ArrayList<>();
        stations = new ArrayList<>();

        String connectURL = "https://uber-electric.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        createTabs();
        getData();
    }

    private void createTabs() {
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerTabs);
        profileTabsAdapter = new ProfileTabsAdapter(this, this);

        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(profileTabsAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayoutTabs);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText("Cars");
                    tab.setIcon(R.drawable.ic_car);
                    break;
                }
                case 1: {
                    tab.setText("Stations");
                    tab.setIcon(R.drawable.ic_station);
                    break;
                }
                case 2: {
                    tab.setText("History");
                    tab.setIcon(R.drawable.ic_history);
                    break;
                }
            }
        }
        );

        tabLayoutMediator.attach();
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

    private void getData() {
        Call<ApiResponse> callProfile = jsonApi.getProfile(Utilizator.getUtilizatorInstance().getAuthentificationKey());
        callProfile.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    myToast.show();
                    showProgress(false);
                    return;
                }

                if (response.body() != null) {
                    User user = response.body().getUser();
                    username = user.getFirstName() + " " + user.getLastName();
                    paypal = user.getPaypalEmail();
                    location = user.getAddress();
                    car2s = user.getListOfCars();
                    stations = user.getListOfChargingStations();

                    profileInfo(username, paypal, location);
                    if (profileTabsAdapter.getCarsFragment() != null) {
                        profileTabsAdapter.getCarsFragment().listCars(car2s);
                    }
                }

                showProgress(false);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG);
                myToast.show();
                showProgress(false);
            }
        });

        Call<ApiResponse> callHistory = jsonApi.getHistory(Utilizator.getUtilizatorInstance().getAuthentificationKey());
        callHistory.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    myToast.show();
                    return;
                }

                if (response.body() != null) {
                    payments = response.body().getPayments();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG);
                myToast.show();
            }
        });
    }

    private void profileInfo(String username, String paypal, String location) {
        TextView textViewName = findViewById(R.id.textViewName);
        EditText textViewPaypal = findViewById(R.id.textViewPayPal);
        EditText textViewLocation = findViewById(R.id.textViewLocation);

        textViewName.setText(username);
        textViewLocation.setText(location);
        textViewPaypal.setText(paypal);
    }

    public void clickChangeInfo(View view) {
        Context context = this;
        Intent intent = new Intent(context, AccountSettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickRefresh(View view) {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void clickAddCar(View view) {
        Context context = this;
        Intent intent = new Intent(context, RegisterCarActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickRemoveCar(View view) {
        String carId = profileTabsAdapter.getCarsFragment().getSelected();

        if (carId == null) {
            Toast.makeText(getApplicationContext(), "No cars to remove.", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Call<ApiResponse> call = jsonApi.removeCar(Utilizator.getUtilizatorInstance().getAuthentificationKey(), carId);

                            Context context = view.getContext();

                            call.enqueue(new Callback<ApiResponse>() {
                                @Override
                                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                    if (!response.isSuccessful()) {
                                        Toast myToast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                                        myToast.show();
                                        return;
                                    }

                                    CarsFragment carsFragment = profileTabsAdapter.getCarsFragment();
                                    ViewPager2 viewPager2 = carsFragment.getViewPager2();
                                    CarsSliderAdapter carsSliderAdapter = carsFragment.getCarsSliderAdapter();

                                    int curr = viewPager2.getCurrentItem();

                                    carsSliderAdapter.getItems().remove(curr);
                                    carsFragment.getCar2s().remove(curr);
                                    carsSliderAdapter.notifyDataSetChanged();

                                    if (carsFragment.getCar2s().isEmpty()) {
                                        TextView textView = findViewById(R.id.textViewCarManufacturer);
                                        textView.setText("No cars owned");

                                        textView = findViewById(R.id.textViewCarModel);
                                        textView.setText("");

                                        textView = findViewById(R.id.textViewNumber);
                                        textView.setText("0/0");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ApiResponse> call, Throwable t) {
                                    Toast myToast = Toast.makeText(getApplicationContext(), "Something went wrong. Please contact the staff.", Toast.LENGTH_LONG);
                                    myToast.show();
                                }
                            });
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void selectStations() {
        if (profileTabsAdapter.getStationsFragment() != null) {
            profileTabsAdapter.getStationsFragment().listStations(stations, jsonApi, token);
        }
    }

    public void selectHistory() {
        if (profileTabsAdapter.getHistoryFragment() != null) {
            profileTabsAdapter.getHistoryFragment().listHistory(payments);
        }
    }

    public List<Car2> getCar2s() {
        return car2s;
    }

    public List<String> getStations() {
        return stations;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
