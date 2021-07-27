package com.example.navigation3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DirectReplyReceiver extends BroadcastReceiver {
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {
            CharSequence replyText = remoteInput.getCharSequence("key_text_reply");
            Message answer = new Message(replyText, null);
            System.out.println("Input introdus: "+answer.getText());
            String raspunsUser=answer.getText().toString();
            Station waypoint = MainActivity.statiePay;
  //          Point coordonateWaypoint = MainActivity.statiePoint;
//            System.out.println("Pay waypoint: lat: "+waypoint.getCoordinates().getLatitude()+" lon"+waypoint.getCoordinates().getLongitude());
   //         System.out.println("Pay waypoint: lat "+coordonateWaypoint.latitude()+" lon:"+coordonateWaypoint.longitude());
            //MainActivity.MESSAGES.add(answer);
           // getPaymentLink(new PaymentInfo(waypoint.getId(),Float.valueOf(raspunsUser)));
            getPaymentLink(new GetPaymentInfo("5eb315e3585c010017b04e57",17.4f));//TODO
            MainActivity.sendChannel1Notification(context);
        }
    }

    private void getPaymentLink(GetPaymentInfo paymentBody){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uber-electric.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Utilizator utilizator = Utilizator.getUtilizatorInstance();
        System.out.println("Auth-key in getPaymentLink: "+utilizator.getAuthentificationKey());
        Call<PaymentResponse> paymentLink = jsonPlaceHolderApi.getPaymentLink(utilizator.getAuthentificationKey(), paymentBody);
        System.out.println("In getPaymentLink request-ul este: "+paymentLink);
        paymentLink.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                System.out.println("Am primit cod: "+response.code()+" pe getPaymentLink");
                if(response.isSuccessful()){
                    PaymentResponse body = response.body();
                    System.out.println("Raspuns getPaymentLink: "+body);
                    deschideLink(body.getPayment());

                    NotificationManagerCompat.from(MainActivity.getAppContext()).cancel(1);
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                System.out.println("Fail la primirea linkului de la payment");
            }
        });
    }
    public void deschideLink(String payment) {
        Uri uri = Uri.parse(payment);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.samoarafamiliamea(intent);
    }
}