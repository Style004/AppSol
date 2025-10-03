package com.jobd.solapp;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

public class LoginFragment extends Fragment {

    private static final String API_URL = "http://TU_IP_O_DOMINIO:PUERTO/login"; // Cambia por tu API Flask
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public LoginFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout tilCorreo = view.findViewById(R.id.tilEmail);
        TextInputLayout tilPassword = view.findViewById(R.id.tilPassword);
        TextInputEditText etCorreo = view.findViewById(R.id.etEmail);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);

        final boolean[] isPasswordVisible = {false};

        // Mostrar/Ocultar contraseña
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

        // Botón Login → llamar API
        view.findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = etPassword.getText().toString().trim();

            if(correo.isEmpty() || contrasena.isEmpty()){
                Toast.makeText(getContext(), "Correo y contraseña son requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUsuario(correo, contrasena, view);
        });

        // Botón Sign Up → navegar a RegisterFragment
        view.findViewById(R.id.btnSignUp).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void loginUsuario(String correo, String contrasena, View view) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("correo", correo);
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
                if(getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
                    );
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view)
                                    .navigate(R.id.action_loginFragment_to_homeFragment);
                        } else {
                            Toast.makeText(getContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
