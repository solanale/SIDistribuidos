/**
 * Autores: Rubén Gabás Celimendiz, Alejandro Solanas Bonilla
 * NIA: 590738, 647647
 * Fichero: Photo.java
 * Fecha: 3/5/2015
 * Funcionalidad: Creación y definición del objeto foto, con sus parametros y metodos correspondientes
 */

package com.sid.practica5.models;


public class Photo {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private String farm;
    private String title;
    private boolean isPublic;
    private boolean isFriend;
    private boolean isFamily;
    private String url;

    /**
     * Constructor de la clase Photo
     * @param id
     * @param owner
     * @param secret
     * @param server
     * @param farm
     * @param title
     * @param isPublic
     * @param isFriend
     * @param isFamily
     */
    public Photo(String id, String owner, String secret, String server,
                 String farm, String title, boolean isPublic, boolean isFriend, boolean isFamily) {
        this.id = id;
        this.isFamily = isFamily;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.isPublic = isPublic;
        this.isFriend = isFriend;
        this.url = "https://farm" + farm + ".staticflickr.com/" +
                server + "/" + id+ "_" + secret + "_m.jpg";
    }

    /** Getters & Setters **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public boolean isFamily() {
        return isFamily;
    }

    public void setIsFamily(boolean isFamily) {
        this.isFamily = isFamily;
    }

    public String getUrl() {
        return url;
    }
}
