package c.cesar.cantinacesarmanrique;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;





    public class PedidoDao {

        private SQLiteDatabase db;

        public PedidoDao(Context context) {
            PedidoDBHelper helper = new PedidoDBHelper(context);
            db = helper.getWritableDatabase();
        }

        public void insertarPedido(double euros, int puntos, String hora) {
            ContentValues values = new ContentValues();
            values.put("euros", euros);
            values.put("puntos", puntos);
            values.put("hora", hora);

            db.insert("pedido", null, values);
        }
    }
