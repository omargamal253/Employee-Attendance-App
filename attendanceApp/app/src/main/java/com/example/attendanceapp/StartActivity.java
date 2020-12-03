package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceapp.api.DataClient;
import com.example.attendanceapp.api.model.CheckResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
    String androidId ;
    TextView textView;

    public static CheckResponse.Data EmployeeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = findViewById(R.id.textView);
       // androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        androidId = "9f83789b6c8661df";
      //    androidId = "bmlkgkl@lk052sd"; //block employee
     //   androidId = "9f83789b6888661df"; //  new Employee


        DataClient.getINSTANCE().CheckCode(androidId).enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                if(response.body()!=null){
                    Log.d("CurrentUser",response.body().message);
                    Toast.makeText(StartActivity.this,response.body().message,Toast.LENGTH_SHORT).show();
                    String status_code=" ",block=" ";
                    if(response.body().data!=null){
                     status_code =response.body().data.statusCode;
                     block =response.body().data.block;
                    }

                    if(response.body().data==null|| (status_code.equals("0") && block.equals("0"))){
                        Intent intent = new Intent(StartActivity.this,activationActivity.class);
                        startActivity(intent);
                        finish();

                    }else if( status_code.equals("1") && block.equals("1")){
                        textView.setText(response.body().message);
                    }else if( status_code.equals("1") && block.equals("0")){
                        EmployeeData =response.body().data;
                        Intent intent = new Intent(StartActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"تحقق من اتصالك بالانترنت",Toast.LENGTH_LONG).show();

                    }
                }





            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.d("CurrentUser",t.getMessage());
                Toast.makeText(getApplicationContext(),"تحقق من اتصالك بالانترنت",Toast.LENGTH_LONG).show();


            }
        });
    }
}