package co.edu.javeriana.bittus.fitt.Filtros;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.BusquedaEntrenadorAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenador;

public class EntrenadorFiltro extends Filter {



    private List<Entrenador> listUsuarios;
    private BusquedaEntrenadorAdapter adapter;
    private List<Entrenador> listTemporal;



    public EntrenadorFiltro(List<Entrenador> listUsuarios, BusquedaEntrenadorAdapter adapter) {
        this.listUsuarios = listUsuarios;
        this.adapter = adapter;
        listTemporal = new ArrayList<Entrenador>();
        this.listTemporal.addAll(listUsuarios);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        listUsuarios.clear();
        listUsuarios.addAll(listTemporal);

        FilterResults results = new FilterResults();
        List<Entrenador> filtrados = new ArrayList<Entrenador>();
        if(constraint==null || constraint.length()==0) {
            filtrados.addAll(listUsuarios);
        }
        else{
            String filterString = constraint.toString().toLowerCase().trim();

            for (Entrenador usuario:listUsuarios) {
                if(usuario.getNombre().toLowerCase().contains(filterString)) {
                    filtrados.add(usuario);
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
        return ((Entrenador)resultValue).getNombre();
    }
}