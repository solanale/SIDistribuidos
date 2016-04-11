package com.sid.practica4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.sid.practica4.adapters.GetPhotosAdapter;
import com.sid.practica4.models.Photo;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            checkNetworkConnection();
            new GetPhotosAdapter(this).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }

    public void showPhotos(List<Photo> photos) {
        TextView texto = (TextView) findViewById(R.id.textoTitulos);
        texto.setText("");
        for (Photo photo : photos) {
            texto.append(photo.getTitle() + "\n\n");

        }
    }

    /**
     * Comprueba que existe conexión a internet
     *
     * @throws IOException
     */
    private void checkNetworkConnection() throws IOException {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null | (networkInfo != null && !networkInfo.isConnected())) {
            throw new IOException();
        }
        System.err.println("SSSSS");
    }
}
