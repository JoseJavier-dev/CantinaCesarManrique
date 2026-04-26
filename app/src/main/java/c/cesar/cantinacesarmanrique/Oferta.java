package c.cesar.cantinacesarmanrique;


import java.io.Serializable;
import java.util.List;

public class Oferta implements ItemCarrito, Serializable {
    private String nombre;
    private List<Comida> comidas;
    private int puntosNecesarios;
    private double precioFinal;
    private boolean soloPuntos;
    public Oferta(String nombre, List<Comida> comidas, int puntosNecesarios, double precioFinal, boolean soloPuntos) {
        this.nombre = nombre;
        this.comidas = comidas;
        this.puntosNecesarios = puntosNecesarios;
        this.precioFinal = precioFinal;
        this.soloPuntos = soloPuntos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Comida> getComidas() {
        return comidas;
    }

    public int getPuntos() {
        return puntosNecesarios;
    }

    public double getPrecio() {
        return precioFinal;
    }

    public boolean isSoloPuntos() {
        return soloPuntos;
    }
}