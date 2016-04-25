package com.sid.practica5.adapters;

import android.os.AsyncTask;

import com.sid.practica5.MainActivity;
import com.sid.practica5.models.Photo;
import com.sid.practica5.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phyrion on 4/04/16.
 */
public class GetPhotosAdapter extends AsyncTask<String, String, String> {
    private static final String CODIFICATION = "UTF-8";

    private List photos;
    private MainActivity mainActivity;
    private StringBuilder response = new StringBuilder();
    private String responseMessage;
    private int responseCode = -1;

    public GetPhotosAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }



    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(Constants.GET_PHOTOS_URL);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                getResponse(httpUrlConnection.getInputStream());
                getImagesFromJSON(response.toString());
            } else if (status == HttpURLConnection.HTTP_FORBIDDEN) {
                getResponse(httpUrlConnection.getErrorStream());
                if (!getMessageFromJson(response.toString())) {
                    responseCode = getErrorFromJson(response.toString());
                    responseMessage = getErrorMessageFromHson(response.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Consigue la respuesta del servidor
     *
     * @param inputStream de la conexión con el servidor
     */
    private void getResponse(InputStream inputStream) {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Consigue el código de error desde un JSON
     *
     * @param json a parsear
     * @return código de error
     */
    private boolean getMessageFromJson(String json) {
        try {
            JSONObject jsonOb = new JSONObject(json);
            responseMessage = jsonOb.getString("message");

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Consigue el código de error de un JSON
     *
     * @param json a parsear
     * @return código de error
     */
    private int getErrorFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            return jsonObject.getInt(Constants.ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Constants.UNKOWN_ERROR;
    }


    private String getErrorMessageFromHson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            return jsonObject.getString("mensaje");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //mainActivity.showPhotos(photos);
    }

    private List<Photo> getImagesFromJSON(String json) {
        photos = new ArrayList<>();
        try {
            JSONObject o = new JSONObject(json);
            JSONObject o2 = new JSONObject(o.getString("photos"));
            JSONArray images = o2.getJSONArray("photo");
            for (int i = 0; i < images.length(); i++) {
                JSONObject jsonOb = new JSONObject(images.get(i).toString());
                photos.add(new Photo(jsonOb.getString("id"), jsonOb.getString("owner"), jsonOb.getString("secret"),
                                    jsonOb.getString("server"), jsonOb.getString("farm"), jsonOb.getString("title"),
                                    Boolean.parseBoolean(jsonOb.getString("ispublic")), Boolean.parseBoolean(jsonOb.getString("isfriend")),
                                    Boolean.parseBoolean(jsonOb.getString("isfamily"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return photos;
    }

}