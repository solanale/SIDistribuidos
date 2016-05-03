/**
 * Autores: Rubén Gabás Celimendiz, Alejandro Solanas Bonilla
 * NIA: 590738, 647647
 * Fichero: GetPhotoAdapter.java
 * Fecha: 3/5/2015
 * Funcionalidad: Adapter que recupera la imagen del servicio a partir de la url
 */

package com.sid.practica6.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.sid.practica6.MainActivity;
import com.sid.practica6.models.Photo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPhotoAdapter extends AsyncTask<String, Void, Integer> {

    private final Photo photo;
    private MainActivity mActivity = null;
    private Bitmap bitmap;

    public GetPhotoAdapter(MainActivity activity, Photo photo) {
        attach(activity);
        this.photo = photo;
    }

    @Override
    protected Integer doInBackground(String... params) {

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(photo.getUrl()).openConnection();
            bitmap = BitmapFactory.decodeStream((InputStream) connection.getContent());
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer integer)
    {

        if (mActivity==null) {
            Log.i("MyAsyncTask", "Me salto onPostExecute() -- no hay nueva activity");
        }
        else {
            this.mActivity.setViewContent(this.bitmap, this.photo.getTitle());
        }

    }

    void detach() {
        this.mActivity=null;
    }

    void attach(MainActivity activity) {
        this.mActivity = activity;
    }

}
