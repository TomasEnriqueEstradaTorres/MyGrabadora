package com.example.mygrabadora;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class GrabarVideo extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private Button reproducir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_video);

        reproducir = findViewById(R.id.buttonReproducirGrabado);

        //Servira pra pedir los permisos de escritura y uso de la camara para poder grabar el video
        if (ContextCompat.checkSelfPermission(GrabarVideo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GrabarVideo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GrabarVideo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrabarVideo.this, ReproducirVideo.class);
                File direccionEnviada = getFilePath(); // se obtiene la direccion del ultimo video grabado
                String dato = direccionEnviada.getAbsolutePath();// octiene la ruta absoluta de la ubicacion del video
                intent.putExtra("direccion", dato);  // Se envia el dato a la otra ventana para reproducir el video
                startActivity(intent);
            }
        });

    }

    public void TomarVideo(View view) {
        Intent grabarVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE); // llama a ca camara del movil
        File video_file = getFilePath();  // Obtiene el nobre y direccion donde se guardara el video
        //Uri video_uri = Uri.fromFile(video_file); // ya no sirve a partir de SDK 23
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",video_file);
        grabarVideo.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        grabarVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1); // Esto es para la calidad del video, 1 es maxima calidad

        if (grabarVideo.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(grabarVideo, REQUEST_VIDEO_CAPTURE); // inicia el programa para grabar video
        }
    }

    // con este metodo se creara la carpeta de almacenamiento, el nombre de archivo y el formato del video
    public File getFilePath(){
        File folder = new File("sdcard/Actividad5_grabacionVideo"); // sera la direccion donde se almacena el video
        // aqui se verifica si la carpeta existe y no si es asi se crea la carpeta donde almacenamos el video creado
        if (!folder.exists()){
            folder.mkdir();
        }
        File video_file = new File(folder, "video_grabado.mp4"); // sirve para poner un nombre por defecto
        /**el codigo de abajo comentado genera nombre aleatorio para el video pero esto no permite poder reproducirlo
         * cuando enviamos la ubicacion por medio de un intent para poder reproducirlo en otra ventana, solo funciona
         * si se pone un nombre por defecto     */
        //String nombreVariado = (System.currentTimeMillis()/1000) + ".mp4";// Nombre y formato de video
        //File video_file = new File(folder, nombreVariado);// asi el nombre varia cada vez que se crea un nuevo video
        return video_file;
    }

}
