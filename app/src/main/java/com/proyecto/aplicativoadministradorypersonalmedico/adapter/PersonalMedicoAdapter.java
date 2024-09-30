package com.proyecto.aplicativoadministradorypersonalmedico.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.proyecto.aplicativoadministradorypersonalmedico.DB.PersonalMedico;
import com.proyecto.aplicativoadministradorypersonalmedico.R;

public class PersonalMedicoAdapter extends FirestoreRecyclerAdapter<PersonalMedico, PersonalMedicoAdapter.ViewHolder> {

    public PersonalMedicoAdapter(@NonNull FirestoreRecyclerOptions<PersonalMedico> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PersonalMedicoAdapter.ViewHolder holder, int position, @NonNull PersonalMedico model) {
        holder.nombres.setText(model.getNombres());
        holder.apellidoPaterno.setText(model.getApellidoPaterno());
        holder.apellidoMaterno.setText(model.getApellidoMaterno());
        holder.Telefono.setText(model.getTelefono());
        holder.correoElectronico.setText(model.getCorreoElectronico());
        holder.Especialidad.setText(model.getEspecialidad());
    }

    @NonNull
    @Override
    public PersonalMedicoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_personalmedico, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombres, apellidoPaterno, apellidoMaterno, Telefono, correoElectronico, Especialidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombres = itemView.findViewById(R.id.textViewNombresPersonalMedicoCardView);
            apellidoPaterno = itemView.findViewById(R.id.textViewApellidoPaternoPersonalMedicoCardView);
            apellidoMaterno = itemView.findViewById(R.id.textViewApellidoMaternoPersonalMedicoCardView);
            Telefono = itemView.findViewById(R.id.textViewTelefonoPersonalMedicoCardView);
            correoElectronico = itemView.findViewById(R.id.textViewCorreoElectronicoPersonalMedicoCardView);
            Especialidad = itemView.findViewById(R.id.textViewEspecialidadPersonalMedicoCardView);

        }
    }
}
