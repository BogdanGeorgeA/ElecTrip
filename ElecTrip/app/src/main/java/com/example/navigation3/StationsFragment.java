package com.example.navigation3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationsFragment extends Fragment {
    private View view;
    private ProfileActivity profileActivity;

    public StationsFragment() {
        // Required empty public constructor
    }

    public StationsFragment(ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        profileActivity.selectStations();
    }

    public void listStations(List<String> stations, JsonApi jsonApi, String token) {
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);

        for (int i = 0; i < stations.size(); i++) {
            String station = stations.get(i);

            TextView textView = new TextView(view.getContext());
            textView.setText(station);

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 16, 8, 16);

            textView.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.box_bg_white));
            textView.setLayoutParams(layoutParams);
            textView.setPadding(8, 8, 8, 8);
            textView.setTextSize(24);
            textView.setTextColor(Color.parseColor("#000000"));

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Call<ApiResponse> call = jsonApi.removeStation(token, station);

                                    call.enqueue(new Callback<ApiResponse>() {
                                        @Override
                                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                            if (!response.isSuccessful()) {
                                                Toast toast = Toast.makeText(view.getContext(), response.message(), Toast.LENGTH_LONG);
                                                toast.show();
                                                return;
                                            }

                                            linearLayout.removeView(view);
                                        }

                                        @Override
                                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                                            Toast myToast = Toast.makeText(view.getContext(), "Something went wrong please contact the staff.", Toast.LENGTH_LONG);
                                            myToast.show();
                                            System.out.println(t);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }
            });
            linearLayout.addView(textView);
        }
    }
}
