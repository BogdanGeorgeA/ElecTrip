package com.example.navigation3;

import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class NewToken {
    private static JsonPlaceHolderApi jsonPlaceHolderApi;

    public static void getNewToken() {
        System.out.println("Obtain new Token");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();

        Call<ResponseRefreshToken> call = jsonPlaceHolderApi.getNewToken(utilizator.getRefreshKey());
        call.enqueue(new Callback<ResponseRefreshToken>() {
            @Override
            public void onResponse(Call<ResponseRefreshToken> call, Response<ResponseRefreshToken> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Nu s-a putut obtine newToken");
                }
                ResponseRefreshToken raspunsBody = response.body();
                if (raspunsBody.isSuccess()) {
                    System.out.println("Refresh-Token succesfully obtained");
                    Headers raspunsHeader = response.headers();
                    String newToken = raspunsHeader.get("Auth-Token");
                    System.out.println("newToken :" + newToken);
                    utilizator.setAuthentificationKey(newToken);
                }
            }

            @Override
            public void onFailure(Call<ResponseRefreshToken> call, Throwable t) {
                System.out.println("Fail la RefreshToken");
            }
        });
    }
}
