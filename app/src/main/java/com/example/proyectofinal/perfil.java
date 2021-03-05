 package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.pojos.DireccionesBd;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.proyectofinal.pojos.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

 public class perfil extends AppCompatActivity {
        private TextView tvPerfil, nombreUsuario, correo, PerfilTelefono,puesto;
        ImageView imagen;
        private Button logout;
        DireccionesBd direcciones = new DireccionesBd();
        RequestQueue requestQueue;
        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        FirebaseUser mAuth;
        private static final int REQUEST_PERMISSION_CALL = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        String putId = getIntent().getStringExtra("usuarioId");
        nombreUsuario = findViewById(R.id.nombreUsuario);
        correo = findViewById(R.id.PerfilCorreo);
        PerfilTelefono = findViewById(R.id.PerfilTelefono);
        puesto = findViewById(R.id.puesto);
        logout= findViewById(R.id.logout);
        Spinner spinner = (Spinner)findViewById(R.id.editarFoto);
        registerForContextMenu(nombreUsuario);
        String [] opciones = {"Foto 1", "Foto 2", "Foto 3"};

        Switch quitarMusica = findViewById(R.id.quitarMusica);
        quitarMusica.setChecked(cargarPreferencias());
        //cargamos el sharedPreferences

        imagen= (ImageView) findViewById(R.id.iVFotoPerfil);
        Button editar = findViewById(R.id.editarCaracteristicas);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = mAuth.getUid();
        //Button cambiarImagen = findViewById(R.id.editarFoto);
        Button logout = findViewById(R.id.logout);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        if(putId==null){
            sacarUsuario(user_id);
        }else{
            ImageButton llamar = findViewById(R.id.btnLlamar);
            spinner.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
            editar.setVisibility(View.INVISIBLE);
            sacarUsuario(putId);
            llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ContextCompat.checkSelfPermission(perfil.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                        call(PerfilTelefono.getText().toString());
                    }else{

                    }
                    ActivityCompat.requestPermissions(perfil.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PERMISSION_CALL);
                }

            });
        }
        quitarMusica.setOnCheckedChangeListener((buttonView, isChecked) -> {
            quitarMusica.setChecked(cargarPreferencias());
            if(isChecked){
                activarMusica();
                Toast.makeText(getApplicationContext(), R.string.musicaActiva, Toast.LENGTH_SHORT).show();

            }else{
                desactivarMusica();
                Toast.makeText(getApplicationContext(), R.string.musicaDesactiva, Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        /**cambiarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });*/
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);

            }
        });

        Usuario usuario = new Usuario(null,null,null,null,null,"",null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageRequest imageRequest = new ImageRequest(direcciones.actualizarFoto()+position+".png", response -> {
                    imagen.setImageBitmap(response);
                    meterFoto(""+position);

                }, 0, 0, ImageView.ScaleType.CENTER, null,
                        error -> Toast.makeText(getApplication(),R.string.errorCargarImagen, Toast.LENGTH_SHORT).show());
                requestQueue.add(imageRequest);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), R.string.errorConexion, Toast.LENGTH_SHORT).show();
            }
        });
    }
     //metodo para el sharedPreferences

     private boolean cargarPreferencias(){
         SharedPreferences preferences=getSharedPreferences("Musica", Context.MODE_PRIVATE);
         Boolean sonidoActivado = preferences.getBoolean("Musica",true);
         return sonidoActivado;
     }
     public void activarMusica(){
         SharedPreferences preferences=getSharedPreferences("Musica",Context.MODE_PRIVATE);
         SharedPreferences.Editor sharePreference=preferences.edit();
         sharePreference.putBoolean("Musica",true);
         sharePreference.apply();
     }
     public void desactivarMusica(){
         SharedPreferences preferences=getSharedPreferences("Musica",Context.MODE_PRIVATE);
         SharedPreferences.Editor sharePreference=preferences.edit();
         sharePreference.putBoolean("Musica",false);
         sharePreference.apply();
     }
    private void meterFoto(String posicionImagen){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, direcciones.subirFoto(), response ->{


        }
                , error -> Toast.makeText(getApplicationContext(), R.string.falloActualizacion, Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id",mAuth.getUid());
                parametros.put("imagen",posicionImagen);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
     private void sacarUsuario(String fireId){
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(direcciones.cogerUsuario()+fireId, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                 JSONObject jsonObject = null;
                 Usuario usuario =null;
                 for (int i = 0; i < response.length(); i++) {//String nombre, String telefono, String correo, String puesto
                     try {
                         // Toast.makeText(getApplicationContext(), jsonObject.getString("nombre"), Toast.LENGTH_SHORT).show();
                         jsonObject = response.getJSONObject(i);//jsonObject.getString("nombre"),

                         nombreUsuario.setText(jsonObject.getString("nombre"));
                         correo.setText(jsonObject.getString("email"));
                         PerfilTelefono.setText(jsonObject.getString("telefono"));
                         puesto.setText(jsonObject.getString("puesto"));
                         /*Usuario = new Usuario(jsonObject.getString("nombre"),
                                 jsonObject.getString("nombre"),
                                 jsonObject.getString("nombre"),
                                 jsonObject.getString("nombre"));*/

                     } catch (JSONException e) {
                         Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }

             }
         }, error ->{
             Toast.makeText(getApplicationContext(), R.string.falloAlCargarDatos, Toast.LENGTH_SHORT).show();
         }
         );

         requestQueue.add(jsonArrayRequest);

     }
     //metodo para mostrar el popup de editar datos Usuario

     public void ShowPopup(View v) {

        Usuario editarUsuario = new Usuario("","",null,"");
        dialogBuilder= new AlertDialog.Builder(this);
         final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        EditText editarNombre = contactPopupView.findViewById(R.id.editarNombre);
         EditText editartelefono = contactPopupView.findViewById(R.id.editTelefono);
         EditText editarpuesto = contactPopupView.findViewById(R.id.texto);


         Button btnEnviar = contactPopupView.findViewById(R.id.btnEnviar);
         TextView txtclose = contactPopupView.findViewById(R.id.txtclose);
         txtclose.setText("x");
         dialogBuilder.setView(contactPopupView);
         dialog= dialogBuilder.create();
         dialog.show();
         btnEnviar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(editarNombre.getText().toString().equals("") || editarpuesto.getText().toString().equals("") || editartelefono.getText().toString().equals("")){
                     Snackbar snackbar = Snackbar.make(v, R.string.camposNoVacios , Snackbar.LENGTH_LONG);
                     snackbar.setDuration(2000);
                     snackbar.show();
                 }
                 else{
                     editarUsuario.setNombre(editarNombre.getText().toString());
                     editarUsuario.setTelefono(editartelefono.getText().toString());
                     editarUsuario.setPuesto(editarpuesto.getText().toString());
                     editarUsuario.setId(mAuth.getUid());
                     actualizarUsuario(editarUsuario);
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
        //contactPopupView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

     }
     private void actualizarUsuario(Usuario usuarioActualizar){
         StringRequest stringRequest = new StringRequest(Request.Method.POST, direcciones.actualizarUsuario(), response ->{
             Toast.makeText(getApplicationContext(), R.string.usuarioActualizadoExito, Toast.LENGTH_SHORT).show();
             nombreUsuario.setText(usuarioActualizar.getNombre());
             PerfilTelefono.setText(usuarioActualizar.getTelefono());
             puesto.setText(usuarioActualizar.getPuesto());
         }
                 , error -> Toast.makeText(getApplicationContext(), R.string.falloActualizacion, Toast.LENGTH_SHORT).show()){
             @Override
             protected Map<String, String> getParams() {
                 Map<String, String> parametros = new HashMap<>();
                 parametros.put("id",usuarioActualizar.getId());
                 parametros.put("nombre",usuarioActualizar.getNombre());
                 parametros.put("telefono",usuarioActualizar.getTelefono());
                 parametros.put("puesto",""+usuarioActualizar.getPuesto());

                 return parametros;
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(stringRequest);
     }
     //Metodo para cerrar sesion de Firebase
     public void logout ()
     {
         FirebaseAuth.getInstance().signOut();
         Intent logout = new Intent(this, Login.class );
         startActivity(logout);
     }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.eligeOpcion);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
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
                String text=nombreUsuario.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, R.string.copiarTexto, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
    //metodo de llamar
     private void call(String telefono){
         startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ telefono)));
     }
     //Este metodo es para pedir los permisos de la llamada
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if(requestCode == REQUEST_PERMISSION_CALL){
             if(permissions.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 // String telefono= numero.getText().toString(.trim());
                 call(PerfilTelefono.getText().toString());
             }
             else{
                 if(ActivityCompat.shouldShowRequestPermissionRationale(perfil.this, Manifest.permission.CALL_PHONE)){//Por si dio a no en los permisos
                     new AlertDialog.Builder(this).setMessage(R.string.necesitasPermisos)
                             .setPositiveButton(R.string.volverIntentar, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     ActivityCompat.requestPermissions(perfil.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PERMISSION_CALL);
                                 }
                             })
                             .setNegativeButton(R.string.noGracias, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     //Nada
                                 }
                             }).show();
                 }else{
                     Toast.makeText(this, R.string.necesitasPermisos, Toast.LENGTH_SHORT).show();
                 }
             }
         }
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     }
 }