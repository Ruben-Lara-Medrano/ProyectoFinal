package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }



//    public void menuPrincipal (View view)
//    {
//        Intent botonMenuPrincipal = new Intent(this,principal.class );
//        startActivity(botonMenuPrincipal);
//    }
    public void perfil (View view)
    {
        Intent botonPerfil = new Intent(this,perfil.class );
        startActivity(botonPerfil);
    }
   public void chat (View view)
    {
        Intent botonChat = new Intent(this,chat.class );
        startActivity(botonChat);
    }
    public void principal (View view)
    {
        Intent botonChat = new Intent(this,principal.class );
        startActivity(botonChat);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //Carpeta res - menu - overflow y pasamos el objeto menu
        //getMenuInflater().inflate(R.menu.overflow, menu);
        getMenuInflater().inflate(R.menu.menuacciones, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        // Id de cada opcion del menu
        if (id != R.id.paginaPrincipal) {
             if(id == R.id.btnprincipal){
                Toast.makeText(this, "paginaPrincipal", Toast.LENGTH_SHORT).show();
            }
            else if(id == R.id.btnChat){
                Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
            }
            if(id == R.id.btnperfil){
                Toast.makeText(this, "perfil", Toast.LENGTH_SHORT).show();
            }
            else if(id == R.id.compartir){
                Toast.makeText(this, "Compartir", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(id == R.id.buscar){
                Toast.makeText(this, "Buscar", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else {
            Toast.makeText(this, "menuPrincipal", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}