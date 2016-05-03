/**
 * Autores: Rubén Gabás Celimendiz, Alejandro Solanas Bonilla
 * NIA: 590738, 647647
 * Fichero: MyWorkerThread.java
 * Fecha: 3/5/2015
 * Funcionalidad: Adapter encargado de hacer las peticiones y descarga de imagenes
 */

package com.sid.practica5.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.sid.practica5.MainActivity;
import com.sid.practica5.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyWorkerThread extends HandlerThread {
    private Handler mWorkerHandler;
    private Handler mResponseHandler;
    private static final String TAG = MyWorkerThread.class.getSimpleName();
    private Map<String, PhotosAdapter.MyViewHolder> mRequestMap =  new HashMap<>();
    private Callback mCallback;

    public interface Callback {
        void onImageDownloaded(PhotosAdapter.MyViewHolder holder, Bitmap bitmap, String url);
    }

    public MyWorkerThread(Handler mResponseHandler, Callback callback) {
        super(TAG);
        this.mResponseHandler = mResponseHandler;
        this.mCallback = callback;
    }

    public void queueTask(String url, PhotosAdapter.MyViewHolder holder) {
        mRequestMap.put(url, holder);
        Log.i(TAG, url + " added to the queue");
        mWorkerHandler.obtainMessage(MyWorkerThread.NORM_PRIORITY, url).sendToTarget();
    }

    public void prepareHandler() {
        mWorkerHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String url = (String) msg.obj;

                Log.i(TAG, String.format("Procesing %s", mRequestMap.get(url)));
                handleRequest(url, msg.what);
                return true;
            }

        });
    }

    private void handleRequest(final String url, final int side) {
        final PhotosAdapter.MyViewHolder holder = mRequestMap.get(url);
        try {
            //URL url = new URL(Constants.GET_PHOTOS_URL);

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            final Bitmap bitmap = BitmapFactory .decodeStream((InputStream) connection.getContent());
            mRequestMap.remove(url);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onImageDownloaded(holder, bitmap, url);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

    }

}
