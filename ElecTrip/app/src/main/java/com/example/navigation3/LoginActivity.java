package com.example.navigation3;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private String email;
    private String password;
    private Utilizator utilizator = null;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private EditText emailInput;
    private EditText passwordInput;
    private Gson gson= new Gson();

    private static boolean gataBoss=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(LoginActivity.this,
//                Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            System.out.println("Nu are gps pornit");
//                ActivityCompat.requestPermissions(LoginActivity.this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        Constants.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//        } else {
//            System.out.println("Are gps pornit");
//        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(LoginActivity.this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        LoginActivity.this,
                                        LocationRequest.PRIORITY_HIGH_ACCURACY);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);
        emailInput = (EditText) findViewById(R.id.EmailInput);
        passwordInput = (EditText) findViewById(R.id.PasswordInput);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonForgotPass = (Button) findViewById(R.id.buttonForgotPassword);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        View.OnClickListener loginOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                utilizator = Utilizator.getUtilizatorInstance();
                utilizator.setEmail(email);
                utilizator.setPassword(password);

                verifyLoginData(utilizator);
                TextView numeUtilizLayout = findViewById(R.id.emailtextview);

                /*Gson gson = new Gson();
                if (loginData != null) {
                    String date = gson.toJson(loginData);
                    System.out.println(date);
                }*/

            }
        };


        View.OnClickListener registerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        };

        View.OnClickListener forgotPassOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickForgotPassword();
            }
        };

        if (buttonLogin != null) {
            buttonLogin.setOnClickListener(loginOnClickListener);
        }
        if (buttonRegister != null) {
            buttonRegister.setOnClickListener(registerOnClickListener);
        }
        if (buttonForgotPass != null) {
            buttonForgotPass.setOnClickListener(forgotPassOnClickListener);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void verifyLoginData(final Utilizator utilizator) {

        Map<String, String> fields = new HashMap<>();
        fields.put("email", utilizator.getEmail());
        fields.put("password", utilizator.getPassword());
        Call<Utilizator> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Utilizator>() {
            @Override
            public void onResponse(Call<Utilizator> call, Response<Utilizator> response) {
                if (!response.isSuccessful()) {
                    CharSequence text = "Email or password invalid!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(LoginActivity.this, text, duration).show();
                    return;
                }
                if(response.code()==401){
                        System.out.println("Token expirat");
                        NewToken.getNewToken();
                        verifyLoginData(utilizator);

                }

                Utilizator raspuns = response.body();
                if (raspuns.getSuccess()) {
                    CharSequence text = "Logged succesfully!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(LoginActivity.this, text, duration).show();
                    Headers raspunsHeader = response.headers();
                    utilizator.setAuthentificationKey(raspunsHeader.get("Auth-Token"));
                    utilizator.setRefreshKey(raspunsHeader.get("Refresh-Token"));
                    //getAllStations();

                    openMainActivity();
                }
            }


            @Override
            public void onFailure(Call<Utilizator> call, Throwable t) {
                System.out.println("eroare login");
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("authToken", utilizator.getAuthentificationKey());
        startActivityForResult(intent,0);
        startActivity(intent);
    }

    public void clickRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void clickForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordFirstActivity.class);
        startActivity(intent);
    }


}
