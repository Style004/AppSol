package com.jobd.solapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        Button btnSignUp = view.findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_welcomeFragment_to_loginFragment));

        btnSignUp.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_welcomeFragment_to_registerFragment));
    }

}
