package c.cesar.cantinacesarmanrique;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuActivity extends AppCompatActivity {

    private static final int REQUEST_CARRITO = 1; // Identificador para startActivityForResult
    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (getIntent().hasExtra("carrito")) {
            carrito = (Carrito) getIntent().getSerializableExtra("carrito");
        } else {
            carrito = new Carrito();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Comida> menu = crearMenuDemo();
        ComidaAdapter adapter = new ComidaAdapter(this, menu, comida -> {
            carrito.agregarComida(comida);
            Toast.makeText(MenuActivity.this,
                    comida.getNombre() + " añadido al carrito",
                    Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Abrir CarritoActivity esperando resultado
        findViewById(R.id.Carrito).setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CarritoActivity.class);
            intent.putExtra("carrito", carrito);
            startActivityForResult(intent, REQUEST_CARRITO);
        });
    }

    // Recibir carrito actualizado al volver de CarritoActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CARRITO && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("carrito")) {
                carrito = (Carrito) data.getSerializableExtra("carrito");
            }
        }
    }

    private List<Comida> crearMenuDemo() {
        List<Comida> menu = new ArrayList<>();
        Ingrediente pan = new Ingrediente("Pan", Set.of(Alergeno.GLUTEN));
        Ingrediente queso = new Ingrediente("Queso", Set.of(Alergeno.LACTOSA));
        Ingrediente jamon = new Ingrediente("Jamón", Set.of());
        Ingrediente lechuga = new Ingrediente("Lechuga", Set.of());
        Ingrediente tomate = new Ingrediente("Tomate", Set.of());
        Ingrediente huevo = new Ingrediente("Huevo", Set.of(Alergeno.HUEVO));
        Ingrediente atun = new Ingrediente("Atún", Set.of(Alergeno.PESCADO));
        Ingrediente salsa = new Ingrediente("Salsa especial", Set.of(Alergeno.SOJA, Alergeno.SULFITOS));
        Ingrediente cafe = new Ingrediente("Café", Set.of());
        Ingrediente leche = new Ingrediente("Leche", Set.of(Alergeno.LACTOSA));

        menu.add(new Comida("Sandwich Mixto", List.of(pan, queso, jamon, lechuga, tomate), 3.50));
        menu.add(new Comida("Ensalada con Huevo", List.of(lechuga, tomate, huevo, salsa), 2.80));
        menu.add(new Comida("Sandwich de Atún", List.of(pan, atun, lechuga, tomate), 3.90));
        menu.add(new Comida("Queso Frito", List.of(queso, salsa), 2.50));
        menu.add(new Comida("Café con Leche", List.of(cafe, leche, leche), 1.50));

        menu.add(new Comida("Paquete de Doritos", List.of(new Ingrediente("Doritos", Set.of(Alergeno.GLUTEN, Alergeno.LACTOSA, Alergeno.SULFITOS))), 1.50));
        menu.add(new Comida("Palmera de Chocolate", List.of(new Ingrediente("Palmera Chocolate", Set.of(Alergeno.GLUTEN, Alergeno.LACTOSA))), 1.80));
        menu.add(new Comida("Bocadillo de Nutella", List.of(new Ingrediente("Bocadillo Nutella", Set.of(Alergeno.GLUTEN, Alergeno.LACTOSA, Alergeno.FRUTOS_SECOS))), 2.20));
        menu.add(new Comida("Chips de Patata", List.of(new Ingrediente("Chips", Set.of())), 1.20));
        menu.add(new Comida("Bebida Refresco", List.of(new Ingrediente("Refresco", Set.of())), 1.00));

        return menu;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("carrito", carrito);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}