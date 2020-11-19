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

    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
    public void menuPrincipal (View view)
    {
        Intent botonMenuPrincipal = new Intent(this,principal.class );
        startActivity(botonMenuPrincipal);
    }
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
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        // Id de cada opcion del menu
        if(id == R.id.paginaPrincipal){
            Toast.makeText(this, "menuPrincipal", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.chat){
            Toast.makeText(this, "registro", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.perfil){
            Toast.makeText(this, "perfil", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.compartir){
            Toast.makeText(this, "compartir", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.buscar){
            Toast.makeText(this, "buscar", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}