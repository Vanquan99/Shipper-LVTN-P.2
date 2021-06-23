package com.vanquanshipper.shipper.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.vanquanshipper.shipper.R;
import com.vanquanshipper.shipper.fregment.DeliveryFragment;
import com.vanquanshipper.shipper.fregment.NotificationFragment;
import com.vanquanshipper.shipper.fregment.PendingFragment;
import com.vanquanshipper.shipper.fregment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fragment_frame)
    FrameLayout fragmentFrame;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.navigation_pendding);
    }

    public boolean callFragment(Fragment fragmentClass) {
        if (fragmentClass != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragmentClass).addToBackStack(null).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_pendding:
                fragment = new PendingFragment();
                break;

            case R.id.navigation_delivery:
                fragment = new DeliveryFragment();
                break;

            case R.id.navigation_notifications:
                fragment = new NotificationFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
            default:
                break;
        }
        return callFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
