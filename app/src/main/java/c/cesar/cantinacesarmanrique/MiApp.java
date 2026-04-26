package c.cesar.cantinacesarmanrique;

import android.app.Application;

public class MiApp extends Application {

    private int puntosEnPosesion = 0;

    public int getPuntosEnPosesion() {
        return puntosEnPosesion;
    }

    public void setPuntosEnPosesion(int puntosEnPosesion) {
        this.puntosEnPosesion = puntosEnPosesion;
    }

    public void agregarPuntos(double eurosGastados) {

        int puntos = (int) (eurosGastados * 10);
        this.puntosEnPosesion += puntos;
    }

    public void restarPuntos(int puntosUsados) {
        this.puntosEnPosesion -= puntosUsados;
        if (this.puntosEnPosesion < 0) this.puntosEnPosesion = 0;
    }
}