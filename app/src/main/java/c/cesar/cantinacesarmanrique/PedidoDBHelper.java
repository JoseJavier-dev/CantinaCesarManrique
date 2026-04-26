package c.cesar.cantinacesarmanrique;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidoDBHelper extends SQLiteOpenHelper {

    public PedidoDBHelper(Context context) {
        super(context, "cantina.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "euros REAL," +
                "puntos INTEGER," +
                "hora TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}