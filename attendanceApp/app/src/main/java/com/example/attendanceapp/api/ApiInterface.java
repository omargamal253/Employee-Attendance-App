package com.example.attendanceapp.api;

import com.example.attendanceapp.api.model.CheckResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {



    @FormUrlEncoded
    @POST("attends/v3/api/checkCode")
    public Call<CheckResponse> CheckCode(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("attends/v3/api/attends")
    public Call<CheckResponse> SendAttendance(
            @Field("job_id") int job_id,
            @Field("lat") String lat_,
            @Field("long") String long_,
            @Field("image") String image,
            @Field("status") int status

            );



}
