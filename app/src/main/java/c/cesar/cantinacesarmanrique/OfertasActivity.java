package c.cesar.cantinacesarmanrique;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class OfertasActivity extends AppCompatActivity {

    private TextView tvPuntos;
    private ImageButton btnCarrito;
    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> carritoLauncher;
    private Carrito carrito;
    private MiApp app;

    private OfertasAdapter adapter;
    private List<Oferta> listaOfertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);
        carritoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        carrito = (Carrito) result.getData().getSerializableExtra("carrito");

                        // 🔄 ACTUALIZAR UI
                        actualizarPuntos();
                        adapter.notifyDataSetChanged();
                    }
                }
        );
        // 🧠 App global (puntos)
        app = (MiApp) getApplication();

        // 🔗 Vistas
        tvPuntos = findViewById(R.id.tvPuntos);
        btnCarrito = findViewById(R.id.btnCarrito);
        recyclerView = findViewById(R.id.recyclerOfertas);
        btnCarrito.setOnClickListener(v -> {
            Intent intent = new Intent(OfertasActivity.this, CarritoActivity.class);
            intent.putExtra("carrito", carrito);
            carritoLauncher.launch(intent);
        });
        // 🛒 Recoger carrito
        carrito = (Carrito) getIntent().getSerializableExtra("carrito");
        if (carrito == null) {
            carrito = new Carrito();
        }

        // 🔢 Mostrar puntos
        actualizarPuntos();

        // 📦 Crear ofertas
        listaOfertas = crearOfertas();

        // 🔁 RecyclerView
        adapter = new OfertasAdapter(listaOfertas, carrito, app);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    private void actualizarPuntos() {
        tvPuntos.setText("Tus puntos: " + app.getPuntosEnPosesion());
    }

    private List<Oferta> crearOfertas() {
        List<Oferta> lista = new ArrayList<>();

        // 🔴 ALÉRGENOS (usa los que tengas en tu enum)
        Alergeno GLUTEN = Alergeno.GLUTEN;
        Alergeno LACTOSA = Alergeno.LACTOSA;
        Alergeno FRUTOS_SECOS = Alergeno.FRUTOS_SECOS;

        // 🧾 INGREDIENTES
        Ingrediente pan = new Ingrediente("Pan", new HashSet<>(Arrays.asList(GLUTEN)));
        Ingrediente queso = new Ingrediente("Queso", new HashSet<>(Arrays.asList(LACTOSA)));
        Ingrediente cafeIng = new Ingrediente("Café", new HashSet<>());
        Ingrediente leche = new Ingrediente("Leche", new HashSet<>(Arrays.asList(LACTOSA)));
        Ingrediente refrescoIng = new Ingrediente("Refresco", new HashSet<>());
        Ingrediente cremaCacao = new Ingrediente("Crema cacao", new HashSet<>(Arrays.asList(FRUTOS_SECOS)));

        // 🍽 COMIDAS
        Comida cafe = new Comida(
                "Café",
                Arrays.asList(cafeIng),
                1.20
        );

        Comida cafeLeche = new Comida(
                "Café con leche",
                Arrays.asList(cafeIng, leche),
                1.50
        );

        Comida bocadillo = new Comida(
                "Bocadillo de queso",
                Arrays.asList(pan, queso),
                3.50
        );

        Comida bocadilloDulce = new Comida(
                "Bocadillo dulce",
                Arrays.asList(pan, cremaCacao),
                3.80
        );

        Comida refresco = new Comida(
                "Refresco",
                Arrays.asList(refrescoIng),
                1.80
        );

        // ☕ OFERTA 1 → SOLO PUNTOS
        List<Comida> oferta1Comidas = new ArrayList<>();
        oferta1Comidas.add(cafe);
        oferta1Comidas.add(refresco);

        lista.add(new Oferta(
                "Desayuno básico",
                oferta1Comidas,
                20,   // puntos
                0,    // euros
                true  // solo puntos
        ));

        // 🥪 OFERTA 2 → MIXTA (puntos + dinero)
        List<Comida> oferta2Comidas = new ArrayList<>();
        oferta2Comidas.add(bocadillo);
        oferta2Comidas.add(refresco);

        lista.add(new Oferta(
                "Combo salado",
                oferta2Comidas,
                20,    // puntos
                2.00,  // euros
                false  // mixto
        ));

        // 🍫 OFERTA 3 → SOLO PUNTOS con alérgenos distintos
        List<Comida> oferta3Comidas = new ArrayList<>();
        oferta3Comidas.add(bocadilloDulce);
        oferta3Comidas.add(cafeLeche);

        lista.add(new Oferta(
                "Desayuno dulce",
                oferta3Comidas,
                25,
                0,
                true
        ));

        return lista;
    }
    @Override
    protected void onResume() {
        super.onResume();
        actualizarPuntos();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("carrito", carrito);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}