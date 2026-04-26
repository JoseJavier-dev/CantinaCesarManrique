package c.cesar.cantinacesarmanrique;

import java.io.Serializable;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Comida implements Serializable ,ItemCarrito{
    private String nombre;
    private List<Ingrediente> ingredientes;
    private double precio;

    public Comida(String nombre, List<Ingrediente> ingredientes, double precio) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public double getPrecio() {
        return precio;
    }
    public String getPrecioFormateado() {
        return String.format("%.2f€", precio);
    }
    public Set<Alergeno> getAlergenos() {
        Set<Alergeno> resultado = new HashSet<>();

        for (Ingrediente ingrediente : ingredientes) {
            resultado.addAll(ingrediente.getAlergenos());
        }

        return resultado;
    }
    @Override
    public int getPuntos() {
        return 0;
    }
}