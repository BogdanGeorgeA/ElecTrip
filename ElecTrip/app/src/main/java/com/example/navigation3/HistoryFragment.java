package com.example.navigation3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private View view;
    private ProfileActivity profileActivity;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public HistoryFragment(ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        profileActivity.selectHistory();
    }

    public void listHistory(List<Payment> payments) {
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);

        for (int i = payments.size() - 1; i >= 0; i--) {
            Payment payment = payments.get(i);

            TextView textView = new TextView(view.getContext());

            String tmp = payment.getCreatedDate().substring(0, 10);
            String date = tmp.substring(8, 10) + '.' + tmp.substring(5, 7) + '.' + tmp.substring(0, 4);

            textView.append("charged: " + Math.round(payment.getKwCharged()) + " kW"
                    + "\n" + "paid: " + Math.round(payment.getTotalPrice()) + " RON"
                    + "\n" + "to: " + payment.getConsumerEmail()
                    + "\n" + "date: " + date
                    + "\n" + "status: " + payment.getStatus()
            );

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 16, 8, 16);

            textView.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.box_bg_white));
            textView.setLayoutParams(layoutParams);
            textView.setPadding(8, 8, 8, 8);
            textView.setTextSize(16);
            textView.setTextColor(Color.parseColor("#000000"));

            linearLayout.addView(textView);
        }
    }
}
