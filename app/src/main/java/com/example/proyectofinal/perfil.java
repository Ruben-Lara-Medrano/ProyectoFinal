 package com.example.proyectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.proyectofinal.pojos.DireccionesBd;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.proyectofinal.pojos.DireccionesBd;
import com.example.proyectofinal.pojos.usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

 public class perfil extends AppCompatActivity {
private TextView tvPerfil, nombreUsuario, correo, PerfilTelefono,puesto;
ImageView imagen;
private Button volver;
    DireccionesBd direcciones = new DireccionesBd();
     RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        TextView textView = findViewById(R.id.text_view);
        nombreUsuario = findViewById(R.id.nombreUsuario);
        correo = findViewById(R.id.PerfilCorreo);
        PerfilTelefono = findViewById(R.id.PerfilTelefono);
        puesto = findViewById(R.id.puesto);
        registerForContextMenu(textView);
        imagen= (ImageView) findViewById(R.id.iVFotoPerfil);
        FirebaseUser mAuth;
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = mAuth.getUid();
       // nombreuser.setText(user_id);
       // String url ="http://172.16.2.219:80/Android/sacar_usuario.php"+user_id;
        sacarUsuario(user_id);
    }
     private void sacarUsuario(String IdUsuario){
        String url = direcciones.cogerUsuario()+IdUsuario;
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
         /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(direcciones.cogerUsuario()+IdUsuario, response -> {
             JSONObject jsonObject = null;
             usuario usuarioDatos = null;
             for (int i = 0; i < response.length(); i++) {
                 try {
                     jsonObject = response.getJSONObject(i);
                     usuarioDatos = new usuario(
                             jsonObject.getString("nombre"),
                             jsonObject.getString("telefono"),
                             jsonObject.getString("correo"),
                             jsonObject.getString("puesto"));
                 } catch (JSONException e) {
                     Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
             nombreUsuario.setText(usuarioDatos.getNombre());
             PerfilTelefono.setText(usuarioDatos.getTelefono());
             correo.setText(usuarioDatos.getCorreo());
             puesto.setText(usuarioDatos.getPuesto());
             //cargarImagen(usuario.getFoto());
         }, error ->{
             Toast.makeText(getApplicationContext(), "Error al cargar los datos del usuario.", Toast.LENGTH_SHORT).show();
             FirebaseAuth.getInstance().signOut();
             /**SharedPreferences preferences=getSharedPreferences("sonido",Context.MODE_PRIVATE);
             SharedPreferences.Editor editor=preferences.edit();
             editor.clear();
             editor.commit();*//*
             Intent i = new Intent(getApplicationContext(), login.class);
             startActivity(i);
         }
         );
         requestQueue.add(jsonArrayRequest);
*/
     }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Elige una opcion");
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }
    public void mapa (View view)
    {
        Intent botonMapa = new Intent(this,MapsEmpresa.class );
        startActivity(botonMapa);
    }
    public void volver (View view)
    {
        Intent volver = new Intent(this,principal.class );
        startActivity(volver);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcion1:
                Toast.makeText(this, "Cambio de texto", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opcion2:
                Toast.makeText(this, "Borrado de texto", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void cambiarFoto(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent btnCambiarImagen = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        btnCambiarImagen.setType("image/");
        startActivityForResult(btnCambiarImagen.createChooser(btnCambiarImagen,"Selecciona una imagen"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
}