/**
 * Autores: Rubén Gabás Celimendiz, Alejandro Solanas Bonilla
 * NIA: 590738, 647647
 * Fichero: MainActivity.java
 * Fecha: 3/5/2015
 * Funcionalidad: Modulo principal del sistema, establece la conexion con el servidor haciendo las
 *                peticiones a través del adapter basadas en una localizacion
 */

package com.sid.practica6;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sid.practica6.adapters.GetPhotosAdapter;
import com.sid.practica6.models.Photo;
import com.sid.practica6.util.Constants;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mCliente;
    private LocationRequest mLocationRequest;
    private Button btnCargar;
    private Location location;
    private ImageView ivPhoto;
    private MainActivity main = this;
    private TextView txTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCliente = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mCliente.connect();


        setContentView(R.layout.activity_main);
        btnCargar = (Button) findViewById(R.id.btnCargar);
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location == null) {
                    Toast.makeText(getApplicationContext(), "No se ha actualizado la posición del gps", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println(location.toString());
                    try {
                        checkNetworkConnection();
                        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + Constants.API_KEY + "&lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&format=json&nojsoncallback=1";
                        new GetPhotosAdapter(main).execute(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        txTitle = (TextView) findViewById(R.id.txTitle);
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
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mCliente, mLocationRequest, this);
    }



    @Override
    public void onConnectionSuspended(int i) {
        Log.i("pena", "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("PEna", "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Location", "La localización ha cambiado");
        this.location = location;
    }

    public void setViewContent(Bitmap bitmap, String title) {
        ivPhoto.setImageBitmap(bitmap);
        txTitle.setText(title);
    }
}
