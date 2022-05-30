package com.example.medo.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.medo.R;
import com.example.medo.SignupActivity;

public class LogoutFragment extends Fragment {

    Button yes, no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);

        return view;

    }
}