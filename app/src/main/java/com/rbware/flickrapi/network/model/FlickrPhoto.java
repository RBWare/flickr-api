package com.rbware.flickrapi.network.model;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */


import android.os.Parcel;
import android.os.Parcelable;

public class FlickrPhoto implements Parcelable {

    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;

    public static final Parcelable.Creator<FlickrPhoto> CREATOR
            = new Parcelable.Creator<FlickrPhoto>() {
        public FlickrPhoto createFromParcel(Parcel in) {
            return new FlickrPhoto(in);
        }

        public FlickrPhoto[] newArray(int size) {
            return new FlickrPhoto[size];
        }
    };


    private FlickrPhoto(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readInt();
        title = in.readString();
        ispublic = in.readInt();
        isfriend = in.readInt();
        isfamily = in.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlickrPhoto that = (FlickrPhoto) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(owner);
        out.writeString(secret);
        out.writeString(server);
        out.writeInt(farm);
        out.writeString(title);
        out.writeInt(ispublic);
        out.writeInt(isfriend);
        out.writeInt(isfamily);
    }

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

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsPublic() {
        return ispublic;
    }

    public void setIsPublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public int getIsFriend() {
        return isfriend;
    }

    public void setIsFriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsFamily() {
        return isfamily;
    }

    public void setIsFamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public String getImageUrl() {

        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg

        try {
            StringBuilder builder = new StringBuilder();
            builder.append("https://farm");
            builder.append(this.farm);
            builder.append(".staticflickr.com/");
            builder.append(this.server);
            builder.append("/");

            builder.append(this.id);
            builder.append("_");
            builder.append(this.secret);
            builder.append("_h");
            builder.append(".jpg");


            return builder.toString();
        } catch (Exception e){
            return "Oops";
        }
    }

}
