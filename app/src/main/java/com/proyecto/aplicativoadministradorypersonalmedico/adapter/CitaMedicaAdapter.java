package com.proyecto.aplicativoadministradorypersonalmedico.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.proyecto.aplicativoadministradorypersonalmedico.DetalleCitaMedica;
import com.proyecto.aplicativoadministradorypersonalmedico.DetallePaciente;
import com.proyecto.aplicativoadministradorypersonalmedico.R;
import com.proyecto.aplicativoadministradorypersonalmedico.DB.CitaMedica;

public class CitaMedicaAdapter extends FirestoreRecyclerAdapter<CitaMedica, CitaMedicaAdapter.ViewHolder>{

    Context context;

    public CitaMedicaAdapter(@NonNull FirestoreRecyclerOptions<CitaMedica> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull CitaMedica model) {
        holder.textViewCodigoCitaMedicaCardView.setText(model.getDni());
        holder.textViewFechaCitaMedicaCardView.setText(model.getFechaCita());
        holder.textViewEstado.setText(model.getEstado());

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetalleCitaMedica.class);
            intent.putExtra("DNICita", model.getDni());
            intent.putExtra("CodigoCita", model.getCodigo());
            intent.putExtra("FechaCita", model.getFechaCita());
            intent.putExtra("AsuntoCita", model.getAsunto());
            intent.putExtra("Estado", model.getEstado());
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_citas_medicas, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCodigoCitaMedicaCardView, textViewFechaCitaMedicaCardView, textViewPersonalMedicoCardView, textViewCorreoElectronicoPersonalMedicoCardView, textViewEspecialidadPersonalMedicoCardView;
        TextView textViewEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCodigoCitaMedicaCardView = itemView.findViewById(R.id.textViewCodigoCitaMedicaCardView);
            textViewFechaCitaMedicaCardView = itemView.findViewById(R.id.textViewFechaCitaMedicaCardView);
            textViewPersonalMedicoCardView = itemView.findViewById(R.id.textViewPersonalMedicoCardView);
            textViewCorreoElectronicoPersonalMedicoCardView = itemView.findViewById(R.id.textViewCorreoElectronicoPersonalMedicoCardView);
            textViewEspecialidadPersonalMedicoCardView = itemView.findViewById(R.id.textViewEspecialidadPersonalMedicoCardView);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
        }
    }
}
