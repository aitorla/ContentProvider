package com.example.aitor.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {



    String[] TIPO_LLAMADA = {"", "entrante", "saliente", "perdida"};
    Uri llamadas = Uri.parse("content://call_log/calls");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void listar(View view) {

        TextView salida = (TextView) findViewById(R.id.salida);

        salida.setText("");

        Cursor c = getContentResolver().query(llamadas, null, null, null, null);

        while (c.moveToNext()) {
            salida.append("\n"
                    + DateFormat.format("dd/MM/yy k:mm (",
                    c.getLong(c.getColumnIndex(CallLog.Calls.DATE)))
                    + c.getString(c.getColumnIndex(CallLog.Calls.DURATION)) + ") "
                    + c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)) + ", "
                    + TIPO_LLAMADA[Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)))]);
        }


/*
        String[] proyeccion = new String[]{
                CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER, CallLog.Calls.TYPE};
        String[] argsSelecc = new String[]{"1", "2"};



        Cursor c = getContentResolver().query(
                llamadas,      // Uri del ContentProvider
                proyeccion,    // Columnas que nos interesan
               // "duration = ? and type = ? ",    // consulta WHERE
                "duration = ? and type = ? ",    // consulta WHERE
                argsSelecc,    // parámetros de la consulta anterior
                "date DESC");  // Ordenado por fecha, orden ascenciente


        while (c.moveToNext()) {
            salida.append("\n"
                    + DateFormat.format("dd/MM/yy k:mm (", c.getLong(c.getColumnIndex(CallLog.Calls.DATE)))
                    + c.getString(c.getColumnIndex(CallLog.Calls.DURATION)) + ") "
                    + c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)) + ", "
                    + TIPO_LLAMADA[Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)))]);
        }
        */

    }

    public void insertar(View view) {

        ContentValues valores = new ContentValues();
        valores.put(CallLog.Calls.DATE, new Date().getTime() );
        valores.put(CallLog.Calls.NUMBER, "555555555");
        valores.put(CallLog.Calls.DURATION, "55");
        valores.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
        Uri nuevoElemento = getContentResolver().insert(CallLog.Calls.CONTENT_URI, valores);

    }


    public void borrar(View view) {
        getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number='555555555'", null);

    }


    public void modificar(View view) {

        ContentValues valores2 = new ContentValues();
        valores2.put(CallLog.Calls.NUMBER, "444444444");
        getContentResolver().update(CallLog.Calls.CONTENT_URI, valores2, "number='555555555'", null);

    }

}
