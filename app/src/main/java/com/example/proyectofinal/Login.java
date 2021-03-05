package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        correo = findViewById(R.id.telefono);
        pass = findViewById(R.id.Pass1Registro);
        mAuth = FirebaseAuth.getInstance();
        String idFirebase = mAuth.getUid();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.musiaca_intro);
        mp.start();
        if(idFirebase!=null){
            Intent i = new Intent(getApplicationContext(), Principal.class);
            startActivity(i);
        }
    }

    protected void onStart() {
        super.onStart();


        // La actividad está a punto de hacerse visible.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registrar (View view)
    {
        Intent registrar = new Intent(this, Registro.class );
        startActivity(registrar);
    }
    public void iniciarSesion (View view){
        if(correo.getText().toString().equals("")|| pass.getText().toString().equals("")){
            Snackbar snackbar = Snackbar.make(view, R.string.camposNoVacios, Snackbar.LENGTH_LONG);
            snackbar.setDuration(2000);
            snackbar.show();
        }
        else{

        mAuth.signInWithEmailAndPassword(correo.getText().toString().trim(), pass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getApplicationContext(), Principal.class);
                            startActivity(i);
                            /*Toast.makeText(getApplicationContext(), "Inicio de sesion correcto.",
                                    Toast.LENGTH_SHORT).show();*/
                            Snackbar snackbar = Snackbar.make(view, R.string.inicioSesionCorrecto, Snackbar.LENGTH_LONG);
                            snackbar.setDuration(2000);
                            snackbar.show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar snackbar = Snackbar.make(view, R.string.falloAutenticaion, Snackbar.LENGTH_LONG);
                            snackbar.setDuration(2000);
                            snackbar.show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
        }
    }
}