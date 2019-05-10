package co.edu.javeriana.bittus.fitt.Filtros;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;

public class UsuarioFiltro extends Filter {



    private List<Usuario> listRutinas;
    private UsuariosAdapter adapter;
    private List<Usuario> listTemporal;



    public UsuarioFiltro(List<Usuario> listRutinas, UsuariosAdapter adapter) {
        this.listRutinas = listRutinas;
        this.adapter = adapter;
        listTemporal = new ArrayList<Usuario>();
        this.listTemporal.addAll(listRutinas);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        listRutinas.clear();
        listRutinas.addAll(listTemporal);

        FilterResults results = new FilterResults();
        List<Usuario> filtrados = new ArrayList<Usuario>();
        if(constraint==null || constraint.length()==0) {
            filtrados.addAll(listRutinas);
        }
        else{
            String filterString = constraint.toString().toLowerCase().trim();

            for (Usuario rutina:listRutinas) {
               /* if(rutina.getNombre().toLowerCase().contains(filterString)){
                    filtrados.add(rutina);
                }*/
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

    //@Override
   /* public CharSequence convertResultToString(Object resultValue) {
        return ((Usuario)resultValue).getNombre();
    }*/
}
