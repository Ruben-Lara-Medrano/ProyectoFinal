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
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.pojos.DireccionesBd;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.proyectofinal.pojos.DireccionesBd;
import com.example.proyectofinal.pojos.usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
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
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        sacarUsuario(user_id);
    }
     private void sacarUsuario(String fireId){
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://172.16.2.219/PhpMoviles/sacar_usuario.php?id=iGdD8vdidHSW0qhuC5w4dV136VR2", new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                 JSONObject jsonObject = null;
                 usuario usuario =null;
                 for (int i = 0; i < response.length(); i++) {//String nombre, String telefono, String correo, String puesto
                     try {
                         // Toast.makeText(getApplicationContext(), jsonObject.getString("nombre"), Toast.LENGTH_SHORT).show();
                         jsonObject = response.getJSONObject(i);//jsonObject.getString("nombre"),

                         nombreUsuario.setText(jsonObject.getString("nombre"));
                         /*usuario = new usuario(jsonObject.getString("nombre"),
                                 jsonObject.getString("nombre"),
                                 jsonObject.getString("nombre"),
                                 jsonObject.getString("nombre"));*/

                     } catch (JSONException e) {
                         Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }

             }
         }, error ->{
             Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
         }
         );

         requestQueue.add(jsonArrayRequest);

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