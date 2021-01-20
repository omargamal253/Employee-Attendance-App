package com.example.attendanceapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageResponse {


    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("content")
        @Expose
        public String content;

    }

    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("api_token")
    @Expose
    public Object apiToken;
    @SerializedName("options")
    @Expose
    public Object options;

}