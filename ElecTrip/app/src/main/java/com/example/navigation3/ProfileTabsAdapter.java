package com.example.navigation3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfileTabsAdapter extends FragmentStateAdapter {
    private CarsFragment carsFragment;
    private StationsFragment stationsFragment;
    private HistoryFragment historyFragment;
    private ProfileActivity profileActivity;

    public ProfileTabsAdapter(@NonNull FragmentActivity fragmentActivity, ProfileActivity profileActivity) {
        super(fragmentActivity);
        this.profileActivity = profileActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                carsFragment = new CarsFragment();
                return carsFragment;
            }
            case 1: {
                stationsFragment = new StationsFragment(profileActivity);
                return stationsFragment;
            }
            default: {
                historyFragment = new HistoryFragment(profileActivity);
                return historyFragment;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public CarsFragment getCarsFragment() {
        return carsFragment;
    }

    public StationsFragment getStationsFragment() {
        return stationsFragment;
    }

    public HistoryFragment getHistoryFragment() {
        return historyFragment;
    }
}
