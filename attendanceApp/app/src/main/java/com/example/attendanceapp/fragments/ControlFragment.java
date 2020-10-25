package com.example.attendanceapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.attendanceapp.R;
import com.example.attendanceapp.api.model.SettingResponse;
import com.example.attendanceapp.viewmodel.SettingViewModel;

public class ControlFragment extends Fragment  {
    FragmentActivity c ;
    public static WebView webView ;
    ProgressDialog pd;
    SettingViewModel settingViewModel;

    public ControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_control, container, false);
        c=  getActivity();
        pd = new ProgressDialog(c);
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        pd.setCancelable(true);

        webView = view.findViewById(R.id.webView);




        webView.setWebViewClient(new WebViewClient()

                                 {
                                     @Override
                                     public void onReceivedError(WebView view, int errorCode,
                                                                 String description, String failingUrl) {
                                         // TODO Auto-generated method stub
                                         super.onReceivedError(view, errorCode, description, failingUrl);
                                         pd.dismiss();
                                         Toast.makeText(c,description,Toast.LENGTH_LONG).show();


                                     }
                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         // TODO Auto-generated method stub
                                         super.onPageFinished(view, url);
                                         //   Toast.makeText(c,"Page Loaded Successful",Toast.LENGTH_SHORT).show();
                                         pd.dismiss();

                                     }


                                 }
        );


        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        if(settingViewModel.SettingData.getValue()!=null){
            webView.loadUrl(settingViewModel.SettingData.getValue().data.webviewLink);
            Log.d("CurrentUser","settingViewModel.SettingData != null");

        }else {
            settingViewModel.getSetting();

            settingViewModel.SettingData.observe(this, new Observer<SettingResponse>() {
                @Override
                public void onChanged(SettingResponse settingResponse) {
                    webView.loadUrl(settingViewModel.SettingData.getValue().data.webviewLink);
                    Log.d("CurrentUser", "settingViewModel.SettingData from observer");
                }
            });
        }
        //    webView.loadUrl("https://injazcart.com/attends/v3");
        //  webView.loadUrl("https://www.google.com");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        return view;
    }


}