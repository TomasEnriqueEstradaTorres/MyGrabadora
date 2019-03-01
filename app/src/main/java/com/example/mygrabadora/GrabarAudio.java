package com.example.mygrabadora;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GrabarAudio extends AppCompatActivity implements MediaController.MediaPlayerControl{

    private ToggleButton inicioPara; // boton para iniciar y para la grabacion
    private MediaRecorder grabacion; // sirve para poder grabar audio
    private String archivoSalida = null; //Es la direccion de donde se guardara el sonido y su nombre con formato

    private MediaPlayer mediaPlayer; // servira pra reproducir sonidos largos, en este caso la gravacion del audio
    private MediaController mediaController; // servira para controlar la reproduccion del audio por medio de una barra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_audio);

        inicioPara = findViewById(R.id.toggleButtonInicioParar); // sera para el inicio y parar la grabacion
        // con esto pondremos los controles para el audio por defecto
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.soundSonido)); // indica en que pantalla aparece la barra, el el layout activity

        //Servira pra pedir los permisos de escritura en la memoria y para poder usar el micro del movil
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GrabarAudio.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

    //Metodo para poder grabar audio
    public void Recorder(View view){
        if(grabacion == null){// verifica si no es nula
            //Aqui se guarda en la direccion externa mas el nombre y formato
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + (System.currentTimeMillis()/1000) + ".mp3";
            //System.out.println("===> VER AQUI: " + archivoSalida);
            grabacion = new MediaRecorder(); //Se declara un objeto de la clase MediaRecorder para grabar audio
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);//  definimos el micrófono como fuente de audio
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//especificando que el archivo será almacenado con la especificación THREE_GPP
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);//Especificamos el codec a emplear llamando al método setAudioEncode
            grabacion.setOutputFile(archivoSalida);// le indicamos el archivo donde debe almacenarse la grabación

            try{
                //Llamamos al método prepare y finalmente al método start para comenzar la grabación
                grabacion.prepare();
                grabacion.start();
            } catch (IOException e){
            }

            inicioPara.setBackgroundResource(R.drawable.microfono_on); // asigna la imagen de encendido
            Toast toast = Toast.makeText(this, "Grabando Audio", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();

        } else if(grabacion != null){
            //Llamamos a los métodos stop de la clase MediaRecorder y liberamos los recursos consumidos llamando a release
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            inicioPara.setBackgroundResource(R.drawable.microfono_off); // asigna la imagen de apagado
            Toast toast = Toast.makeText(this, "Grabación finalizada", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void reproducir(View view) {
        mediaPlayer = new MediaPlayer();
        try {
            //Inicia la reproducion del ultimo audio grabado
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();
        } catch (IOException e){
        }

        mediaPlayer.start();// inicia la reproduccion del audio grabado
        Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_LONG).show();
    }

    //----------------------------------------------------------------------------------------------
    //Este metodo servira para mostrar el seek en la pantalla (barra de audio)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediaController.show();
        return false;
    }
    //----------------------------------------------------------------------------------------------
    // metodos implementados de la interfaz para poder usar el seek(barra de audio)

    @Override
    public void start() {
        //Es para iniciar la reproduccion del audio
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        // Cuando el usuario toca el botón de pausa
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        // Vuelve la duración de la pista de audio
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        // Vuelve la posición actual en la pista de audio
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        // Para ir a una posición de la pista
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        // Vuelve cierto cuando se está reproduciendo audio
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        // este hay que cambiarlo a true, por defecto viene en false
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        // cambiar a true porque asi se puede controlar por medio de la barra de audio
        return true;
    }

    @Override
    public boolean canSeekForward() {
        // cambiar a true porque asi se puede controlar por medio de la barra de audio
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
