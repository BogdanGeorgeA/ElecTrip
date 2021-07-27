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

public class ForgotPasswordThirdActivity extends AppCompatActivity {
    private JsonApi jsonApi;

    private void createRetrofit() {
        String connectURL = "https://uber-electric.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_third);
    }

    public void clickCancel(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickConfirm(View view) {
        EditText editPassword1 = findViewById(R.id.editPassword1);
        String password1 = editPassword1.getText().toString();

        EditText editPassword2 = findViewById(R.id.editPassword2);
        String password2 = editPassword2.getText().toString();

        if (password1.length() < 3 || password1.length() > 20) {
            Toast myToast = Toast.makeText(getApplicationContext(), "The password must be between 4 and 20 characters!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (password1.contains("#") || password1.contains("%") || password1.contains("*") || password1.contains("/")) {
            Toast myToast = Toast.makeText(getApplicationContext(), "The password should not contain the following symbols # % * /.", Toast.LENGTH_LONG);
            myToast.show();
        } else if (!password1.equals(password2)) {
            Toast myToast = Toast.makeText(getApplicationContext(), "The passwords do not match!", Toast.LENGTH_LONG);
            myToast.show();
        } else {
            showProgress(true);
            createRetrofit();

            String token = getIntent().getStringExtra(ForgotPasswordSecondActivity.EXTRA_VERIFY_TOKEN);

            Context context = this;

            Call<ApiResponse> call = jsonApi.confirmNewPassword(Utilizator.getUtilizatorInstance().getAuthentificationKey(), password1);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                        toast.show();
                        showProgress(false);
                        return;
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Your password has been changed successfully!", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
