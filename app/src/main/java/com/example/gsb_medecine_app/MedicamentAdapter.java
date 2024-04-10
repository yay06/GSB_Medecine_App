package com.example.gsb_medecine_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class MedicamentAdapter extends ArrayAdapter<Medicament> {

    public MedicamentAdapter(Context context, List<Medicament> medicaments) {
        super(context, 0, medicaments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Medicament medicament = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_medicament, parent, false);
        }

        // Populate the data into the template view using the data object
        TextView tvCodeCIS = convertView.findViewById(R.id.tvCodeCIS);
        TextView tvDenomination = convertView.findViewById(R.id.tvDenomination);
        TextView tvFormePharmaceutique = convertView.findViewById(R.id.tvFormePharmaceutique);
        TextView tvVoiesAdmin = convertView.findViewById(R.id.tvVoiesAdmin);
        TextView tvTitulaires = convertView.findViewById(R.id.tvTitulaires);
        TextView tvStatutAdministratif = convertView.findViewById(R.id.tvStatutAdministratif);

        tvCodeCIS.setText(String.valueOf(medicament.getCodeCIS()));
        tvDenomination.setText(medicament.getDenomination());
        tvFormePharmaceutique.setText(medicament.getFormePharmaceutique());
        tvVoiesAdmin.setText(medicament.getVoiesAdmin());
        tvTitulaires.setText(medicament.getTitulaires());
        tvStatutAdministratif.setText(medicament.getStatutAdministratif());

        // Return the completed view to render on screen
        return convertView;
    }
}
