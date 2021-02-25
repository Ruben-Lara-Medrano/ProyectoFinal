package com.example.proyectofinal.pojos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.proyectofinal.R;

public class DetallesUsuario extends AppCompatActivity {

    TextView NombreUsuario;
    TextView Descripcion_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuarios);

        NombreUsuario = findViewById(R.id.user_name);
        Descripcion_usuario =findViewById(R.id.user_description);
        String s=getIntent().getStringExtra("username");
        String userdescription=getIntent().getStringExtra("userDesc");
//para meter mas cosas aqui y que se vean hay que poner mas set text con sus respectivos  strings en el adapatador
        NombreUsuario.setText(s);
        Descripcion_usuario.setText(userdescription);
    }
}