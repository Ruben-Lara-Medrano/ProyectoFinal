package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.pojos.DireccionesBd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class principal extends AppCompatActivity {
    List<Publicacion> publicacionLista = new ArrayList<>();
    RequestQueue requestQueue;
    DireccionesBd direcciones = new DireccionesBd();
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    FirebaseUser mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setContentView(R.layout.activity_principal); mAuth = FirebaseAuth.getInstance().getCurrentUser();


        FloatingActionButton anadirPublicaion = findViewById(R.id.anadirPublicacion);
        init();
        anadirPublicaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);

            }
        });
    }

    public void ShowPopup(View v) {
        //Publicacion publicacion = new Publicacion(R.drawable.ic_foto_perfil_predeterminada,"");
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup_publicacion, null);
        dialogBuilder= new AlertDialog.Builder(this);
        EditText texto = contactPopupView.findViewById(R.id.texto);
        Button btnEnviar = contactPopupView.findViewById(R.id.btnEnviar);
        TextView txtclose = contactPopupView.findViewById(R.id.txtclose);
        String user_id = mAuth.getUid();
        int listo=0;
        dialogBuilder.setView(contactPopupView);
        dialog= dialogBuilder.create();
        dialog.show();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(texto.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "El campo texto debe estar lleno.", Toast.LENGTH_SHORT).show();
                }
                else{
                    insertarPublicacion(user_id,texto.getText().toString());

                    dialog.cancel();

                }

            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }
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
    public void principal (View view)
    {
        Intent botonChat =new Intent(this,principal.class );
        startActivity(botonChat);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //Carpeta res - menu - overflow y pasamos el objeto menu
        //getMenuInflater().inflate(R.menu.overflow, menu);
        getMenuInflater().inflate(R.menu.menuacciones, menu);
        return true;
    }
    public void init(){
        obtenerPublicaciones(direcciones.buscarPublicaciones());

    }
    private void obtenerPublicaciones(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        publicacionLista.add(new Publicacion(R.drawable.ic_foto_perfil_predeterminada,jsonObject.getString("texto")));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                setRecyclerView(publicacionLista);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
    public void setRecyclerView(List<Publicacion> publicacionesLista){
        adaptadorLista listadapter = new adaptadorLista(publicacionLista, this);
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
    private boolean cargarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("Musica", Context.MODE_PRIVATE);
        Boolean sonidoActivado = preferences.getBoolean("Musica",true);
        return sonidoActivado;
    }
    //metodo para la insercion de publicaciones
    private void insertarPublicacion(String idFirebase, String texto){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, direcciones.anadirPublicaciones(), response ->
                Toast.makeText(getApplicationContext(), "Publicacion insertada con exito", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(getApplicationContext(), "La publicacion no se pudo insertar!", Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> atributos = new HashMap<>();
                atributos.put("id", idFirebase);
                atributos.put("texto",texto);
                return atributos;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        if(cargarPreferencias()){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.musica_para_publicacion);
            mp.start();
        }

        Intent botonChat =new Intent(this,principal.class );
        startActivity(botonChat);
    }
}