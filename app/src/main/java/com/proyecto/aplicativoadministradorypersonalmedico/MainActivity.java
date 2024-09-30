package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton buttonRegistroPaciente, buttonListadoPaciente, buttonRegistroPersonalMedico, buttonListadoPersonalMedico,
    buttonListadoCitasMedicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findImageButton();
        initializeImageButton();

    }

    private void findImageButton(){
        buttonRegistroPaciente = findViewById(R.id.buttonRegistroPaciente);
        buttonListadoPaciente = findViewById(R.id.buttonListadoPaciente);
        buttonRegistroPersonalMedico = findViewById(R.id.buttonRegistroPersonalMedico);
        buttonListadoPersonalMedico = findViewById(R.id.buttonListadoPersonalMedico);
        buttonListadoCitasMedicas = findViewById(R.id.buttonListadoCitasMedicas);
    }

    private void initializeImageButton(){
        buttonRegistroPaciente.setOnClickListener(v -> {
            Navigate(RegistroPaciente.class);
        });

        buttonListadoPaciente.setOnClickListener(v -> {
            Navigate(ListadoPacientes.class);
        });

        buttonRegistroPersonalMedico.setOnClickListener(v -> {
            Navigate(RegistroPersonalMedico.class);
        });

        buttonListadoPersonalMedico.setOnClickListener(v -> {
            Navigate(ListadoPersonalMedico.class);
        });

        buttonListadoCitasMedicas.setOnClickListener(v -> {
            Navigate(ListadoCitasMedicas.class);
        });
    }

    private void Navigate(Class<?> activity){
        startActivity(new Intent(this, activity));
        finish();
    }

}