package com.example.navigation3;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface JsonApi {
    @FormUrlEncoded
    @POST("/profile/removeStation")
    Call<ApiResponse> removeStation(@Header("Auth-Token") String token, @Field("stationId") String stationId);

    @PUT("/profile/reset")
    @FormUrlEncoded
    Call<ApiResponse> changeInfo(@Header("Auth-Token") String token,
                                 @Field("firstName") String firstName, @Field("lastName") String lastName,
                                 @Field("phoneNumber") String phoneNumber, @Field("address") String address);

    @GET("/payment/payments")
    Call<ApiResponse> getHistory(@Header("Auth-Token") String token);

    @POST("/profile/changePassword")
    @FormUrlEncoded
    Call<ApiResponse> changePassword(@Header("Auth-Token") String token,
                                     @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @POST("/profile/removeCar")
    @FormUrlEncoded
    Call<ApiResponse> removeCar(@Header("Auth-Token") String token, @Field("carId") String carId);

    @GET("/cars/getAll")
    Call<ApiResponse> getCarList(@Header("Auth-Token") String token);

    @GET("/profile")
    Call<ApiResponse> getProfile(@Header("Auth-Token") String token);

    @FormUrlEncoded
    @POST("/profile/addCar")
    Call<ApiResponse> addCar(@Header("Auth-Token") String toke, @Field("carId") String carId);

    @POST("/auth/login")
    Call<ApiResponse> verifyLogin(@Body LoginInfo loginInfo);

    @POST("/auth/register")
    Call<ApiResponse> createRegister(@Body RegisterInfo registerInfo);

    @FormUrlEncoded
    @POST("/auth/forgotPassword")
    Call<ApiResponse> requestPasswordChange(@FieldMap Map<String, String> requestInfo);

    @FormUrlEncoded
    @POST("/auth/forgotPassword/validate")
    Call<ApiResponse> verifyIdentity(@FieldMap Map<String, String> identityInfo);

    @FormUrlEncoded
    @POST("/profile/changePassword")
    Call<ApiResponse> confirmNewPassword(@Header("Auth-Token") String token, @Field("newPassword") String newPassword);
}
