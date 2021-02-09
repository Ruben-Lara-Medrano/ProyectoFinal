package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText pass;
    private EditText confirmacionpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        correo = findViewById(R.id.EmailRegistro);
        pass = findViewById(R.id.Pass1Registro);
        confirmacionpass = findViewById(R.id.Pass2Registro);
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void registrarUsuario (View view){

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
                                Intent registro = new Intent(getApplicationContext(), principal.class);
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
        else{
            Toast.makeText(this,"Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
        }


    }
    public void VolverLogin (View view)
    {
        Intent VolverLogin = new Intent(this, login.class );
        startActivity(VolverLogin);
    }
}