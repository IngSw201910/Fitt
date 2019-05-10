package co.edu.javeriana.bittus.fitt.Filtros;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;


import co.edu.javeriana.bittus.fitt.Adapters.EntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;

public class RutinasFiltro extends Filter {



    private List<Entrenamiento> listEntrenamientos;
    private EntrenamientosAdapter adapter;
    private List<Entrenamiento> listTemporal;



    public RutinasFiltro(List<Entrenamiento> listEntrenamientos, EntrenamientosAdapter adapter) {
        this.listEntrenamientos = listEntrenamientos;
        this.adapter = adapter;
        listTemporal = new ArrayList<Entrenamiento>();
        this.listTemporal.addAll(listEntrenamientos);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        listEntrenamientos.clear();
        listEntrenamientos.addAll(listTemporal);

        FilterResults results = new FilterResults();
        List<Entrenamiento> filtrados = new ArrayList<Entrenamiento>();
        if(constraint==null || constraint.length()==0) {
            filtrados.addAll(listEntrenamientos);
        }
        else{
            String filterString = constraint.toString().toLowerCase().trim();

            for (Entrenamiento entrenamiento : listEntrenamientos) {
                if(entrenamiento.getNombre().toLowerCase().contains(filterString)){
                    filtrados.add(entrenamiento);
                }
            }

        }

        results.values =filtrados;
        results.count = filtrados.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.clear();
        adapter.addAll((List) results.values);
        adapter.notifyDataSetChanged();

    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return ((Entrenamiento)resultValue).getNombre();
    }
}
