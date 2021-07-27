package com.example.navigation3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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

public class ForgotPasswordFirstActivity extends AppCompatActivity {
    private JsonApi jsonApi;
    public static final String EXTRA_VERIFY_CODE = "com.example.unitedcharging.VERIFY_CODE";

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
        setContentView(R.layout.activity_forgot_first);
    }

    public void clickBack(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickNext(View view) {
        EditText editEmail = findViewById(R.id.editEmail);
        String email = editEmail.getText().toString();

        RadioGroup radioMethods = findViewById(R.id.radioMethods);
        String method;

        showProgress(true);
        createRetrofit();

        if (radioMethods.getCheckedRadioButtonId() == findViewById(R.id.radioEmail).getId()) {
            method = "email";
        } else {
            method = "sms";
        }

        Map<String, String> requestInfo = new HashMap<>();
        requestInfo.put("email", email);
        requestInfo.put("method", method);

        Call<ApiResponse> call = jsonApi.requestPasswordChange(requestInfo);

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

                Intent intent = new Intent(context, ForgotPasswordSecondActivity.class);
                intent.putExtra(EXTRA_VERIFY_CODE, email);

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
