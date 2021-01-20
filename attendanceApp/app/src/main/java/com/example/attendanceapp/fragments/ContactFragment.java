package com.example.attendanceapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendanceapp.R;
import com.example.attendanceapp.api.DataClient;
import com.example.attendanceapp.api.model.CheckResponse;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactFragment extends Fragment {

    FragmentActivity c;
    EditText EmployeeName,EmployeeEmail,Message;
    Button Send;
    ProgressDialog pd;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final  View view= inflater.inflate(R.layout.fragment_contact, container, false);
        c = getActivity();

        EmployeeName =view.findViewById(R.id.EmployeeName);
        EmployeeEmail = view.findViewById(R.id.EmployeeEmail);
        Message = view.findViewById(R.id.Message);
        Send = view.findViewById(R.id.Send);
        pd =new ProgressDialog(c);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(EmployeeName.getText().toString())){
                    Toast.makeText(c,"الاسم فارغ",Toast.LENGTH_LONG).show();

                }else if(TextUtils.isEmpty(EmployeeEmail.getText().toString())){
                    Toast.makeText(c,"البريد الالكترونى فارغ",Toast.LENGTH_LONG).show();


                }else if(TextUtils.isEmpty(Message.getText().toString())){
                    Toast.makeText(c,"نص الرساله فارغ",Toast.LENGTH_LONG).show();

                }else {
                    SetContact();
                }
            }


        });


        return view;
    }
    private void SetContact() {
        pd.show();
        pd.setContentView(R.layout.progress_dialog);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        pd.setCancelable(false);
        DataClient.getINSTANCE().SetContact(EmployeeName.getText().toString(),EmployeeEmail.getText().toString(),Message.getText().toString()).enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(c, response.body().message, Toast.LENGTH_LONG).show();
                    EmployeeEmail.setText("");
                    EmployeeName.setText("");
                    Message.setText("");
                    Log.d("CurrentUser",response.body().message);
                }else  Toast.makeText(c, "تحقق من اتصالك بالانترنت.", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Toast.makeText(c, "تحقق من اتصالك بالانترنت", Toast.LENGTH_LONG).show();
                Log.d("CurrentUser",t.getMessage());
                pd.dismiss();
            }
        });
    }
}