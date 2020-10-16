package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.attendanceapp.fragments.AboutFragment;
import com.example.attendanceapp.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener  {
Toolbar toolbar ;
    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    public  FragmentTransaction fragmentTransaction ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // tvInfo =(TextView) findViewById(R.id.tv_info);
        drawerLayout = findViewById(R.id.drawer_layout);
         navigationView =(NavigationView) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout,toolbar,R.string.open,R.string.close);

        drawerToggle.getDrawerArrowDrawable().setColor(Color.parseColor("#FFF8F3"));
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        SetDrawerHeader();
        NavigateTo("Home");


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

    int  itemId = item.getItemId();

    if (itemId == R.id.item_home){

      /*  HomeFragment homeFragment = new HomeFragment();
      //   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();
        toolbar.setTitle("Home");*/
        NavigateTo("Home");
    }
     if(itemId== R.id.item_about) {
       /* AboutFragment aboutFragment = new AboutFragment();
      //   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, aboutFragment);
         fragmentTransaction.commit();
        toolbar.setTitle("About us");*/
        NavigateTo("About us");
    }
        CloseDrawer();
        return true;
    }
    private void CloseDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void NavigateTo(String page){
        Fragment fragment;
        if(page.equals("Home")){
            fragment = new HomeFragment();
        }else if (page.equals("About us")){
            fragment = new AboutFragment();
        }else fragment = new HomeFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        toolbar.setTitle(page);

    }
    public void SetDrawerHeader(){
        View view = navigationView.getHeaderView(0);

       TextView EmployeeName = view.findViewById(R.id.EmployeeName);
      //  EmployeeName.setText("Omar Gamal");
        EmployeeName.setText(StartActivity.EmployeeData.name);

        TextView EmployeeCode = view.findViewById(R.id.EmployeeCode);
     //   EmployeeCode.setText("156");
        EmployeeCode.setText(StartActivity.EmployeeData.jobId);


    }

}