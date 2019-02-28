package com.example.mygrabadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void onResume() {
        super.onResume();

    }

    public void onRestart() {
        super.onRestart();

    }


    public void onPause() {
        super.onPause();

    }

    public void onStop() {
        super.onStop();

    }

    public void onDestroy() {
        super.onDestroy();

        /*
        Toast toast = Toast.makeText(this, "Ventana 1 Estoy en onDestroy", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 1400);
        toast.show();
        Log.i(AVISO, getLocalClassName() + ".onDestroy;"); */
    }

}
