package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import com.example.proyectofinal.modelo.InformacionUsuario;
import com.example.proyectofinal.adaptador.Adaptador_barra_de_busqueda;
public class barra_de_busqueda extends AppCompatActivity {

    RecyclerView RecyclerView;
    Adaptador_barra_de_busqueda adaptadorbarradebusqueda;
    EditText searchView;
    CharSequence search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_de_busqueda);

        searchView = findViewById(R.id.search_bar);

        List<InformacionUsuario> ListaUsuarios = new ArrayList<>();
        ListaUsuarios.add(new InformacionUsuario("Pepe Thomas","Android Mola un monton tio pruebalo mastodonte crack titan leyenda.", R.drawable.photo_female_1));
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
        ListaUsuarios.add(new InformacionUsuario("Sara fgsdf","Android Mola un monton tio pruebalo mastodonte crack titan leyenda..", R.drawable.photo_female_4));



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

    private  void setRecyclerView(List<InformacionUsuario> informacionUsuarioList){
        RecyclerView = findViewById(R.id.userRecycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView.setLayoutManager(layoutManager);
        adaptadorbarradebusqueda = new Adaptador_barra_de_busqueda(this, informacionUsuarioList);
        RecyclerView.setAdapter(adaptadorbarradebusqueda);
    }

}