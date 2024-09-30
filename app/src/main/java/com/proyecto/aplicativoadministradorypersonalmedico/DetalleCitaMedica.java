package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetalleCitaMedica extends AppCompatActivity {

    private TextView txtCodigoCitaMedicaCardView, txtFechaCitaMedicaCardView, txtAsuntoCitaMedicaDetalle, txtCorreoElectronicoPersonalMedicoCardView, txtEspecialidadPersonalMedicoCardView;
    private String codigoCitaMedica, fechaCitaMedica, asuntoCitaMedica, correoElectronico, especialidad, estado;

    private ImageButton imageButton;

    private Button buttonActualizarCita;

    private FirebaseFirestore db;

    private Spinner spinner, spinerPersonalMedico;

    private List<String> items = new ArrayList<>();
    private List<String> personalMedicoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_cita_medica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        spinner = (Spinner) findViewById(R.id.spinner5);
        imageButton = (ImageButton) findViewById(R.id.imageButtonBack);
        buttonActualizarCita = (Button) findViewById(R.id.buttonActualizarCita);

        spinerPersonalMedico = (Spinner) findViewById(R.id.spinner6);

        MostrarDetallePaciente();

        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListadoPacientes.class);
            startActivity(intent);
        });

        buttonActualizarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinerPersonalMedico.getSelectedItem().toString() != null) ActualizarDocumentosCitaMedica();
            }
        });


        db.collection("PersonalMedico")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String item = document.getString("Especialidad");
                                if (item.equals(asuntoCitaMedica)) {
                                    String personalMedicoId = document.getId();
                                    personalMedicoList.add(personalMedicoId);
                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DetalleCitaMedica.this, android.R.layout.simple_spinner_item, personalMedicoList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinerPersonalMedico.setAdapter(adapter);
                        } else {
                            Log.w("Firestore", "Error al obtener Personal Medico", task.getException());
                        }
                    }
                });
    }

    private void MostrarDetallePaciente(){
        Intent intent = getIntent();

        fechaCitaMedica = intent.getStringExtra("FechaCita");
        txtFechaCitaMedicaCardView = (TextView) findViewById(R.id.textViewFechaCitaMedicaDetalle);
        txtFechaCitaMedicaCardView.setText(fechaCitaMedica);

        asuntoCitaMedica = intent.getStringExtra("AsuntoCita");
        txtAsuntoCitaMedicaDetalle = (TextView) findViewById(R.id.textViewAsuntoCitaMedicaDetalle);
        txtAsuntoCitaMedicaDetalle.setText(asuntoCitaMedica);

        codigoCitaMedica = intent.getStringExtra("CodigoCita");
        txtCodigoCitaMedicaCardView = (TextView) findViewById(R.id.textViewCodigoCitaDetalle);
        txtCodigoCitaMedicaCardView.setText(codigoCitaMedica);

        estado = intent.getStringExtra("Estado");
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        int position = adapter.getPosition(estado);
        if (position >= 0) spinner.setSelection(position);
    }

    private void ActualizarDocumentosCitaMedica() {
        String collection = "CitaMedica";
        String documentId = codigoCitaMedica;

        Map<String, Object> actualizaciones = new HashMap<>();
        actualizaciones.put("estado", spinner.getSelectedItem().toString());
        actualizaciones.put("dniPersonalMedico", spinerPersonalMedico.getSelectedItem().toString());

        ActualizarDocumentos(collection, documentId, actualizaciones);
    }

    private void ActualizarDocumentos(String collectionPath, String documentId, Map<String, Object> actualizaciones) {
        db.collection(collectionPath)
                .document(documentId)
                .update(actualizaciones)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }

}