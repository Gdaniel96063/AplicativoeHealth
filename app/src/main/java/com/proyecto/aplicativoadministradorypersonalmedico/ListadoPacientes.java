package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

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
import com.proyecto.aplicativoadministradorypersonalmedico.DB.Paciente;
import com.proyecto.aplicativoadministradorypersonalmedico.adapter.PacienteAdapter;

import java.util.concurrent.RecursiveAction;

public class ListadoPacientes extends AppCompatActivity {

    private RecyclerView mRecycler;
    private PacienteAdapter mAdapter;
    private FirebaseFirestore db;

    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_pacientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = db.collection("Pacientes");

        FirestoreRecyclerOptions<Paciente> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Paciente>().setQuery(query, Paciente.class).build();

        mAdapter = new PacienteAdapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        buttonBack = (ImageButton) findViewById(R.id.imageButtonBack);

        buttonBack.setOnClickListener(v ->{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mAdapter.stopListening();

    }

}