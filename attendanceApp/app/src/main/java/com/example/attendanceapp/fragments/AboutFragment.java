package com.example.attendanceapp.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceapp.R;
import com.example.attendanceapp.api.DataClient;
import com.example.attendanceapp.api.model.PageResponse;
import com.example.attendanceapp.api.model.SettingResponse;
import com.example.attendanceapp.viewmodel.SettingViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutFragment extends Fragment {
    FragmentActivity c;
    TextView AboutText, Phone,Email,Address,SiteCopy;
    SettingViewModel settingViewModel;
    RelativeLayout mail_Relative,CallRelative;
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
        Phone= view.findViewById(R.id.Call_Mobile);
        Email = view.findViewById(R.id.about_email);
        Address = view.findViewById(R.id.about_address);
        SiteCopy=view.findViewById(R.id.SiteCopy);
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


        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        if(settingViewModel.SettingData.getValue()!=null){

            Phone.setText(settingViewModel.SettingData.getValue().data.sitePhone);
            Email.setText(settingViewModel.SettingData.getValue().data.siteEmail);
            Address.setText(settingViewModel.SettingData.getValue().data.siteAddress);
            SiteCopy.setText(settingViewModel.SettingData.getValue().data.siteCopy);

            Log.d("CurrentUser","settingViewModel.SettingData != null");

        }else {
            settingViewModel.getSetting();

            settingViewModel.SettingData.observe(this, new Observer<SettingResponse>() {
                @Override
                public void onChanged(SettingResponse settingResponse) {

                    Phone.setText(settingViewModel.SettingData.getValue().data.sitePhone);
                    Email.setText(settingViewModel.SettingData.getValue().data.siteEmail);
                    Address.setText(settingViewModel.SettingData.getValue().data.siteAddress);
                    SiteCopy.setText(settingViewModel.SettingData.getValue().data.siteCopy);

                    Log.d("CurrentUser", "settingViewModel.SettingData from observer");
                }
            });
        }

        mail_Relative = view.findViewById(R.id.mail_Relative);
        mail_Relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{Email.getText().toString()});

                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
        });

        CallRelative= view.findViewById(R.id.CallRelative);
        CallRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Boolean Install = WhatsInstallOrNot("com.whatsapp");
                if(Install){
               //     intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+02"+"01102090773"+"&text="+""));
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+Phone.getText().toString()+"&text="+""));
                  //  Toast.makeText(c,Phone.getText().toString(),Toast.LENGTH_LONG).show();

                    startActivity(intent);
                }else{
                    Toast.makeText(c,"تطبيق واتس اب غير مثبت على هاتفك",Toast.LENGTH_LONG).show();

                }
            }
        });

        return  view;
    }
    public boolean WhatsInstallOrNot(String uri){
        boolean Install;
        PackageManager Manager = c.getPackageManager();
        try {
            Manager.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            Install =true;
        }catch (PackageManager.NameNotFoundException e){
            Install=false;
        }
        return  Install;

    }
}