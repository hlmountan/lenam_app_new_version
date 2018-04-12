package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

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


        @SerializedName("apk_amz")
        private int apk_amz;

        public int getApk_amz() {
            return apk_amz;
        }

        public void setApk_amz(int apk_amz) {
            this.apk_amz = apk_amz;
        }

        @SerializedName("total_reviews")
        private int total_reviews;
        @SerializedName("star")
        private float star;

        @SerializedName("appid")
        private String appid;
        @SerializedName("price")
        private float price;
        @SerializedName("drop_percent")
        private int dropPercent;
        @SerializedName("drop_value")
        private float dropValue;
        @SerializedName("rate_total")
        private int rateTotal;
        @SerializedName("title")
        private String title;
        @SerializedName("offerby")
        private String offerby;
        @SerializedName("tag")
        private String tag;
        @SerializedName("category")
        private String category;
        @SerializedName("cover")
        private String cover;
        @SerializedName("score")
        private double score;
        @SerializedName("size")
        private String size;
        @SerializedName("installs")
        private int installs;
        @SerializedName("version")
        private String version;
        @SerializedName("thumbnails")
        private String thumbnails;
        @SerializedName("description")
        private String description;
        @SerializedName("requireandroid")
        private String requireandroid;
        @SerializedName("all_price")
        private ArrayList allPrice;
        @SerializedName("email_dev")
        private String email_dev;
        @SerializedName("linksupport")
        private String linksupport;
        @SerializedName("domain")
        private String domain;
        @SerializedName("subdomain")
        private String subdomain;
        @SerializedName("requires")
        private String requires;
        @SerializedName("different_prices")
        private float different_prices;
        @SerializedName("gplay_price")
        private float gplay_price;

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

        public int getTotal_reviews() {
            return total_reviews;
        }

        public void setTotal_reviews(int total_reviews) {
            this.total_reviews = total_reviews;
        }

        public float getStar() {
            return star;
        }

        public void setStar(float star) {
            this.star = star;
        }

        public String getEmail_dev() {
            return email_dev;
        }

        public void setEmail_dev(String email_dev) {
            this.email_dev = email_dev;
        }

        public String getLinksupport() {
            return linksupport;
        }

        public void setLinksupport(String linksupport) {
            this.linksupport = linksupport;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getSubdomain() {
            return subdomain;
        }

        public void setSubdomain(String subdomain) {
            this.subdomain = subdomain;
        }

        public String getRequires() {
            return requires;
        }

        public void setRequires(String requires) {
            this.requires = requires;
        }

        public float getDifferent_prices() {
            return different_prices;
        }

        public void setDifferent_prices(float different_prices) {
            this.different_prices = different_prices;
        }

        public float getGplay_price() {
            return gplay_price;
        }

        public void setGplay_price(float gplay_price) {
            this.gplay_price = gplay_price;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDropPercent() {
            return dropPercent;
        }

        public void setDropPercent(int dropPercent) {
            this.dropPercent = dropPercent;
        }

        public float getDropValue() {
            return dropValue;
        }

        public void setDropValue(float dropValue) {
            this.dropValue = dropValue;
        }

        public int getRateTotal() {
            return rateTotal;
        }

        public void setRateTotal(int rateTotal) {
            this.rateTotal = rateTotal;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOfferby() {
            return offerby;
        }

        public void setOfferby(String offerby) {
            this.offerby = offerby;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getInstalls() {
            return installs;
        }

        public void setInstalls(int installs) {
            this.installs = installs;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getThumbnails() {
            return thumbnails;
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
        public void setThumbnails(String thumbnails) {
            this.thumbnails = thumbnails;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRequireandroid() {
            return requireandroid;
        }

        public void setRequireandroid(String requireandroid) {
            this.requireandroid = requireandroid;
        }


        public ArrayList getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(ArrayList allPrice) {
            this.allPrice = allPrice;
        }


    }
}
