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
import com.proyecto.aplicativoadministradorypersonalmedico.DB.PersonalMedico;
import com.proyecto.aplicativoadministradorypersonalmedico.adapter.PacienteAdapter;
import com.proyecto.aplicativoadministradorypersonalmedico.adapter.PersonalMedicoAdapter;

public class ListadoPersonalMedico extends AppCompatActivity {

    private RecyclerView mRecycler;
    private PersonalMedicoAdapter mAdapter;
    private FirebaseFirestore db;

    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado_personal_medico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findComponents();

        inizialiteButtons();

    }

    private void findComponents(){
        db = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerviewPersonalMedico);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = db.collection("PersonalMedico");

        FirestoreRecyclerOptions<PersonalMedico> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<PersonalMedico>().setQuery(query, PersonalMedico.class).build();

        mAdapter = new PersonalMedicoAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        buttonBack = (ImageButton) findViewById(R.id.imageButtonBack);
    }

    private void inizialiteButtons(){
        buttonBack.setOnClickListener(v ->{
            Navigate(MainActivity.class);
        });
    }

    private void Navigate(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
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