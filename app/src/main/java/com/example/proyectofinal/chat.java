package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class chat extends AppCompatActivity {

    private TextView tv1;
    private ListView lv1;

    private String nombres []={"Samuel","Valentina" ,"Pepe" ,"Jefe","Secretaria","DBA","Cafeteria"};
    private String conexion []={"18 Abril","25 Mayo","53 Septiembre" ,"24 febrero","35 Agosto","51 Diciembre","19 Julio"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
        tv1 = (TextView)findViewById(R.id.tv1);
        lv1 = (ListView)findViewById(R.id.lv1);

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_chat, nombres);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText("Ultima conexion con: "+ lv1.getItemAtPosition(position)+ " fue el "+ conexion[position]);
            }
        });
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
    public void principal (View view)
    {
        Intent botonChat = new Intent(this,principal.class );
        startActivity(botonChat);
    }
}