package co.edu.javeriana.bittus.fitt.Filtros;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;


import co.edu.javeriana.bittus.fitt.Adapters.RutinasAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Rutina;

public class RutinasFiltro extends Filter {



    private List<Rutina> listRutinas;
    private RutinasAdapter adapter;
    private List<Rutina> listTemporal;



    public RutinasFiltro(List<Rutina> listRutinas, RutinasAdapter adapter) {
        this.listRutinas = listRutinas;
        this.adapter = adapter;
        listTemporal = new ArrayList<Rutina>();
        this.listTemporal.addAll(listRutinas);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        listRutinas.clear();
        listRutinas.addAll(listTemporal);

        FilterResults results = new FilterResults();
        List<Rutina> filtrados = new ArrayList<Rutina>();
        if(constraint==null || constraint.length()==0) {
            filtrados.addAll(listRutinas);
        }
        else{
            String filterString = constraint.toString().toLowerCase().trim();

            for (Rutina rutina:listRutinas) {
                if(rutina.getNombre().toLowerCase().contains(filterString)){
                    filtrados.add(rutina);
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
        return ((Rutina)resultValue).getNombre();
    }
}
