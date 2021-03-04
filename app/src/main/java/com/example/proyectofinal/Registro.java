package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.PeriodicSync;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.pojos.DireccionesBd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText pass;
    private EditText confirmacionpass, nombre, puesto,telefono;
    private CheckBox tusDatos;
    String idFirebase;
    boolean confirmacion = false;
    DireccionesBd direcciones = new DireccionesBd();
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
   // String url="http://192.168.8.119:80/Android/insertar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        nombre=findViewById(R.id.nombre);
        correo = findViewById(R.id.correoRegistro);
        puesto=findViewById(R.id.puesto);
        telefono=findViewById(R.id.telefono);
        pass = findViewById(R.id.pass1Registro);
        confirmacionpass = findViewById(R.id.pass2Registro);
        tusDatos= findViewById(R.id.tusDatos);
        tusDatos.setOnClickListener(v -> confirmacion= !confirmacion);
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void registrarUsuario (View view){
        if(correo.getText().toString().equals("") || nombre.getText().toString().equals("") || puesto.getText().toString().equals("")
                || pass.getText().toString().equals("") || confirmacionpass.getText().toString().equals("")|| telefono.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Todos los campos deben estar rellenados.", Toast.LENGTH_SHORT).show();
            // Snackbar snackbar = Snackbar.make(view, R.string.todosCamposOk, Snackbar.LENGTH_LONG);
            // snackbar.setDuration(10000);
           // snackbar.setAction("Ok", v -> {
            //});
           // snackbar.show();
        }
        else if(!pass.getText().toString().equals(confirmacionpass.getText().toString())){
            Toast.makeText(getApplicationContext(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
           // snackbar.setDuration(10000);
            //snackbar.setAction("Ok", v -> {
           // });
            //snackbar.show();
        }
        else if(pass.length()<9){
            Toast.makeText(getApplicationContext(), "La contraseña tiene que tener minimo 9 caracteres", Toast.LENGTH_SHORT).show();
        }
        else if(!confirmacion){
            Toast.makeText(getApplicationContext(), "Tienes que aceptar los terminos.", Toast.LENGTH_SHORT).show();
        }
        else{


        if(pass.getText().toString().trim().equals(confirmacionpass.getText().toString().trim())){
            mAuth.createUserWithEmailAndPassword(correo.getText().toString().trim(), pass.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Esta parte no es necesaria
                                //Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                idFirebase = mAuth.getUid();
                                insertarUsuarioBdPropia(idFirebase, nombre.getText().toString(), correo.getText().toString(), puesto.getText().toString(),telefono.getText().toString(), pass.getText().toString());
                                Intent registro = new Intent(getApplicationContext(), principal.class);
                                setPendingIntent();
                                createNotificacionChanel();
                                createNotification();
                                startActivity(registro);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //este log se puede quitar
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Fallo la autenticacion", Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    }
    private void insertarUsuarioBdPropia(String idFirebase, String nombreUsuario, String correo, String puesto,String telefono, String pass){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, direcciones.insertarUsuario(), response ->
                Toast.makeText(getApplicationContext(), "Bienvenid@ a la mejor red social del mundo", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(getApplicationContext(), "No conseguiste logearte WACHINNN!", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> atributos = new HashMap<>();
                atributos.put("id", idFirebase);
                atributos.put("nombre",nombreUsuario);
                atributos.put("telefono",telefono);
                atributos.put("email",correo);
                atributos.put("puesto",puesto);
                atributos.put("pass",pass);
                return atributos;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void VolverLogin (View view)
    {
        Intent VolverLogin = new Intent(this, login.class );
        startActivity(VolverLogin);
    }
    private void setPendingIntent(){
        Intent intent = new Intent(this, perfil.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Registro.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT);
    }
    private void createNotificacionChanel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_persona_perfil);
        builder.setContentTitle("Esta es tu primera notificacion WACHINNN!!!!!");
        builder.setContentText("Cambia lo que quieras de tu perfil, no te cortes.");
        builder.setColor(Color.BLACK);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.GREEN, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}