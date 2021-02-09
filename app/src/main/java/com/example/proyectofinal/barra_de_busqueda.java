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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.modelo.InformacionUsuario;
import com.example.proyectofinal.adaptador.Adaptador_barra_de_busqueda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class barra_de_busqueda extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView RecyclerView;
    Adaptador_barra_de_busqueda adaptadorbarradebusqueda;
    EditText searchView;
    CharSequence search="";
    List<InformacionUsuario> ListaUsuarios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_de_busqueda);

        searchView = findViewById(R.id.search_bar);

        ListaUsuarios=obtenerFavoritos("http://192.168.8.100:80/Android/buscar_favoritos.php");

       /** ListaUsuarios.add(new InformacionUsuario("Pepe Thomas","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_1));
        ListaUsuarios.add(new InformacionUsuario("Juan Green","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_male_2));
        ListaUsuarios.add(new InformacionUsuario("Laura Michelle","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_4));
        ListaUsuarios.add(new InformacionUsuario("Betty La fea","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_male_4));
        ListaUsuarios.add(new InformacionUsuario("Garcia Lewis","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_1));
        ListaUsuarios.add(new InformacionUsuario("Roberto asdf","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_2));
        ListaUsuarios.add(new InformacionUsuario("Maria Jacks","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_1));
        ListaUsuarios.add(new InformacionUsuario("Sara La crack","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_4));
        ListaUsuarios.add(new InformacionUsuario("Ruben xzcv","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_male_4));
        ListaUsuarios.add(new InformacionUsuario("Adams Grezen","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_male_2));
        ListaUsuarios.add(new InformacionUsuario("Laura zxcv","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_2));
        ListaUsuarios.add(new InformacionUsuario("Betty Lasef","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_male_4));
        ListaUsuarios.add(new InformacionUsuario("Garcia asdf","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_1));
        ListaUsuarios.add(new InformacionUsuario("Alex evfd","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_2));
        ListaUsuarios.add(new InformacionUsuario("Pepa sadf","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_male_2));
        ListaUsuarios.add(new InformacionUsuario("Sara fgsdf","Android Mola un monton tio pruebalo mastodonte crack titan leyenda..", R.drawable.photo_female_4));*/



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
    private List<InformacionUsuario> obtenerFavoritos(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        InformacionUsuario info=new InformacionUsuario();
                        jsonObject = response.getJSONObject(i);
                        ListaUsuarios.add(new InformacionUsuario(jsonObject.getString("Nombre"),jsonObject.getString("descripcion"), R.drawable.photo_female_4));
                       // info.setUserName(jsonObject.getString("Nombre"));
                      //  info.setDescp(jsonObject.getString("descripcion"));
                       // info.setImageUrl(R.drawable.photo_female_4);
                        //ListaUsuarios.add(info);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        return ListaUsuarios;
    }
    private  void setRecyclerView(List<InformacionUsuario> informacionUsuarioList){
        RecyclerView = findViewById(R.id.userRecycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView.setLayoutManager(layoutManager);
        adaptadorbarradebusqueda = new Adaptador_barra_de_busqueda(this, informacionUsuarioList);
        RecyclerView.setAdapter(adaptadorbarradebusqueda);
    }

}