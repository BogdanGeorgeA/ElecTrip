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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private RegisterInfo registerInfo;
    private JsonApi jsonApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void clickRegister(View view) {
        EditText viewEmail = findViewById(R.id.emailString);
        String email = viewEmail.getText().toString();

        EditText viewName = findViewById(R.id.nameString);
        String name = viewName.getText().toString();

        EditText viewSurname = findViewById(R.id.surnameString);
        String surname = viewSurname.getText().toString();

        EditText viewNumber = findViewById(R.id.phoneNumber);
        String phoneNumber = viewNumber.getText().toString();

        EditText viewPassword = findViewById(R.id.passwordString);
        String password = viewPassword.getText().toString();

        EditText viewConfirmPassword = findViewById(R.id.passwordStringConfirm);
        String confirmPassword = viewConfirmPassword.getText().toString();

        verifyIntegrity(email, name, surname, phoneNumber, password, confirmPassword);
    }

    public void clickRegisterFacebook(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Signing up with Facebook...", Toast.LENGTH_LONG);
        toast.show();
    }

    private void verifyIntegrity(String email, String name, String surname, String phoneNumber, String password, String confirmPassword) {
        if (email.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter an email!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (name.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter a name!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (surname.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter a surname!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (phoneNumber.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter a phone number!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (password.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter a password!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (confirmPassword.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please confirm your password!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (!email.contains("@")) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter a valid email address.", Toast.LENGTH_LONG);
            myToast.show();
        } else if (phoneNumber.length() < 7 || phoneNumber.length() > 15) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please enter a valid phone number.", Toast.LENGTH_LONG);
            myToast.show();
        } else if (password.length() < 3 || password.length() > 20) {
            Toast myToast = Toast.makeText(getApplicationContext(), "The password must be between 4 and 20 characters!", Toast.LENGTH_LONG);
            myToast.show();
        } else if (password.contains("#") || password.contains("%") || password.contains("*") || password.contains("/")) {
            Toast myToast = Toast.makeText(getApplicationContext(), "The password should not contain the following symbols # % * /.", Toast.LENGTH_LONG);
            myToast.show();
        } else if (!password.equals(confirmPassword)) {
            Toast myToast = Toast.makeText(getApplicationContext(), "The passwords do not match!", Toast.LENGTH_LONG);
            myToast.show();
        } else {
            registerInfo = new RegisterInfo(surname, name, phoneNumber, email, password);

            showProgress(true);
            createRegister();
        }
    }

    private void createRegister() {
        String connectURL = "https://uber-electric.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(connectURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        Call<ApiResponse> call = jsonApi.createRegister(registerInfo);

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

                Toast myToast = Toast.makeText(getApplicationContext(), "You have successfully registered! An email has been sent for confirmation.", Toast.LENGTH_LONG);
                myToast.show();

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
