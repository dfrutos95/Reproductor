package com.example.reproductor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    ListView miLista;
    private String nombre, artista;
    private int imagen, pista;
    private String[] canciones = new String[]{"New Born", "999", "Unholy"}; //Datos
    private String[] artistas = new String[]{"Muse", "Selena Gomez & Camilo", "Sam Smith & Kim Petras",
            }; //Datos

    private int imagenes[] = new int[]{R.drawable.newborn, R.drawable.nueve, R.drawable.unholy,
            R.drawable.cuenca, R.drawable.albacete, R.drawable.talavera};

    private int pistas[] = new int[]{R.raw.newborn, R.raw.nueve, R.raw.unholy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<Cancion> listaCanciones = new ArrayList<Cancion>();
        for (int i = 0; i < canciones.length; i++) {
            Cancion c = new Cancion(canciones[i], artistas[i], imagenes[i], pistas[i]);
            listaCanciones.add(c);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        miLista = findViewById(R.id.miLista); //IU -> se va a "inflar" una fila por cada ciudad a través de mi_fila_personalizada.xml


        MiAdaptadorPersonalizado adapter =
                new MiAdaptadorPersonalizado(this, R.layout.mi_fila_personalizada, listaCanciones);

        //enfuchar adaptador a la vista
        miLista.setAdapter(adapter);
        miLista.setOnItemClickListener(this);
    }
    public void onClick(View view){
        openReproductor();
    }

    public void openReproductor(){

        Intent intentReproductor = new Intent(this, Reproductor.class);
        intentReproductor.putExtra("nombre", nombre);
        intentReproductor.putExtra("artista", artista);
        intentReproductor.putExtra("imagen", imagen);
        intentReproductor.putExtra("pista", pista);
        startActivity(intentReproductor);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //acción al seleccionar un elemento
        Cancion c = (Cancion) parent.getAdapter().getItem(position);
        //no me esta cogiendo bien la informacion
        //Cancion cancion = (Cancion) c;
        nombre=c.getNombre();
        artista=c.getArtista();
        imagen=c.getImagen();
        pista=c.getPista();
        openReproductor();
    }

    public class MiAdaptadorPersonalizado extends ArrayAdapter<Cancion> {
        private int mResource;
        private ArrayList<Cancion> misCanciones;

        public MiAdaptadorPersonalizado(@NonNull Context context, int resource, @NonNull List<Cancion> objects) {
            super(context, resource, objects);
            mResource = resource;
            misCanciones = (ArrayList<Cancion>) objects;
        }


        //Este método sólo es necesario reescribirlo si el adaptador se enchufa a un spinner
        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        private View crearFila(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Este método es invocado tantas veces como "filas" se pinten en la actividad

            LayoutInflater miInflador = getLayoutInflater();
            View miFila = miInflador.inflate(mResource, parent, false);

            TextView txtDescripcion = miFila.findViewById(R.id.txtDescripcion);
            TextView txtCiudad = miFila.findViewById(R.id.txtCiudad);
            ImageView imgCiudad = miFila.findViewById(R.id.imgCiudad);

            txtDescripcion.setText(misCanciones.get(position).artista);
            txtCiudad.setText(misCanciones.get(position).nombre);
            imgCiudad.setImageResource(misCanciones.get(position).imagen);
            Log.d("RDT", "creada la fila" + position);
            return miFila;
        }
    }

    public class Cancion{
        String nombre, artista;
        int imagen, pista;

        public Cancion(String nombre, String artista, int imagen, int pista){
            this.nombre=nombre;
            this.artista=artista;
            this.imagen=imagen;
            this.pista=pista;
        }

        public String getNombre(){
            return this.nombre;
        }
        public String getArtista(){
            return this.artista;
        }
        public int getImagen(){
            return this.imagen;
        }
        public int getPista(){
            return this.pista;
        }
    }
    public class Ciudad {
        String nombre;
        String descripcion;
        int imagen;

        public Ciudad(String nombre, String descripcion, int imagen) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.imagen = imagen;
        }

    }

}