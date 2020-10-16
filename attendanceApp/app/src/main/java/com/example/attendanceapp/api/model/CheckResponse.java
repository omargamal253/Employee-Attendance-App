package com.example.attendanceapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckResponse {

    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("job")
        @Expose
        public String job;
        @SerializedName("job_id")
        @Expose
        public String jobId;
        @SerializedName("salary")
        @Expose
        public String salary;
        @SerializedName("restricted")
        @Expose
        public String restricted;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("admin")
        @Expose
        public String admin;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("cate_id")
        @Expose
        public String cateId;
        @SerializedName("branche_id")
        @Expose
        public String brancheId;
        @SerializedName("time_id")
        @Expose
        public String timeId;
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("status_code")
        @Expose
        public String statusCode;
        @SerializedName("block")
        @Expose
        public String block;

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