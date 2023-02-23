package com.example.reproductor;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

public class Reproductor extends AppCompatActivity implements MediaController.MediaPlayerControl{

    MediaController mc;
    MediaPlayer mp;
    Handler h;

    ImageView caratula;
    TextView tvNombre, tvArtista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        mc= new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(findViewById(R.id.constraintLayout));
        //mp=MediaPlayer.create(this,R.raw.newborn);
        Bundle detalles = getIntent().getExtras();
        String nombre, artista;
        int imagen, pista;
        nombre= detalles.getString("nombre");
        artista=detalles.getString("artista");
        imagen=detalles.getInt("imagen");
        pista=detalles.getInt("pista");
        h=new Handler();
        caratula=findViewById(R.id.imageView);
        caratula.setImageResource(imagen);
        tvNombre=findViewById(R.id.nombre);
        tvArtista=findViewById(R.id.artista);
        tvNombre.setText(nombre);
        tvArtista.setText(artista);
        mp=MediaPlayer.create(this, pista);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mc.show(0);
                    }
                });
            }
        });


    }


    @Override
    public void start() {
        if(!mp.isPlaying())
            mp.start();
    }

    @Override
    public void pause() {
        if(mp.isPlaying())
            mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mp.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mp.getAudioSessionId();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
            if(!mc.isShowing())
                mc.show(0);
            else
                mc.hide();
        return false;
    }
}