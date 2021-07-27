package com.example.navigation3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountSettingsActivity extends AppCompatActivity {
    private String token;
    private JsonApi jsonApi;
    private EditText editTextFirst;
    private EditText editTextLast;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextOldPass;
    private EditText editTextNewPass;
    private String first;
    private String last;
    private String phone;
    private String address;
    private String oldPass;
    private String newPass;
    public static final String EXTRA_USER_TOKEN = "com.example.unitedcharging.USER_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        init();
    }

    private void init() {

        editTextFirst = findViewById(R.id.editTextFirst);
        editTextLast = findViewById(R.id.editTextLast);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextOldPass = findViewById(R.id.editTextOldPass);
        editTextNewPass = findViewById(R.id.editTextNewPass);

        String connectURL = "https://uber-electric.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);
    }

    private boolean checkInfo() {
        first = editTextFirst.getText().toString();
        last = editTextLast.getText().toString();
        phone = editTextPhone.getText().toString();
        address = editTextAddress.getText().toString();
        return true;
    }

    private boolean checkPassword() {
        oldPass = editTextOldPass.getText().toString();
        newPass = editTextNewPass.getText().toString();
        return true;
    }

    public void clickCommitInfo(View view) {
        if (checkInfo()) {
            showProgress(true);

            Call<ApiResponse> call = jsonApi.changeInfo(Utilizator.getUtilizatorInstance().getAuthentificationKey(), first, last, phone, address);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast myToast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                        myToast.show();
                        showProgress(false);
                        return;
                    }

                    showProgress(false);
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Account details have been succesfully updated." +
                                "Press the Back button to return to your profile.", Toast.LENGTH_SHORT).show();
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
    }

    public void clickChangePassword(View view) {
        if (checkPassword()) {
            showProgress(true);

            Call<ApiResponse> call = jsonApi.changePassword(Utilizator.getUtilizatorInstance().getAuthentificationKey(), oldPass, newPass);
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

                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Account password has been succesfully updated.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        finish();
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
