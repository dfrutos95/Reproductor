package com.example.reproductor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import java.util.Arrays;

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
/**
    //pruebo a poner un fondo dependiendo del color de la imagen
        Bitmap icon = BitmapFactory.decodeResource(getResources(),imagen);
        int[] mainColors = getMainColorsFromBitmap(icon, 1);
        int mainColor=mainColors[1];

// set the background color of the current activity
        getWindow().setBackgroundDrawable(new ColorDrawable(mainColor));

*/
    }


    @Override
    public void start() {
        if(!mp.isPlaying()) {
            //mp.stop();
            mp.start();
        }
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
    public int[] getMainColorsFromBitmap(Bitmap bitmap, int numColors) {
        // Convert the bitmap into an array of pixels
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        // Initialize an array to hold the color frequencies
        int[] colorFreq = new int[256];

        // Loop through the pixels to count the frequency of each color
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            int alpha = Color.alpha(color);
            int quantizedColor = Color.argb(alpha, red, green, blue);
            colorFreq[quantizedColor]++;
        }

        // Sort the color frequencies in descending order and select the top N colors as the main colors
        int[] sortedColors = Arrays.copyOf(colorFreq, colorFreq.length);
        Arrays.sort(sortedColors);
        int[] mainColors = new int[numColors];
        for (int k = 0; k < numColors; k++) {
            int color = sortedColors[255 - k];
            for (int j = 0; j < colorFreq.length; j++) {
                if (color == colorFreq[j]) {
                    mainColors[k] = j;
                    break;
                }
            }
        }

        // Return the main colors as integers in the format 0xAARRGGBB
        return mainColors;
    }
}