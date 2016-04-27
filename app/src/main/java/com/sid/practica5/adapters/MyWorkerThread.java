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

/**
 * Created by phyrion on 26/04/16.
 */
public class MyWorkerThread extends HandlerThread {
    private Handler mWorkerHandler;
    private Handler mResponseHandler;
    private static final String TAG = MyWorkerThread.class.getSimpleName();
    private Map<ImageView, String> mRequestMap =  new HashMap<>();
    private Callback mCallback;

    public interface Callback {
        void onImageDownloaded(ImageView imageView, Bitmap bitmap, int side);
    }

    public MyWorkerThread(Handler mResponseHandler, Callback callback) {
        super(TAG);
        this.mResponseHandler = mResponseHandler;
        this.mCallback = callback;
    }

    public void queueTask(String url, int side, ImageView imageView) {
        mRequestMap.put(imageView, url);
        Log.i(TAG, url + " added to the queue");
        mWorkerHandler.obtainMessage(side, imageView).sendToTarget();
    }

    public void prepareHandler() {
        mWorkerHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                ImageView imageView = (ImageView) msg.obj;
                //String side = msg.what == MainActivity.LEFT
                Log.i(TAG, String.format("Procesing %s", mRequestMap.get(imageView)));
                handleRequest(imageView, msg.what);
                msg.recycle();
                return true;
            }

        });
    }

    private void handleRequest(final ImageView imageView, final int side) {
        //String url = mRequestMap.get(imageView);
        try {
            URL url = new URL(Constants.GET_PHOTOS_URL);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();
            System.err.println("aaaaaaa-----" + httpUrlConnection.getResponseCode());
            //final Bitmap bitmap = BitmapFactory.decodeStream((connection.getInputStream()));
            mRequestMap.remove(imageView);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onImageDownloaded(imageView, null, side);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

    }

}
