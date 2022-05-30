package com.example.medo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.medo.Fragments.HomeFragment;
import com.example.medo.Fragments.LogoutFragment;
import com.example.medo.Fragments.ProfileFragment;
import com.example.medo.Fragments.QrFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String getfullname;
    String fullname;
    BottomNavigationView bottomNavBar;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getfullname = getIntent().getStringExtra("sendfullname");

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        fullname = userDetails.get(SessionManager.KEY_FULLNAME);


        bottomNavBar = findViewById(R.id.bottom_navBar);
//        bottomMenu();
        
        bottomNavBar.setSelectedItemId(R.id.bottom_nav_home);
        bottomNavBar.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFrag;

            switch (item.getItemId()) {

                case R.id.bottom_nav_home:
                    if (!(id == R.id.bottom_nav_home)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("fullname", fullname );
                        selectedFrag = new HomeFragment();
                        selectedFrag.setArguments(bundle);
                        id = R.id.bottom_nav_home;
                        loadFragment(selectedFrag);
                    }
                    break;
                case R.id.bottom_nav_profile:
                    if (!(id == R.id.bottom_nav_profile)) {
                        selectedFrag = new ProfileFragment();
                        id = R.id.bottom_nav_profile;
                        loadFragment(selectedFrag);
                    }
                    break;
                case R.id.bottom_nav_qr:
                    if (!(id == R.id.bottom_nav_qr)) {
                        selectedFrag = new QrFragment();
                        id = R.id.bottom_nav_qr;
                        loadFragment(selectedFrag);
                    }
                    break;
                case R.id.bottom_logout:
                    if (!(id == R.id.bottom_logout)) {
                        selectedFrag = new LogoutFragment();
                        id = R.id.bottom_logout;
                        loadFragment(selectedFrag);
                    }
                    break;
            }
            return true;
        });
        loadFragment(new HomeFragment());
        id = R.id.bottom_nav_home;
    }

//    private void bottomMenu() {
//
//        bottomNavBar.setOnClickListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int i){
//
//            }
//        });
//
//    }

    private void loadFragment(Fragment selectedFrag) {
        if (selectedFrag != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_fragment, selectedFrag).commit();
        } else {
            Toast.makeText(this, "Fragment Error", Toast.LENGTH_SHORT).show();
        }
    }
}