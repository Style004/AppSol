package com.jobd.solapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Infla el layout de Home
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ----------------------------
        // Botón GASTO → ir a GastoFragment
        // ----------------------------
        view.findViewById(R.id.llIngresosButton).setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.action_homeFragment_to_gastoFragment)
        );

        // ----------------------------
        // Si quieres, puedes agregar aquí más listeners para otros botones
        // ----------------------------
        // Ejemplo: botón INGRESO
        // view.findViewById(R.id.llGastosButton).setOnClickListener(v ->
        //        Navigation.findNavController(v)
        //                .navigate(R.id.action_homeFragment_to_ingresoFragment)
        // );
    }
}
