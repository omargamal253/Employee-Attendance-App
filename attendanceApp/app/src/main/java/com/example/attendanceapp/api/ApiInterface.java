package com.example.attendanceapp.api;

import com.example.attendanceapp.api.model.CheckResponse;
import com.example.attendanceapp.api.model.PageResponse;
import com.example.attendanceapp.api.model.SettingResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {



    @FormUrlEncoded
    @POST("api/checkCode")
    public Call<CheckResponse> CheckCode(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("api/attends")
    public Call<CheckResponse> SendAttendance(
            @Field("job_id") int job_id,
            @Field("lat") String lat_,
            @Field("long") String long_,
            @Field("image") String image,
            @Field("status") int status

            );

    @FormUrlEncoded
    @POST("api/contact")
    public Call<CheckResponse> SetContact(
            @Field("name") String name,
            @Field("email") String email,
            @Field("message") String message
    );



    @GET("api/page/{id}")
    public Call<PageResponse> getPage(@Path("id") int id  );

    @GET("api/settings")
    public Call<SettingResponse> getSetting();



}
