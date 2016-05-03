/**
 * Autores: Rubén Gabás Celimendiz, Alejandro Solanas Bonilla
 * NIA: 590738, 647647
 * Fichero: PhotosAdapter.java
 * Fecha: 3/5/2015
 * Funcionalidad: Adapter encargado de gestionar la vista de imagenes
 */

package com.sid.practica5.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sid.practica5.R;
import com.sid.practica5.models.Photo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {
    private final MyWorkerThread handlerThread;
    private LayoutInflater inflater;
    List<Photo> data = Collections.emptyList();
    private Map<String, Bitmap> mapPhotos = new HashMap<>();

    public PhotosAdapter(Context context, List<Photo> data, MyWorkerThread handlerThread) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.handlerThread = handlerThread;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        Photo current = data.get(position);
        holder.title.setText(current.getTitle());
        //holder.icon.setImageResource(R.mipmap.ic_launcher);
        if (mapPhotos.containsKey(current.getUrl())) {
            holder.icon.setImageBitmap(mapPhotos.get(current.getUrl()));
        } else {
            this.handlerThread.queueTask(current.getUrl(), holder);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        public void putPhoto(Bitmap bitmap, String url) {
            mapPhotos.put(url, bitmap);
            icon.setImageBitmap(bitmap);
        }
    }
}
