package com.example.attendanceapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.attendanceapp.api.DataClient;
import com.example.attendanceapp.api.model.SettingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingViewModel extends ViewModel {
  public static    MutableLiveData<SettingResponse> SettingData =new MutableLiveData<>();

    public void getSetting(){
        DataClient.getINSTANCE().getSetting().enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(Call<SettingResponse> call, Response<SettingResponse> response) {
                if(response.isSuccessful()){
                    SettingData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SettingResponse> call, Throwable t) {

            }
        });
    }
}
