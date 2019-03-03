package com.example.mygrabadora;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;

public class ReproducirVideo extends AppCompatActivity {

    private VideoView visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir_video);

        visor = findViewById(R.id.videoStandar);

        Intent intent = getIntent();// obtedra el dato de la ubicacion del video para poder reproducirlo
        String recibido = intent.getStringExtra("direccion");// se extrae el dato

        File file = new File(recibido);
        if (file.exists()){// verifica que exiate el archvivo
            visor = findViewById(R.id.videoStandar);// servira para cargar el
            visor.setVideoURI(Uri.fromFile(new File(String.valueOf(recibido))));// ruta absoluta del video
            visor.setMediaController(new MediaController(this));// controlador del video para para o pausar
            visor.start();
            System.out.println("===> RECIBIDO: " + recibido);
        }else{
            Toast toast = Toast.makeText(this, "No se encontro el video", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
            System.out.println("===> NO EXISTE: " + recibido);
        }

    }

}
/**NOTAS
 *
 * setVideoPath ==>> es para cargar un video desde SDCARD ==> videoView2.setVideoPath("/sdcard/test2.3gp");
 * setVideoURI ==>> es solo para transmisión de video desde internet. ==> videoView1.setVideoUri("http://www.mysite.com/videos/myvideo.3gp");
 *
 * Uri.fromFile(file).toString() ==>> solo devuelve algo como el file///storage/emulated/0/* que es una ruta absoluta
 * simple de un archivo en la sdcard pero con el file// prefijo (esquema)
 *
 *
 */