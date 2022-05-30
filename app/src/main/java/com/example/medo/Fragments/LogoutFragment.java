package com.example.medo.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.medo.LoginActivity;
import com.example.medo.MainActivity;
import com.example.medo.R;
import com.example.medo.SignupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LogoutFragment extends Fragment {

    Button yes, no;
    BottomNavigationView bottomNavBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);

        yes.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        });

        no.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_fragment, new HomeFragment()).commit();
        });

        return view;

    }
}