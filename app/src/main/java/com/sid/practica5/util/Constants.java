package com.sid.practica5.util;

/**
 * Created by phyrion on 4/04/16.
 */
public class Constants {
    private static final String API_KEY = "8122585ef187d9eda67ded83c2245f36";
    public static final String GET_PHOTOS_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=" + API_KEY + "&format=json&nojsoncallback=1";
    public static final String ERROR = "Fallo de algo";
    public static final int UNKOWN_ERROR = -1;
}
