package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.proyecto.aplicativoadministradorypersonalmedico.DB.CitaMedica;
import com.proyecto.aplicativoadministradorypersonalmedico.adapter.CitaMedicaAdapter;
import com.proyecto.aplicativoadministradorypersonalmedico.adapter.PacienteAdapter;

public class DetallePaciente extends AppCompatActivity {

    private TextView txtNombres, txtApellidoPaterno, txtApellidoMaterno, txtDNI;
    private String nombresDetallePaciente, apellidoPaternoPaciente, apellidoMaternoPaciente, dniPaciente = "";


    private RecyclerView mRecycler;
    private CitaMedicaAdapter mAdapter;
    private FirebaseFirestore db;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageButton = (ImageButton) findViewById(R.id.imageButtonBack);

        db = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerviewCitas);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        MostrarDetallePaciente();

        Query initialQuery = db.collection("CitaMedica").whereEqualTo("dni", dniPaciente);
        FirestoreRecyclerOptions<CitaMedica> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<CitaMedica>()
                        .setQuery(initialQuery, CitaMedica.class)
                        .build();

        mAdapter = new CitaMedicaAdapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        imageButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ListadoPacientes.class));
        });

    }

    private void MostrarDetallePaciente(){
        Intent intent = getIntent();
        nombresDetallePaciente = intent.getStringExtra("Nombres");
        txtNombres = (TextView) findViewById(R.id.textViewNombrePacienteDetalle);
        txtNombres.setText(nombresDetallePaciente);
        apellidoMaternoPaciente = intent.getStringExtra("ApellidoPaterno");
        txtApellidoPaterno = (TextView) findViewById(R.id.textViewApellidoPaternoPacienteDetalle);
        txtApellidoPaterno.setText(apellidoMaternoPaciente);
        txtApellidoMaterno = (TextView) findViewById(R.id.textViewApellidoMaternoPacienteDetalle);
        apellidoPaternoPaciente = intent.getStringExtra("ApellidoMaterno");
        txtApellidoMaterno.setText(apellidoPaternoPaciente);
        dniPaciente = intent.getStringExtra("DNI");
        txtDNI = (TextView) findViewById(R.id.textDNIPacienteDetalle);
        txtDNI.setText(dniPaciente);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}