package c.cesar.cantinacesarmanrique;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ComidaAdapter extends RecyclerView.Adapter<ComidaAdapter.ViewHolder> {

    private List<Comida> comidas;
    private Context context;
    private OnAgregarListener listener;

    public interface OnAgregarListener {
        void onAgregar(Comida comida);
    }

    public ComidaAdapter(Context context, List<Comida> comidas, OnAgregarListener listener) {
        this.context = context;
        this.comidas = comidas;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvAlergenos;
        Button btnAgregar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvAlergenos = itemView.findViewById(R.id.tvAlergenos);
            btnAgregar = itemView.findViewById(R.id.btnAgregar);
        }
    }

    @Override
    public ComidaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comida, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComidaAdapter.ViewHolder holder, int position) {
        Comida comida = comidas.get(position);
        holder.tvNombre.setText(comida.getNombre());
        holder.tvPrecio.setText(comida.getPrecioFormateado());

        Set<Alergeno> alergenos = comida.getAlergenos();
        if (alergenos.isEmpty()) {
            holder.tvAlergenos.setText("Sin alérgenos");
        } else {
            List<String> nombres = new ArrayList<>();
            for (Alergeno a : alergenos) {
                String nombre = a.name()
                        .toLowerCase()
                        .replace("_", " ");
                nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
                nombres.add(nombre);
            }
            String resultado = String.join(", ", nombres);
            holder.tvAlergenos.setText("Alérgenos: " + resultado);
        }
        holder.btnAgregar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAgregar(comida);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comidas.size();
    }
}