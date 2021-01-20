package com.example.attendanceapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingResponse {


    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("site_name")
        @Expose
        public String siteName;
        @SerializedName("site_copy")
        @Expose
        public String siteCopy;
        @SerializedName("site_description")
        @Expose
        public String siteDescription;
        @SerializedName("site_keyword")
        @Expose
        public String siteKeyword;
        @SerializedName("site_email")
        @Expose
        public String siteEmail;
        @SerializedName("site_phone")
        @Expose
        public String sitePhone;
        @SerializedName("site_address")
        @Expose
        public String siteAddress;
        @SerializedName("hours")
        @Expose
        public String hours;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("site_logo")
        @Expose
        public String siteLogo;
        @SerializedName("site_icon")
        @Expose
        public String siteIcon;
        @SerializedName("webviewLink")
        @Expose
        public String webviewLink;

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
    public String options;

}