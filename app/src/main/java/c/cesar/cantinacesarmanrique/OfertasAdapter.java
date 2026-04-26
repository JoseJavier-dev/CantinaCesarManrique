package c.cesar.cantinacesarmanrique;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfertasAdapter extends RecyclerView.Adapter<OfertasAdapter.ViewHolder> {

        private List<Oferta> ofertas;
        private Carrito carrito;
        private MiApp app;

        public OfertasAdapter(List<Oferta> ofertas, Carrito carrito, MiApp app) {
            this.ofertas = ofertas;
            this.carrito = carrito;
            this.app = app;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNombre, tvComposicion,tvPrecioOriginal, tvPrecioNuevo ,tvAlergenos; ;
            Button btnAgregar;

            public ViewHolder(View itemView) {
                super(itemView);
                tvNombre = itemView.findViewById(R.id.tvNombre);
                tvComposicion = itemView.findViewById(R.id.tvComposicion);
                tvPrecioOriginal = itemView.findViewById(R.id.tvPrecioOriginal);
                tvPrecioNuevo = itemView.findViewById(R.id.tvPrecioNuevo);
                btnAgregar = itemView.findViewById(R.id.btnAgregar);
                tvAlergenos = itemView.findViewById(R.id.tvAlergenos);
                tvPrecioOriginal.setPaintFlags(
                        tvPrecioOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ofertas, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Oferta oferta = ofertas.get(position);

            // 🏷 Nombre
            holder.tvNombre.setText(oferta.getNombre());
            holder.tvComposicion.setText(obtenerComposicion(oferta));
            holder.tvAlergenos.setText(obtenerAlergenos(oferta));
            // 🔴 Precio original (sumando comidas)
            double precioOriginal = 0;
            for (Comida c : oferta.getComidas()) {
                precioOriginal += c.getPrecio();
            }
            holder.tvPrecioOriginal.setText(String.format("%.2f€", precioOriginal));

            // 💰 Precio nuevo
            if (oferta.isSoloPuntos()) {
                holder.tvPrecioNuevo.setText(oferta.getPuntos() + " pts");
            } else {
                holder.tvPrecioNuevo.setText(
                        String.format("%.2f€ + %d pts",
                                oferta.getPrecio(),
                                oferta.getPuntos()));
            }


            // 🛒 Acción botón
            holder.btnAgregar.setOnClickListener(v -> {

                int puntosDisponibles = app.getPuntosEnPosesion() - carrito.getPuntosEnUso();

                if (puntosDisponibles < oferta.getPuntos()) {
                    Toast.makeText(v.getContext(),
                            "No tienes suficientes puntos disponibles",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                carrito.agregarOferta(oferta);

                Toast.makeText(v.getContext(),
                        "Oferta añadida al carrito",
                        Toast.LENGTH_SHORT).show();
            });
        }
        private String obtenerAlergenos(Oferta oferta) {
            Set<String> nombres = new HashSet<>();

            for (Comida comida : oferta.getComidas()) {
                for (Ingrediente ing : comida.getIngredientes()) {
                    for (Alergeno a : ing.getAlergenos()) {
                        nombres.add(a.name());
                    }
                }
            }

            if (nombres.isEmpty()) {
                return "Sin alérgenos";
            }

            return "Contiene: " + String.join(", ", nombres);
        }
    private String obtenerComposicion(Oferta oferta) {
        List<String> nombres = new ArrayList<>();
        for (Comida c : oferta.getComidas()) {
            nombres.add(c.getNombre());
        }
        return String.join(" + ", nombres);
    }
        @Override
        public int getItemCount() {
            return ofertas.size();
        }

}
