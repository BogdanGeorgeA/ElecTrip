package com.example.navigation3;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @FormUrlEncoded
    @POST("auth/login")
    Call<Utilizator> createPost(@FieldMap Map<String, String> fields);

    @GET("station/getNearby")
    Call<ResponseNearbyStations> getNearbyStations(@Header("Auth-Token") String authtoken, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("station/getAll")
    Call<ResponseNearbyStations> getAllStations(@Header("Auth-Token") String authtoken);

    @GET("auth/refreshToken")
    Call<ResponseRefreshToken> getNewToken(@Header("Refresh-Token") String refreshtoken);

    @POST("profile/addStation")
    Call<ResponseAddStation> addStation(@Header("Auth-Token") String authtoken, @Body AddChargingStation addChargingStation);

    @POST("routes/newRoute")
    Call<ResponseRoutingModule> getRoute(@Header("Auth-token") String authtoken, @Body BodyRoute body);

    @POST("payment/intend")
    Call<PaymentResponse> getPaymentLink(@Header("Auth-Token") String token, @Body GetPaymentInfo info);
}
