package com.jobd.solapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jobd.solapp.databinding.FragmentCategoriaGastoBinding;

public class CategoriaGastoFragment extends Fragment {

    private FragmentCategoriaGastoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriaGastoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Botón de volver en toolbar
        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        // ClickListeners para cada categoría
        binding.btnCatComida.setOnClickListener(v -> selectCategory("Comida"));
        binding.btnCatTransporte.setOnClickListener(v -> selectCategory("Transporte"));
        binding.btnCatMedicina.setOnClickListener(v -> selectCategory("Medicina"));
        binding.btnCatCompras.setOnClickListener(v -> selectCategory("Compras"));
        binding.btnCatRenta.setOnClickListener(v -> selectCategory("Renta"));
        binding.btnCatAuto.setOnClickListener(v -> selectCategory("Auto"));
        binding.btnCatEducacion.setOnClickListener(v -> selectCategory("Ahorro"));
        binding.btnCatEntretenimiento.setOnClickListener(v -> selectCategory("Entretenimiento"));
        binding.btnCatMas.setOnClickListener(v -> selectCategory("Más"));
    }

    private void selectCategory(String categoryName) {
        Toast.makeText(requireContext(), "Seleccionado: " + categoryName, Toast.LENGTH_SHORT).show();
        // Aquí puedes agregar lógica para devolver la categoría al fragmento anterior
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
