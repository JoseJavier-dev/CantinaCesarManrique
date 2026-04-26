package c.cesar.cantinacesarmanrique;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import c.cesar.cantinacesarmanrique.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvPuntos;
    private ImageButton btnCarrito;
    private Button btnMenu, btnOfertas;
    private Carrito carrito;

    // Lanzadores para recibir resultados de otras activities
    private ActivityResultLauncher<Intent> menuLauncher;
    private ActivityResultLauncher<Intent> ofertasLauncher;
    private ActivityResultLauncher<Intent> carritoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(0);
        tvPuntos = findViewById(R.id.tvPuntos);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnMenu = findViewById(R.id.btnMenu);
        btnOfertas = findViewById(R.id.btnOfertas);

        // Inicializar carrito
        if (getIntent().hasExtra("carrito")) {
            carrito = (Carrito) getIntent().getSerializableExtra("carrito");
        } else {
            carrito = new Carrito();
        }

        MiApp app = (MiApp) getApplicationContext();

        // Actualizar puntos desde App
        actualizarPuntos(app);

        // Lanzadores para otras activities
        menuLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Carrito nuevoCarrito = (Carrito) result.getData().getSerializableExtra("carrito");
                        if (nuevoCarrito != null) {
                            carrito = nuevoCarrito;
                            actualizarPuntos(app);
                        }
                    }
                }
        );

        ofertasLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Carrito nuevoCarrito = (Carrito) result.getData().getSerializableExtra("carrito");
                        if (nuevoCarrito != null) {
                            carrito = nuevoCarrito;
                            actualizarPuntos(app);
                        }
                    }
                }
        );

        carritoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Carrito nuevoCarrito = (Carrito) result.getData().getSerializableExtra("carrito");
                        if (nuevoCarrito != null) {
                            carrito = nuevoCarrito;
                            actualizarPuntos(app);
                        }
                    }
                }
        );

        // Botones
        btnCarrito.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CarritoActivity.class);
            intent.putExtra("carrito", carrito);
            carritoLauncher.launch(intent);
        });

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("carrito", carrito);
            menuLauncher.launch(intent);
        });

        btnOfertas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OfertasActivity.class);
            intent.putExtra("carrito", carrito);
            ofertasLauncher.launch(intent);
        });
    }

    // Actualizar puntos usando la clase App
    private void actualizarPuntos(MiApp app) {
        tvPuntos.setText("Tus puntos: " + app.getPuntosEnPosesion());
    }
}