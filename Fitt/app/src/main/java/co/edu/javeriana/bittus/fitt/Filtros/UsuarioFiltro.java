package co.edu.javeriana.bittus.fitt.Filtros;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;

public class UsuarioFiltro extends Filter {



    private List<Usuario> listUsuarios;
    private UsuariosAdapter adapter;
    private List<Usuario> listTemporal;



    public UsuarioFiltro(List<Usuario> listUsuarios, UsuariosAdapter adapter) {
        this.listUsuarios = listUsuarios;
        this.adapter = adapter;
        listTemporal = new ArrayList<Usuario>();
        this.listTemporal.addAll(listUsuarios);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        listUsuarios.clear();
        listUsuarios.addAll(listTemporal);

        FilterResults results = new FilterResults();
        List<Usuario> filtrados = new ArrayList<Usuario>();
        if(constraint==null || constraint.length()==0) {
            filtrados.addAll(listUsuarios);
        }
        else{
            String filterString = constraint.toString().toLowerCase().trim();

            for (Usuario usuario:listUsuarios) {
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
        return ((Usuario)resultValue).getNombre();
    }
}
