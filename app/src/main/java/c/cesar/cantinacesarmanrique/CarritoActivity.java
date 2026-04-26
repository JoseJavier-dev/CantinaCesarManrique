package c.cesar.cantinacesarmanrique;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarritoActivity extends AppCompatActivity {
    private TextView tvTotal;
    private Button btnPagar;
    private Carrito carrito;
    private CarritoAdapter adapter;
    private MiApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        app = (MiApp) getApplication();

        tvTotal = findViewById(R.id.tvTotal);
        btnPagar = findViewById(R.id.btnPagar);
        RadioButton rbHora = findViewById(R.id.rbHora);

        carrito = (Carrito) getIntent().getSerializableExtra("carrito");
        if (carrito == null) {
            carrito = new Carrito();
        }

        RadioGroup rgRecogida = findViewById(R.id.rgRecogida);
        TimePicker timePicker = findViewById(R.id.timePicker);


        rgRecogida.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbHora) {
                timePicker.setVisibility(View.VISIBLE);
            } else {
                timePicker.setVisibility(View.GONE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerCarrito);
        adapter = new CarritoAdapter(carrito.getItems(), position -> {
            carrito.eliminarItem(position);
            adapter.notifyItemRemoved(position);
            actualizarTotal();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        actualizarTotal();

        btnPagar.setOnClickListener(v -> {

            // Validar horario si se ha seleccionado recoger a hora
            if (rbHora.isChecked()) {
                int hora = timePicker.getHour();
                if (hora < 8 || hora >= 18) {
                    Toast.makeText(this, "Horario no válido. Debe ser entre 08:00 y 18:00", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Calcular total y puntos
            double totalEuros = carrito.calcularTotalEuros();
            int puntosUsados = carrito.calcularTotalPuntos();

            // Actualizar puntos en la App (App subclass)
            app.restarPuntos(puntosUsados);
            app.agregarPuntos(totalEuros);
            PedidoDao dao = new PedidoDao(this);

            dao.insertarPedido(totalEuros, puntosUsados,
                    rbHora.isChecked() ? timePicker.getHour() + ":00" : "inmediato");
            Toast.makeText(this,
                    "Pago realizado\nPuntos totales: " + app.getPuntosEnPosesion(),
                    Toast.LENGTH_SHORT).show();

            // Vaciar carrito y actualizar RecyclerView
            carrito.vaciar();
            adapter.notifyDataSetChanged();
            actualizarTotal();

            // 🔹 Devolver carrito actualizado a la actividad que lo llamó
            Intent resultIntent = new Intent();
            resultIntent.putExtra("carrito", carrito);
            setResult(RESULT_OK, resultIntent);
            finish();  // cerramos CarritoActivity
        });
    }
    private void actualizarTotal() {
        double euros = carrito.calcularTotalEuros();
        int puntos = carrito.calcularTotalPuntos();

        String texto;
        if (euros > 0 && puntos > 0) {
            texto = String.format("Total: %.2f€ + %d pts", euros, puntos);
        } else if (euros > 0) {
            texto = String.format("Total: %.2f€", euros);
        } else {
            texto = "Total: " + puntos + " pts";
        }

        tvTotal.setText(texto);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("carrito", carrito);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}