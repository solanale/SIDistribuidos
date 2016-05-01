package com.sid.practica5;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.sid.practica5.adapters.GetPhotosAdapter;
import com.sid.practica5.adapters.MyWorkerThread;
import com.sid.practica5.adapters.PhotosAdapter;
import com.sid.practica5.models.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyWorkerThread.Callback{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotosAdapter pa;
    private GetPhotosAdapter photosAdapter;
    private MyWorkerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            checkNetworkConnection();
            new GetPhotosAdapter(this).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        handlerThread = new MyWorkerThread(new Handler(), this);
        handlerThread.start();
        handlerThread.prepareHandler();

    }

    public void showPhotos(List<Photo> photos) {
//        TextView texto = (TextView) findViewById(R.id.textoTitulos);
//        texto.setText("");
//        for (Photo photo : photos) {
//            texto.append(photo.getTitle() + "\n\n");
//
//        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pa = new PhotosAdapter(this, photos, handlerThread);
        System.out.println("aa->" + pa);
        recyclerView.setAdapter(pa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        String[] urls = new String[]{"http://developer.android.com/design/media/principles_delight.png", "http://developer.android.com/design/media/principles_real_objects.png", "http://developer.android.com/design/media/principles_make_it_mine.png", "http://developer.android.com/design/media/principles_get_to_know_me.png"};
//
//        for (String url : urls) {
//            handlerThread.queueTask(url, 2, new ImageView(this));
//        }
    }

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


    @Override
    public void onImageDownloaded(PhotosAdapter.MyViewHolder holder, Bitmap bitmap, String url) {
        holder.putPhoto(bitmap, url);
    }

}
