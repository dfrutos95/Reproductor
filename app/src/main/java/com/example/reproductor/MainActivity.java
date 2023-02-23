package com.example.reproductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText usuario, contrasena;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        login = findViewById(R.id.login);

    }

   public void onClick(View view){
        if(login.isPressed()){
            openLista();
        }
    }

    public void openLista(){
        Intent intentLista = new Intent(this, Lista.class);
        startActivity(intentLista);
    }
}