 package com.example.proyectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class perfil extends AppCompatActivity {
private TextView tvPerfil;
ImageView imagen;
private Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        TextView textView = findViewById(R.id.text_view);
        TextView nombreuser = findViewById(R.id.nombreUsuario);
        registerForContextMenu(textView);
        imagen= (ImageView) findViewById(R.id.iVFotoPerfil);
        FirebaseUser mAuth;
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = mAuth.getUid();
        nombreuser.setText(user_id);
     //   volver.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //  public void onClick(View v) {
        //    AlertDialog.Builder alerta = new AlertDialog.Builder(perfil.this);
        //    alerta.setMessage("¿Desea guardar los cambios?")
        //   .setCancelable(false)
        //     .setPositiveButton("Si", new DialogInterface.OnClickListener() {
        //         @Override
        //          public void onClick(DialogInterface dialog, int which) {
        //              finish();
        //          }
        //      })
        //      .setNegativeButton("No", new DialogInterface.OnClickListener() {
        //          @Override
        //          public void onClick(DialogInterface dialog, int which) {
        //              dialog.cancel();
        //          }
        //      });
        //       AlertDialog titulo = alerta.create();
        //        titulo.setTitle("Salida");
        //       titulo.show();
        //   }
        // });
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