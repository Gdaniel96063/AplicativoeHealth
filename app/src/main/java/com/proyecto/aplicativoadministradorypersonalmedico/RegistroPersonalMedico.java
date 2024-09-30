package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.proyecto.aplicativoadministradorypersonalmedico.DB.DniResponse;
import com.proyecto.aplicativoadministradorypersonalmedico.Interface.ApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroPersonalMedico extends AppCompatActivity {

    public TextView txtViewNombresPersonalMedico;
    public TextView txtViewApellidoPaternoPersonalMedico;
    public TextView txtViewApellidoMaternoPersonalMedico;
    public TextView txtViewDniPersonalMedico;

    public EditText inputDniPersonalMedico, inputFechaNacimiento, inputCorreoElectronico, inputTelefono;

    public Button buttonBuscarPersonalMedico, buttonRegistrarPersonalMedico;

    public ImageButton buttonMostrarOcultarCalendario, buttonBack;

    public Spinner spinnerEspecialidad;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TextView txtNullTest;

    public static final String BASE_URL = "https://api.apis.net.pe/";
    public static final String TOKEN = "apis-token-10527.5i1BYAJSKM6rJve207mxGfuXOEzzq8G2";

    public CalendarView calendarView;
    public boolean isCalendarVisible = false;
    public SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_personal_medico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findComponents();
        inizialiteButtons();

    }

    public void findComponents(){
        txtViewNombresPersonalMedico = (TextView) findViewById(R.id.textViewNombresPersonalMedico);
        txtViewApellidoPaternoPersonalMedico = (TextView) findViewById(R.id.textViewApellidoPaternoPersonalMedico);
        txtViewApellidoMaternoPersonalMedico = (TextView) findViewById(R.id.textViewApellidoMaternoPersonalMedico);
        inputDniPersonalMedico = (EditText) findViewById(R.id.inputDniPersonalMedico);
        txtViewDniPersonalMedico = (TextView) findViewById(R.id.textViewDniPersonalMedico);
        calendarView = (CalendarView) findViewById(R.id.calendarView2);


        buttonBuscarPersonalMedico = (Button) findViewById(R.id.button);
        buttonRegistrarPersonalMedico = (Button) findViewById(R.id.btnRegistrarPersonalMedico);
        buttonMostrarOcultarCalendario = (ImageButton) findViewById(R.id.btnCalendar);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        inputFechaNacimiento = (EditText) findViewById(R.id.editTextDate);
        inputCorreoElectronico = (EditText) findViewById(R.id.editTextEmailAddress);
        inputTelefono = (EditText) findViewById(R.id.editTextPhone);
        spinnerEspecialidad = (Spinner) findViewById(R.id.spinner);
        buttonBack = (ImageButton) findViewById(R.id.imageButton);
    }

    public void inizialiteButtons(){
        buttonBuscarPersonalMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDniInfoResponse();
            }
        });

        buttonRegistrarPersonalMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid() && txtViewNombresPersonalMedico.getText().toString().length() != 0)
                    registrarPersonalMedico();
            }
        });

        calendarView.setVisibility(CalendarView.GONE);

        buttonMostrarOcultarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarOcultarCalendario();
            }
        });

        calendarView.setOnDateChangeListener((view, dayOfMonth, month, year) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(dayOfMonth, month, year);
            String formattedDate = dateFormat.format(selectedDate.getTime());
            inputFechaNacimiento.setText(formattedDate);
            calendarView.setVisibility(CalendarView.GONE);
            isCalendarVisible = false;
        });

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    public void getDniInfoResponse(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<DniResponse> call = apiService.getDniInfo("Bearer " + TOKEN, inputDniPersonalMedico.getText().toString());

        call.enqueue(new Callback<DniResponse>() {
            @Override
            public void onResponse(Call<DniResponse> call, Response<DniResponse> response) {
                if (response.isSuccessful()) {
                    DniResponse dniResponse = response.body();
                    Log.d("API_RESPONSE", "Response: " + dniResponse.getNombres());
                    txtViewNombresPersonalMedico.setText((dniResponse.getNombres()));
                    txtViewApellidoPaternoPersonalMedico.setText((dniResponse.getApellidoPaterno()));
                    txtViewApellidoMaternoPersonalMedico.setText((dniResponse.getApellidoMaterno()));
                    txtViewDniPersonalMedico.setText(inputDniPersonalMedico.getText().toString());
                } else {
                    Log.e("Error_Api", "Error: " + response.code());
                    mostrarDialogoTemporal(response.code());
                }
            }

            @Override
            public void onFailure(Call<DniResponse> call, Throwable t) {
                Log.e("Error_Api", "Error: " + t.getMessage());
            }
        });
    }

    public void registrarPersonalMedico() {

        Map<String, Object> personalMedico = new HashMap<>();
        personalMedico.put("nombres", txtViewNombresPersonalMedico.getText().toString());
        personalMedico.put("apellidoPaterno", txtViewApellidoPaternoPersonalMedico.getText().toString());
        personalMedico.put("apellidoMaterno", txtViewApellidoMaternoPersonalMedico.getText().toString());
        personalMedico.put("fechaNacimiento", inputFechaNacimiento.getText().toString());
        personalMedico.put("correoElectronico", inputCorreoElectronico.getText().toString());
        personalMedico.put("Telefono", inputTelefono.getText().toString());
        personalMedico.put("DNI", txtViewDniPersonalMedico.getText().toString());
        personalMedico.put("Especialidad", spinnerEspecialidad.getSelectedItem().toString());

        db.collection("PersonalMedico").document(txtViewDniPersonalMedico.getText().toString())
                .set(personalMedico);
//                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Personal Medico registrado con éxito"))
//                .addOnFailureListener(e -> Log.w("Firebase", "Error al registrar Personal Medico", e));


        inputDniPersonalMedico.setText("");
        txtViewNombresPersonalMedico.setText("");
        txtViewApellidoPaternoPersonalMedico.setText("");
        txtViewApellidoMaternoPersonalMedico.setText("");
        txtViewDniPersonalMedico.setText("");
        inputFechaNacimiento.setText("");
        inputCorreoElectronico.setText("");

    }

    public void mostrarOcultarCalendario(){
        if (!isCalendarVisible){
            calendarView.setVisibility(CalendarView.VISIBLE);
            Log.d("Calendar", "Calendario Visible");
        }else{
            calendarView.setVisibility(CalendarView.GONE);
        }
        isCalendarVisible = !isCalendarVisible;
    }

    public void mostrarDialogoTemporal(int numero) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (numero == 404){
            builder.setMessage("Este DNI no existe")
                    .setCancelable(false);
        }
        else if (numero == 422){
            builder.setMessage("Este DNI no es válido")
                    .setCancelable(false);
        }
        else{
            builder.setMessage("Error Desconocido")
                    .setCancelable(false);
        }


        AlertDialog dialog = builder.create();
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 3000);
    }

    public boolean valid(){
        boolean retort = true;

        String c1=inputCorreoElectronico.getText().toString();
        String c2=inputFechaNacimiento.getText().toString();
        String c3=inputTelefono.getText().toString();

        if(c1.isEmpty()){
            inputCorreoElectronico.setError("Ingrese su correo electrónico");
            retort = false;
        }
        if(c2.isEmpty()){
            inputFechaNacimiento.setError("Ingrese su fecha de Nacimiento");
            retort = false;
        }
        if(c3.isEmpty() || c3.length()!=9){
            inputTelefono.setError("Ingrese su número de teléfono");
            retort = false;
        }

        return retort;
    }

    public void clearComponents(){
        inputDniPersonalMedico.setText("");
        txtViewNombresPersonalMedico.setText("");
        txtViewApellidoPaternoPersonalMedico.setText("");
        txtViewApellidoMaternoPersonalMedico.setText("");
        txtViewDniPersonalMedico.setText("");
        inputFechaNacimiento.setText("");
        inputCorreoElectronico.setText("");
        inputTelefono.setText("");
        spinnerEspecialidad.getItemAtPosition(0);
    }
}