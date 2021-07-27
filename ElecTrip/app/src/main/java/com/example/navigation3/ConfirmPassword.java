package com.example.navigation3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmPassword extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_password);
        EditText password = findViewById(R.id.PasswordInput);
        Button confirmPassword = findViewById(R.id.button_confirm_password);
        confirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(password.getText().toString().equals(""))) {
                    Utilizator user = Utilizator.getUtilizatorInstance();
//                    if (password.getText().toString().equals(user.getPassword())) {
                    StationData stationData = StationData.getInstance();
                    AddChargingStation addChargingStation = stationData.getAddChargingStation();
                    addChargingStation.setPassword(password.getText().toString());
                    System.out.println("DATELE PENTRU ADDSTATION: " + addChargingStation);
                    registerChargingStations(addChargingStation);
                    openMainActivity();
//                    }
                }
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddChargingPort();
            }
        });
    }

    private void registerChargingStations(AddChargingStation jsonCreat) {
        System.out.println("Obtain new Token");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();

        Call<ResponseAddStation> call = jsonPlaceHolderApi.addStation(utilizator.getAuthentificationKey(), jsonCreat);
        call.enqueue(new Callback<ResponseAddStation>() {
            @Override
            public void onResponse(Call<ResponseAddStation> call, Response<ResponseAddStation> response) {
                System.out.println("Response code " + response.code() + " in registerChargingStations");
                if (response.code() == 401) {
                    System.out.println("Token expirat");
                    NewToken.getNewToken();
                    registerChargingStations(jsonCreat);
                } else if (response.code() == 403) {
                    System.out.println("Parola gresita!");
                    Toast.makeText(MainActivity.getAppContext(), "Parola incorecta!", Toast.LENGTH_LONG).show();
                } else if (response.code() == 406) {
                    System.out.println("User-ul a adaugat deja o statie");
                    Toast.makeText(MainActivity.getAppContext(), "Ai adaugat deja doua statii!", Toast.LENGTH_LONG).show();
                } else if (response.isSuccessful()) {
                    ResponseAddStation raspunsBody = response.body();
                    if (raspunsBody.isSuccess()) {
                        System.out.println("Added Station succesfully!");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAddStation> call, Throwable t) {
                System.out.println("Fail la addStation");
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAddChargingPort() {
        Intent intent = new Intent(this, AddChargingPort.class);
        startActivity(intent);
    }


}
