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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {

    private static final String API_URL = "http://10.0.2.2:5000/api/registrar";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public RegisterFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText etNombres = view.findViewById(R.id.etNombres);
        TextInputEditText etApellidos = view.findViewById(R.id.etApellidos);
        TextInputEditText etCorreo = view.findViewById(R.id.etEmail);
        TextInputEditText etTelefono = view.findViewById(R.id.etMobileNumber);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);
        TextInputLayout tilPassword = view.findViewById(R.id.tilPassword);

        final boolean[] isPasswordVisible = {false};
        tilPassword.setEndIconOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tilPassword.setEndIconDrawable(R.drawable.eye_off);
                isPasswordVisible[0] = false;
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                tilPassword.setEndIconDrawable(R.drawable.eye_on);
                isPasswordVisible[0] = true;
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        // Botón Crear Cuenta
        MaterialButton btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            String nombres = etNombres.getText().toString().trim();
            String apellidos = etApellidos.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String contrasena = etPassword.getText().toString().trim();

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(getContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            registrarUsuario(nombres, apellidos, correo, telefono, contrasena, view);
        });

        // Enlace "Iniciar Sesión"
        TextView tvLoginLink = view.findViewById(R.id.tvLoginLink);
        tvLoginLink.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    private void registrarUsuario(String nombres, String apellidos, String correo,
                                  String telefono, String contrasena, View view) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("nombres", nombres);
            json.put("apellidos", apellidos);
            json.put("correo", correo);
            json.put("telefono", telefono);
            json.put("contrasena", contrasena);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
                    );
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "¡Usuario registrado exitosamente!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                        } else {
                            Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
