package com.example.attendanceapp.api;

import com.example.attendanceapp.AdminPanel;
import com.example.attendanceapp.api.model.CheckResponse;
import com.example.attendanceapp.api.model.PageResponse;
import com.example.attendanceapp.api.model.SettingResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Path;

public class DataClient {

    private static final String BASE_URL = AdminPanel.getHostUrl();

    private ApiInterface apiInterface;
    private static DataClient INSTANCE;

    public DataClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }


    public static DataClient getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new DataClient();
        }
        return INSTANCE;
    }


    public Call<CheckResponse> CheckCode(String code){
        return  apiInterface.CheckCode(code);
    }

    public Call<CheckResponse> SendAttendance(
            int job_id,
             String lat_,
         String long_,
       String image,
       int status

    ){
        return apiInterface.SendAttendance(job_id,lat_,long_,image,status);
    }

    public Call<CheckResponse> SetContact(String name, String email, String message){
        return apiInterface.SetContact(name,email,message);
    }
    public Call<PageResponse> getPage( int id  ){
        return  apiInterface.getPage(id);
    }
    public Call<SettingResponse> getSetting(){
        return  apiInterface.getSetting();
    }


}
