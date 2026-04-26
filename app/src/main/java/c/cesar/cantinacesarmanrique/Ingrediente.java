package c.cesar.cantinacesarmanrique;

    import java.io.Serializable;
    import java.util.Set;

    public class Ingrediente  implements Serializable {
        private String nombre;
        private Set<Alergeno> alergenos;

        public Ingrediente(String nombre, Set<Alergeno> alergenos) {
            this.nombre = nombre;
            this.alergenos = alergenos;
        }

        public Set<Alergeno> getAlergenos() {
            return alergenos;
        }

        public String getNombre() {
            return nombre;
        }
    }

