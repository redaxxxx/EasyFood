package com.example.easyfood.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfood.Constants;
import com.example.easyfood.R;
import com.example.easyfood.SharedPrefer;
import com.example.easyfood.databinding.FragmentProfileBinding;
import com.example.easyfood.ui.activities.LoginActivity;
import com.example.easyfood.ui.activities.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private SharedPrefer sharedPrefer;
    private HashMap<String, String> userInfo;
    private FirebaseAuth.AuthStateListener stateListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        sharedPrefer = new SharedPrefer(getActivity());
        userInfo = sharedPrefer.getUserInfo();

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null){
                    binding.signInBtn.setOnClickListener(view -> {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    });
                }else {
                    binding.welcomeTV.setVisibility(View.VISIBLE);
                    binding.welcomeTV.append(" " + userInfo.get(Constants.USERNAME_KEY));
                    binding.circleImageView.setVisibility(View.VISIBLE);
                    binding.usernameLayout.setVisibility(View.VISIBLE);
                    binding.usernameTV.setText(userInfo.get(Constants.USERNAME_KEY));
                    binding.emailLayout.setVisibility(View.VISIBLE);
                    binding.emailTV.setText(userInfo.get(Constants.EMAIL_KEY));
                    binding.signInBtn.setVisibility(View.GONE);
                }
            }
        };

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(stateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(stateListener);
    }
}