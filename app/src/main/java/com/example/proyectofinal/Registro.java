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
import com.google.android.material.snackbar.Snackbar;
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
            Snackbar snackbar = Snackbar.make(view, R.string.camposNoVacios , Snackbar.LENGTH_LONG);
            snackbar.setDuration(2000);
            snackbar.show();
        }
        else if(!pass.getText().toString().equals(confirmacionpass.getText().toString())){
            Snackbar snackbar = Snackbar.make(view, R.string.campoIguales , Snackbar.LENGTH_LONG);
            snackbar.setDuration(2000);
            snackbar.show();
           // snackbar.setDuration(10000);
            //snackbar.setAction("Ok", v -> {
           // });
            //snackbar.show();
        }
        else if(pass.length()<9){
            Snackbar snackbar = Snackbar.make(view, R.string.contrasenaCaracteres, Snackbar.LENGTH_LONG);
            snackbar.setDuration(2000);
            snackbar.show();
        }
        else if(!confirmacion){
            Snackbar snackbar = Snackbar.make(view, R.string.terminos, Snackbar.LENGTH_LONG);
            snackbar.setDuration(6000);
            snackbar.show();
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
                                Snackbar snackbar = Snackbar.make(view, R.string.usuarioCreado, Snackbar.LENGTH_LONG);
                                snackbar.setDuration(2000);
                                snackbar.show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                idFirebase = mAuth.getUid();
                                insertarUsuarioBdPropia(idFirebase, nombre.getText().toString(), correo.getText().toString(), puesto.getText().toString(),telefono.getText().toString(), pass.getText().toString());
                                Intent registro = new Intent(getApplicationContext(), Principal.class);
                                setPendingIntent();
                                createNotificacionChanel();
                                createNotification();
                                startActivity(registro);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //este log se puede quitar
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Snackbar snackbar = Snackbar.make(view, R.string.falloAutenticaion, Snackbar.LENGTH_LONG);
                                snackbar.setDuration(2000);
                                snackbar.show();
                                FirebaseUser user = mAuth.getCurrentUser();
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
                Toast.makeText(getApplicationContext(), R.string.accesoRegistro, Toast.LENGTH_SHORT).show(), error -> Toast.makeText(getApplicationContext(), R.string.falloAutenticaion , Toast.LENGTH_SHORT).show()){
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
        Intent VolverLogin = new Intent(this, Login.class );
        startActivity(VolverLogin);
    }
    private void setPendingIntent(){
        Intent intent = new Intent(this, Perfil.class);
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
        builder.setContentText("Cambia lo que quieras de tu Perfil, no te cortes.");
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