package com.example.attendanceapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceapp.R;
import com.example.attendanceapp.api.DataClient;
import com.example.attendanceapp.api.model.PageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutFragment extends Fragment {
FragmentActivity c;
TextView AboutText;

    public AboutFragment() {
        // Required empty public constructor
    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_about, container, false);
        c = getActivity();

           AboutText= view.findViewById(R.id.AboutText);

           DataClient.getINSTANCE().getPage(1).enqueue(new Callback<PageResponse>() {
               @Override
               public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
                   if(response.isSuccessful()){
                       Log.d("CurrentUser",response.body().message);
                       if(response.body().data!=null){
                           AboutText.setText(response.body().data.content);
                       }

                   }else  Toast.makeText(c, "تحقق من اتصالك بالانترنت.", Toast.LENGTH_LONG).show();

               }

               @Override
               public void onFailure(Call<PageResponse> call, Throwable t) {
                   Log.d("CurrentUser",t.getMessage());

                   Toast.makeText(c, "تحقق من اتصالك بالانترنت", Toast.LENGTH_LONG).show();

               }
           });
        return  view;
    }
}