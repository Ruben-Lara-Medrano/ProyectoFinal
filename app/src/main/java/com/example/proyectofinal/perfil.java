package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class perfil extends AppCompatActivity {
private TextView tvPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toast.makeText(this, "perfil", Toast.LENGTH_SHORT).show();
        tvPerfil=(TextView)findViewById(R.id.tvPerfil);
        registerForContextMenu(tvPerfil);
    }

    public boolean onCreateOptionsMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {

        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu );
        return true;
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.copiar:
                Toast.makeText(this, "Copiado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.editar:
                Toast.makeText(this, "editar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.pegar:
                Toast.makeText(this, "pegar", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

        // Id de cada opcion del menu
//        if(id == R.id.paginaPrincipal){
//
//        }

    }
}