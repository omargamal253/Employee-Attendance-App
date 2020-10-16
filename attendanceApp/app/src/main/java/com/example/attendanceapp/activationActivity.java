package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activationActivity extends AppCompatActivity {

    EditText EmployeeName,EmployeeCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        EmployeeName = findViewById(R.id.EmployeeName);
        EmployeeCode = findViewById(R.id.EmployeeCode);


    }


    public void ActiveEmployee(View view) {
        Boolean Install = WhatsInstallOrNot("com.whatsapp");

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

       /* String s = "";
        s += "\n androidId:     " + androidId;
        //   s += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
        s += "\n Device name:     " + Build.BRAND+" "+android.os.Build.DEVICE;
        s += "\n Android version:     " + android.os.Build.VERSION.RELEASE ;*/


        String   message = "Employee name : "+EmployeeName.getText().toString()+"\n"+
                           "Employee code : "+EmployeeCode.getText().toString()+"\n"+
                           "AndroidId:     " + androidId+"\n"+
                           "Device name:     " + Build.BRAND+" "+android.os.Build.DEVICE+"\n"+
                           "Android version:     " + android.os.Build.VERSION.RELEASE ;

                ;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(Install){
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+20"+"01140426977"+"&text="+message));
        startActivity(intent);
        }else{
            Toast.makeText(this,"WhatsApp not installed in your device",Toast.LENGTH_LONG).show();

        }
    }
    public boolean WhatsInstallOrNot(String uri){
        boolean Install;
        PackageManager Manager = getPackageManager();
        try {
            Manager.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            Install =true;
        }catch (PackageManager.NameNotFoundException e){
            Install=false;
        }
        return  Install;

    }
}