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

public class RegistroPaciente extends AppCompatActivity {

    public TextView txtViewNombresPaciente, txtViewApellidoPaternoPaciente, txtViewApellidoMaternoPaciente, txtViewDniPaciente;

    public EditText inputDniPaciente, inputFechaNacimiento, inputCorreoElectronico, inputTelefono;

    public Button buttonBuscarPaciente, buttonRegistrarPaciente;

    public ImageButton buttonMostrarOcultarCalendario, buttonBack;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TextView txtNullTest;

    private static final String BASE_URL = "https://api.apis.net.pe/";
    private static final String TOKEN = "apis-token-10527.5i1BYAJSKM6rJve207mxGfuXOEzzq8G2";

    private CalendarView calendarView;
    private boolean isCalendarVisible = false;
    private SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findComponents();

        initializeButtons();

    }

    public void findComponents(){
        txtViewNombresPaciente = (TextView) findViewById(R.id.textViewNombrespaciente);
        txtViewApellidoPaternoPaciente = (TextView) findViewById(R.id.textViewApellidoPaternoPaciente);
        txtViewApellidoMaternoPaciente = (TextView) findViewById(R.id.textViewApellidoMaternoPaciente);
        inputDniPaciente = (EditText) findViewById(R.id.inputDniPaciente);
        txtViewDniPaciente = (TextView) findViewById(R.id.textViewDniPaciente);
        calendarView = (CalendarView) findViewById(R.id.calendarView2);
        buttonBuscarPaciente = (Button) findViewById(R.id.button);
        buttonRegistrarPaciente = (Button) findViewById(R.id.btnRegistrarPaciente);
        buttonMostrarOcultarCalendario = (ImageButton) findViewById(R.id.btnCalendar);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        inputFechaNacimiento = (EditText) findViewById(R.id.editTextDate);
        inputCorreoElectronico = (EditText) findViewById(R.id.editTextEmailAddress);
        inputTelefono = (EditText) findViewById(R.id.editTextPhone);
        buttonBack = (ImageButton) findViewById(R.id.imageButton);
    }

    private void initializeButtons(){
        buttonBuscarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDniInfoResponse();
            }
        });

        buttonRegistrarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid() && txtViewNombresPaciente.getText().toString().length()!=0) registrarPaciente();
            }
        });

        calendarView.setVisibility(CalendarView.GONE);

        buttonMostrarOcultarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarOcultarCalendario();
            }
        });

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        calendarView.setOnDateChangeListener((view, dayOfMonth, month, year) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(dayOfMonth, month, year);
            String formattedDate = dateFormat.format(selectedDate.getTime());
            inputFechaNacimiento.setText(formattedDate);
            calendarView.setVisibility(CalendarView.GONE);
            isCalendarVisible = false;
        });
    }

    private void getDniInfoResponse(){

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

        Call<DniResponse> call = apiService.getDniInfo("Bearer " + TOKEN, inputDniPaciente.getText().toString());

        call.enqueue(new Callback<DniResponse>() {
            @Override
            public void onResponse(Call<DniResponse> call, Response<DniResponse> response) {
                if (response.isSuccessful()) {
                    DniResponse dniResponse = response.body();
                    Log.d("Respuesta_Api", "Response: " + dniResponse.getNombres());
                    txtViewNombresPaciente.setText((dniResponse.getNombres()));
                    txtViewApellidoPaternoPaciente.setText((dniResponse.getApellidoPaterno()));
                    txtViewApellidoMaternoPaciente.setText((dniResponse.getApellidoMaterno()));
                    txtViewDniPaciente.setText(inputDniPaciente.getText().toString());
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

    public void registrarPaciente() {

        Map<String, Object> paciente = new HashMap<>();
        paciente.put("nombres", txtViewNombresPaciente.getText().toString());
        paciente.put("apellidoPaterno", txtViewApellidoPaternoPaciente.getText().toString());
        paciente.put("apellidoMaterno", txtViewApellidoMaternoPaciente.getText().toString());
        paciente.put("fechaNacimiento", inputFechaNacimiento.getText().toString());
        paciente.put("correoElectronico", inputCorreoElectronico.getText().toString());
        paciente.put("Telefono", inputTelefono.getText().toString());
        paciente.put("DNI", txtViewDniPaciente.getText().toString());

        db.collection("Pacientes").document(txtViewDniPaciente.getText().toString())
                .set(paciente);
//                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Paciente registrado con éxito")
//              .addOnFailureListener(e -> Log.w("Firebase", "Error al registrar paciente", e));

        clearComponents();

    }

    private void mostrarOcultarCalendario(){
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
        inputDniPaciente.setText("");
        txtViewNombresPaciente.setText("");
        txtViewApellidoPaternoPaciente.setText("");
        txtViewApellidoMaternoPaciente.setText("");
        txtViewDniPaciente.setText("");
        inputFechaNacimiento.setText("");
        inputCorreoElectronico.setText("");
        inputTelefono.setText("");
    }

}