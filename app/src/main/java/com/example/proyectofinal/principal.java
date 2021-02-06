package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class principal extends AppCompatActivity {
    List<listview> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        init();
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
    public void buscar (View view)
    {
        Intent botonbuscar = new Intent(this,barra_de_busqueda.class );
        startActivity(botonbuscar);
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
    public void init(){
        elements = new ArrayList<>();
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));
        elements.add(new listview(R.drawable.ic_foto_perfil_predeterminada, "Hola Buenos dias a todos y todas."));

        adaptadorLista listadapter = new adaptadorLista(elements, this);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listadapter);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        // Id de cada opcion del menu
        if (id != R.id.paginaPrincipal) {
             if(id == R.id.btnprincipal){
                Toast.makeText(this, "paginaPrincipal", Toast.LENGTH_SHORT).show();
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