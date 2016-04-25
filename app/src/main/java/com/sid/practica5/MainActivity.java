package com.sid.practica5;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sid.practica5.adapters.GetPhotosAdapter;
import com.sid.practica5.adapters.PhotosAdapter;
import com.sid.practica5.models.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotosAdapter pa;
    private GetPhotosAdapter photosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pa = new PhotosAdapter(this, getData());
        System.out.println("aa->" + pa);
        recyclerView.setAdapter(pa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            checkNetworkConnection();
            new GetPhotosAdapter(this).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void showPhotos(List<Photo> photos) {
        TextView texto = (TextView) findViewById(R.id.textoTitulos);
        texto.setText("");
        for (Photo photo : photos) {
            texto.append(photo.getTitle() + "\n\n");

        }
    }*/

    public static List<Photo> getData() {
        List<Photo> data = new ArrayList<>();
        for (int i = 0 ; i <= 10 ; i++) {
            Photo current = new Photo("aa","ee", "ii", "oo", "uu", "bb"+i, false,false,false);
            data.add(current);
        }
        return data;
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
    }
}
