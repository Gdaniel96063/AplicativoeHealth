package com.proyecto.aplicativoadministradorypersonalmedico.adapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.proyecto.aplicativoadministradorypersonalmedico.DB.Paciente;
import com.proyecto.aplicativoadministradorypersonalmedico.DetallePaciente;
import com.proyecto.aplicativoadministradorypersonalmedico.R;

import java.util.ArrayList;

public class PacienteAdapter extends FirestoreRecyclerAdapter<Paciente, PacienteAdapter.ViewHolder> {

    Context context;

    public PacienteAdapter(@NonNull FirestoreRecyclerOptions<Paciente> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PacienteAdapter.ViewHolder holder, int position, @NonNull Paciente model) {
        holder.dni.setText(model.getDni());
        holder.nombres.setText(model.getNombres());
        holder.apellidoPaterno.setText(model.getApellidoPaterno());
        holder.apellidoMaterno.setText(model.getApellidoMaterno());
        holder.Telefono.setText(model.getTelefono());
        holder.correoElectronico.setText(model.getCorreoElectronico());

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetallePaciente.class);
            intent.putExtra("DNI", model.getDni());
            intent.putExtra("Nombres", model.getNombres());
            intent.putExtra("ApellidoPaterno", model.getApellidoPaterno());
            intent.putExtra("ApellidoMaterno", model.getApellidoMaterno());
            intent.putExtra("Telefono", model.getTelefono());
            intent.putExtra("Correo", model.getCorreoElectronico());
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public PacienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_pacientes, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombres, apellidoPaterno, apellidoMaterno, Telefono, correoElectronico, dni;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dni = itemView.findViewById(R.id.textView17);
            nombres = itemView.findViewById(R.id.textView20);
            apellidoPaterno = itemView.findViewById(R.id.textView22);
            apellidoMaterno = itemView.findViewById(R.id.textView23);
            Telefono = itemView.findViewById(R.id.textView24);
            correoElectronico = itemView.findViewById(R.id.textView26);

        }

    }
}
