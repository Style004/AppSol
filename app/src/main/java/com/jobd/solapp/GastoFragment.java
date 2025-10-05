package com.jobd.solapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class GastoFragment extends Fragment {

    private TextView tvAmount;
    private StringBuilder currentAmount = new StringBuilder();
    private static final String DEFAULT_AMOUNT_DISPLAY = "00.00";
    private String selectedDate = "";
    private EditText etNotes;

    private static final String API_URL = "http://192.168.1.36:5000/api/gastos";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public GastoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gasto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvDate = view.findViewById(R.id.tvDate);
        tvAmount = view.findViewById(R.id.tvAmount);
        etNotes = view.findViewById(R.id.etNotes);
        MaterialButton btnAdd = view.findViewById(R.id.btnAdd);

        // ===== Fecha =====
        view.findViewById(R.id.cardDate).setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(requireContext(),
                    (view1, y, m, d) -> {
                        selectedDate = String.format("%d/%d/%d", d, m + 1, y);
                        tvDate.setText(selectedDate);
                    },
                    year, month, day);
            dpd.show();
        });

        // ===== Teclado tipo calculadora =====
        int[] buttonIds = {
                R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btn0, R.id.btnDot, R.id.btnBack
        };

        DecimalFormat df = new DecimalFormat("0.00");

        for (int id : buttonIds) {
            view.findViewById(id).setOnClickListener(v -> {
                if (v.getId() == R.id.btnBack) {
                    if (currentAmount.length() > 0) currentAmount.deleteCharAt(currentAmount.length() - 1);
                } else if (v.getId() == R.id.btnDot) {
                    if (!currentAmount.toString().contains(".")) {
                        if (currentAmount.length() == 0) currentAmount.append("0.");
                        else currentAmount.append(".");
                    }
                } else {
                    MaterialButton btn = (MaterialButton) v;
                    currentAmount.append(btn.getText().toString());
                }

                if (currentAmount.length() == 0) tvAmount.setText(DEFAULT_AMOUNT_DISPLAY);
                else {
                    try {
                        double val = Double.parseDouble(currentAmount.toString());
                        tvAmount.setText(df.format(val));
                    } catch (NumberFormatException e) {
                        tvAmount.setText(DEFAULT_AMOUNT_DISPLAY);
                    }
                }
            });
        }

        // ===== Botón Añadir =====
        btnAdd.setOnClickListener(v -> {
            if (currentAmount.length() == 0 || Double.parseDouble(currentAmount.toString()) == 0) {
                Toast.makeText(getContext(), "Ingrese un monto válido", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedDate.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                return;
            }

            enviarGastoAlAPI(currentAmount.toString(), selectedDate, etNotes.getText().toString(), view);
        });
    }

    private void enviarGastoAlAPI(String monto, String fecha, String notas, View view) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("monto", monto);
            json.put("fecha", fecha);
            json.put("notas", notas);
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
                            Toast.makeText(getContext(), "Gasto agregado correctamente", Toast.LENGTH_SHORT).show();
                            currentAmount.setLength(0);
                            tvAmount.setText(DEFAULT_AMOUNT_DISPLAY);
                            Navigation.findNavController(view).popBackStack();
                        } else {
                            Toast.makeText(getContext(), "Error al agregar gasto: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
