package co.edu.javeriana.bittus.fitt.Filtros;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.EjerciciosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;

public class EjerciciosFiltro extends Filter {



    private List<Ejercicio> listEjercicios;
    private EjerciciosAdapter adapter;

    public EjerciciosFiltro(List<Ejercicio> listEjercicios, EjerciciosAdapter adapter) {
        this.listEjercicios = listEjercicios;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        List<Ejercicio> filtrados = new ArrayList<Ejercicio>();
        if(constraint==null || constraint.length()==0) {
            filtrados.addAll(listEjercicios);
        }
        else{
            String filterString = constraint.toString().toLowerCase().trim();

            for (Ejercicio ejercicio:listEjercicios) {
                if(ejercicio.getNombre().toLowerCase().contains(filterString)){
                    filtrados.add(ejercicio);
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
        return ((Ejercicio)resultValue).getNombre();
    }
}
