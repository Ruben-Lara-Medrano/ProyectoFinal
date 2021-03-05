package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.proyectofinal.adaptador.AdaptadorBarraDeBusqueda;
import com.example.proyectofinal.pojos.DireccionesBd;
import com.example.proyectofinal.pojos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BarraDeBusqueda extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView RecyclerView;
    AdaptadorBarraDeBusqueda adaptadorbarradebusqueda;
    EditText searchView;
    CharSequence search="";
    DireccionesBd direcciones = new DireccionesBd();
    List<Usuario> ListaUsuarios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_de_busqueda);

        searchView = findViewById(R.id.search_bar);

        ListaUsuarios= buscarUsuarios(direcciones.buscar_usuarios());

        setRecyclerView(ListaUsuarios);


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adaptadorbarradebusqueda.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private List<Usuario> buscarUsuarios(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        ListaUsuarios.add(new Usuario(jsonObject.getString("id"),jsonObject.getString("nombre"),jsonObject.getString("email"),jsonObject.getString("telefono"),jsonObject.getString("puesto"),jsonObject.getString("imagen"),null));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.errorConexion, Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        return ListaUsuarios;
    }
    private void setRecyclerView(List<Usuario> usuarioList){
        RecyclerView = findViewById(R.id.userRecycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView.setLayoutManager(layoutManager);
        adaptadorbarradebusqueda = new AdaptadorBarraDeBusqueda(this, usuarioList);
        RecyclerView.setAdapter(adaptadorbarradebusqueda);
    }

}