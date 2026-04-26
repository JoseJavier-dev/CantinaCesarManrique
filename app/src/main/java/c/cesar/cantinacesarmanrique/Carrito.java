package c.cesar.cantinacesarmanrique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {

    private List<ItemCarrito> items;

    public Carrito() {
        items = new ArrayList<>();
    }

    //  Añadir comida
    public void agregarComida(Comida comida) {
        items.add(comida);
    }

    //  Añadir oferta
    public void agregarOferta(Oferta oferta) {
        items.add(oferta);
    }

    //  Obtener todos los items
    public List<ItemCarrito> getItems() {
        return items;
    }

    //  Eliminar por posición (para RecyclerView)
    public void eliminarItem(int index) {
        items.remove(index);
    }

    //  Total en euros
    public double calcularTotalEuros() {
        double total = 0.0;
        for (ItemCarrito item : items) {
            total += item.getPrecio();
        }
        return total;
    }

    //  Total en puntos
    public int calcularTotalPuntos() {
        int total = 0;
        for (ItemCarrito item : items) {
            total += item.getPuntos();
        }
        return total;
    }
    public int getPuntosEnUso() {
        int total = 0;

        for (ItemCarrito o : items) {
            total += o.getPuntos();
        }

        return total;
    }
    //  Vaciar carrito
    public void vaciar() {
        items.clear();
    }


}