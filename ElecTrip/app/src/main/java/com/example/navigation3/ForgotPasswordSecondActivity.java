package com.example.navigation3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordSecondActivity extends AppCompatActivity {
    public static final String EXTRA_VERIFY_TOKEN = "com.example.unitedcharging.VERIFY_TOKEN";
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_second);
    }

    public void clickBack(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickVerify(View view) {
        String email = getIntent().getStringExtra(ForgotPasswordFirstActivity.EXTRA_VERIFY_CODE);

        EditText editCode = findViewById(R.id.editCode);
        String code = editCode.getText().toString();

        Map<String, String> identityInfo = new HashMap<>();
        identityInfo.put("email", email);
        identityInfo.put("code", code);

        showProgress(true);
        createRetrofit();

        Context context = this;

        Call<ApiResponse> call = jsonApi.verifyIdentity(identityInfo);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    showProgress(false);
                    return;
                }

                Intent intent = new Intent(context, ForgotPasswordThirdActivity.class);
                intent.putExtra(EXTRA_VERIFY_TOKEN, response.headers().get("Auth-Token"));

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
