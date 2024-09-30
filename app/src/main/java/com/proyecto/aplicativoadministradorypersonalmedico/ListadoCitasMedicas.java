package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.proyecto.aplicativoadministradorypersonalmedico.adapter.CitaMedicaAdapter;
import com.proyecto.aplicativoadministradorypersonalmedico.DB.CitaMedica;

public class ListadoCitasMedicas extends AppCompatActivity {

    private RecyclerView mRecycler;
    private CitaMedicaAdapter mAdapter;
    private FirebaseFirestore db;

    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_citas_medicas);

        db = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerviewCitasMedicas);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));


        Query initialQuery = db.collection("CitaMedica");
        FirestoreRecyclerOptions<CitaMedica> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<CitaMedica>()
                        .setQuery(initialQuery, CitaMedica.class)
                        .build();

        mAdapter = new CitaMedicaAdapter(firestoreRecyclerOptions, this);
        mRecycler.setAdapter(mAdapter);

        imageButton = (ImageButton) findViewById(R.id.imageButtonBack);

        imageButton.setOnClickListener(v ->{
            startActivity(new Intent(this, MainActivity.class));
        });

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