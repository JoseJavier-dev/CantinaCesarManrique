package c.cesar.cantinacesarmanrique;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private List<ItemCarrito> items;
    private OnEliminarListener listener;

    public interface OnEliminarListener {
        void onEliminar(int position);
    }

    public CarritoAdapter(List<ItemCarrito> items, OnEliminarListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio;
        Button btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCarrito item = items.get(position);

        holder.tvNombre.setText(item.getNombre());

        //  Lógica del precio
        String precioTexto = "";
        if (item.getPrecio() > 0 && item.getPuntos() > 0) {
            precioTexto = item.getPrecio() + "€ + " + item.getPuntos() + " pts";
        } else if (item.getPrecio() > 0) {
            precioTexto = item.getPrecio() + "€";
        } else {
            precioTexto = item.getPuntos() + " pts";
        }

        holder.tvPrecio.setText(precioTexto);

        holder.btnEliminar.setOnClickListener(v -> {
            listener.onEliminar(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
