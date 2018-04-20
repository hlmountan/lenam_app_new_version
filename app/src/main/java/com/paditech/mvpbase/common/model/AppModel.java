package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hung on 1/25/2018.
 */

public class AppModel {

    @SerializedName("_source")
    private SourceBean source;

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean source) {
        this.source = source;
    }


    public static class SourceBean {

        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(String thumbnails) {
            this.thumbnails = thumbnails;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getOfferby() {
            return offerby;
        }

        public void setOfferby(String offerby) {
            this.offerby = offerby;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        @SerializedName("thumbnails")
        private String thumbnails;

        @SerializedName("version")
        private String version;

        @SerializedName("offerby")
        private String offerby;

        @SerializedName("appid")
        private String appid;
        @SerializedName("price")
        private float price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @SerializedName("title")
        private String title;


        public List<Float> getAll_price() {
            return all_price;
        }

        public void setAll_price(List<Float> all_price) {
            this.all_price = all_price;
        }

        @SerializedName("all_price")
        private List<Float> all_price;


        public float getPrice() {
            return price;
        }

        public String getCover() {
            return cover;
        }

        @SerializedName("cover")
        private String cover;


        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @SerializedName("thumbnail")
        private String thumbnail;


        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }



        private ArrayList<ArrayList<String>> screenShot = null;

        public ArrayList<ArrayList<String>> getScreenShot() {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ArrayList<String>>>(){}.getType();
            screenShot = gson.fromJson(thumbnails,listType);
            return screenShot;
        }

        public void setScreenShot(ArrayList<ArrayList<String>> screenShot) {
            this.screenShot = screenShot;
        }







    }
}
