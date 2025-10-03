package com.jobd.solapp;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // =======================
        // Toggle de contraseña
        // =======================
        TextInputLayout tilPassword = view.findViewById(R.id.tilPassword);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);

        final boolean[] isPasswordVisible = {false};

        tilPassword.setEndIconOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                // Ocultar contraseña
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tilPassword.setEndIconDrawable(R.drawable.eye_off); // ícono cerrado
                isPasswordVisible[0] = false;
            } else {
                // Mostrar contraseña
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                tilPassword.setEndIconDrawable(R.drawable.eye_on); // ícono abierto
                isPasswordVisible[0] = true;
            }
            // Mantener cursor al final
            etPassword.setSelection(etPassword.getText().length());
        });

        // =======================
        // Botón Crear Cuenta
        // =======================
        MaterialButton btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "¡Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show();

            // Navegar al LoginFragment
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });

        // =======================
        // Enlace "Iniciar Sesión"
        // =======================
        TextView tvLoginLink = view.findViewById(R.id.tvLoginLink);
        tvLoginLink.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }
}
