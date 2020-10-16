package com.example.attendanceapp.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceapp.R;
import com.example.attendanceapp.StartActivity;
import com.example.attendanceapp.api.DataClient;
import com.example.attendanceapp.api.model.CheckResponse;
import com.example.attendanceapp.viewmodel.LocationModel;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
     FragmentActivity c;
RelativeLayout CamRelative;
ImageView Image;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int Location_PERM_CODE = 100;
    Bitmap bitmap;
TextClock DateText;
TextClock textClock;
LocationModel locationModel;
Button CheckInBtn,BreakOutBtn,BreakInBtn,CheckOutBtn;
    ProgressDialog pd ,pd2;  View DialogView;
    TextView Message, EmployeeName, EmployeeCode, Time;
    Button Back ;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        c =getActivity();
        Image= view.findViewById(R.id.Image);

        CamRelative = view.findViewById(R.id.CamRelative);
        CamRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();

            }
        });
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();

            }
        });
        textClock = view.findViewById(R.id.textClock);
        DateText = view.findViewById(R.id.DateText);
        //  DateText.setText(textClock.getText());2
        askLocationPermissions();
        locationModel = ViewModelProviders.of(this).get(LocationModel.class);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationModel.GetLocation(getActivity());

        }
        DisplayLocation();
        pd = new ProgressDialog(c);


        CheckInBtn = view.findViewById(R.id.CheckInBtn);
        CheckInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAttendance(1);
            }
        });
        BreakOutBtn = view.findViewById(R.id.BreakOutBtn);
        BreakOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAttendance(2);

            }
        });
        BreakInBtn = view.findViewById(R.id.BreakInBtn);
        BreakInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAttendance(3);

            }
        });

        CheckOutBtn = view.findViewById(R.id.CheckOutBtn);
        CheckOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAttendance(4);

            }
        });

        DefineProgressAttend();


        return view;
    }
    public void DefineProgressAttend(){
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DialogView  =layoutInflater.inflate(R.layout.progress_attend,null);
        pd2 = new ProgressDialog(c);

        Message = DialogView.findViewById(R.id.message);
        EmployeeName = DialogView.findViewById(R.id.EmployeeName);
        //EmployeeName.setText("Omar Gamal");
        EmployeeName.setText(StartActivity.EmployeeData.name);
        EmployeeCode = DialogView.findViewById(R.id.EmployeeCode);
        //EmployeeCode.setText("156");
        EmployeeCode.setText(StartActivity.EmployeeData.jobId);

        Time = DialogView.findViewById(R.id.Time);

        Back = DialogView.findViewById(R.id.BackBtn);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd2.dismiss();
            }
        });

    }
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }

    }
    private  void askLocationPermissions(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , Location_PERM_CODE);
            }
            return;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_PERM_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      //  ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), String.valueOf(permissions));
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
                Toast.makeText(c, "Allow selected.", Toast.LENGTH_SHORT).show();
            }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    //Show permission explanation dialog...
                    askCameraPermissions();
                    Toast.makeText(c, "Deny selected.", Toast.LENGTH_SHORT).show();
                }else{
                    //Never ask again selected, or device policy prohibits the app from having that permission.
                 //   Toast.makeText(c, "Never ask again selected.", Toast.LENGTH_SHORT).show();
                    c.finish();
                    Toast.makeText(c, "Camera Permission is Required to Use camera .", Toast.LENGTH_LONG).show();
                }
            }
        }

        else  if(requestCode == Location_PERM_CODE){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
              //  askLocationPermissions();
                locationModel.GetLocation(getActivity());
                Toast.makeText(c, "Allow selected.", Toast.LENGTH_SHORT).show();
            }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Show permission explanation dialog...
                    askLocationPermissions();
                    Toast.makeText(c, "Deny selected.", Toast.LENGTH_SHORT).show();
                }else{
                    //Never ask again selected, or device policy prohibits the app from having that permission.
                    //   Toast.makeText(c, "Never ask again selected.", Toast.LENGTH_SHORT).show();
                    c.finish();
                    Toast.makeText(c, "Location Permission is Required to Use camera .", Toast.LENGTH_LONG).show();
                }
            }

           }

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_PERM_CODE){
            try {
                if(data.getExtras()!=null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Image.setVisibility(View.VISIBLE);
                    Image.setImageBitmap(bitmap);
                    CamRelative.setVisibility(View.GONE);
                    Log.d("CurrentImage : ", ImageToString());
                }
            }catch (Exception e){
                Log.d("CurrentImage : ", e.getMessage());

            }

        }
    }

    public  String ImageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
    public void DisplayLocation(){
        locationModel.locationMutableLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Log.d("CurrentLocation",String.valueOf(location.getLatitude()) + "  "+String.valueOf(location.getLongitude()));
            }
        });
    }

    protected  void SendAttendance(int status){

        if(bitmap==null){
            Toast.makeText(c, "انقر لالتقاط صورة أولا", Toast.LENGTH_SHORT).show();

        }else if (locationModel.locationMutableLiveData.getValue()==null) {
            Toast.makeText(c, "حاول مرة أخرى.. ", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationModel.GetLocation(getActivity());

            }

        }else {

            pd.show();
            pd.setContentView(R.layout.progress_dialog);
            pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
            pd.setCancelable(false);
String Base64 = "data:image/png;base64,"+ImageToString();
        //    String Base64=  "data:image/png;base64,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";

         /*   Log.d("CurrentUser",    "156" );
            Log.d("CurrentUser",   String.valueOf(locationModel.locationMutableLiveData.getValue().getLatitude()));
            Log.d("CurrentUser",    String.valueOf(locationModel.locationMutableLiveData.getValue().getLongitude()));
            Log.d("CurrentUser",    Base64 );
            Log.d("CurrentUser",   String.valueOf( status) );*/



            DataClient.getINSTANCE().SendAttendance(Integer.parseInt(StartActivity.EmployeeData.jobId),
                    String.valueOf(locationModel.locationMutableLiveData.getValue().getLatitude()),
                    String.valueOf(locationModel.locationMutableLiveData.getValue().getLongitude()),
                    Base64, status).enqueue(new Callback<CheckResponse>() {
                @Override
                public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                    pd.dismiss();
                    if(response.body()!=null) {
                        Log.d("CurrentUser", response.body().message);

                        Toast.makeText(c, response.body().message, Toast.LENGTH_SHORT).show();

                        pd2.show();
                        pd2.setContentView(DialogView);
                        pd2.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        pd2.setCancelable(false);
                        Time.setText(textClock.getText().toString());
                        Message.setText(response.body().message);

                        Image.setVisibility(View.GONE);
                        CamRelative.setVisibility(View.VISIBLE);


                    }else      Toast.makeText(c, "حاول مرة أخرى في وقت لاحق..", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<CheckResponse> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(c, "تحقق من اتصالك بالانترنت", Toast.LENGTH_LONG).show();
                    Log.d("CurrentUser", t.getMessage());

                }
            });

                }
        }

    }


