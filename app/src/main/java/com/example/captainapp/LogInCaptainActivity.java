package com.example.captainapp;

import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.captainapp.Json.ImportJson;


public class LogInCaptainActivity extends AppCompatActivity {
    Button logInButton;
    boolean driverValet=true;
    TextView userName,Password;
    //CountryCodePicker ccp;
    String CodeNo="+962";
    ImportJson importJson ;
    CaptainDatabase captainDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captain_log_in_activity_3);

        initialization();

    }

    private void initialization() {
        captainDatabase=new CaptainDatabase(LogInCaptainActivity.this);
        logInButton=findViewById(R.id.logInButton);
        logInButton.setOnClickListener(onClickListener);
        userName=findViewById(R.id.userName);
        Password=findViewById(R.id.password);
       // ccp=findViewById(R.id.login_ccp);
        importJson =new ImportJson(LogInCaptainActivity.this);

//        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
//            @Override
//            public void onCountrySelected(Country selectedCountry) {
//                Toast.makeText(LogInValet.this, "Updated " + selectedCountry.getPhoneCode(), Toast.LENGTH_SHORT).show();
//                CodeNo=selectedCountry.getPhoneCode();
//            }
//        });
    }


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logInButton:
                    logIn();
                   // intentToMain();
                    break;

            }
        }
    };


    void logIn(){

        if(!userName.getText().toString().equals("")){
            if(!Password.getText().toString().equals("")){


                if(driverValet) {

                  //  startService(new Intent(LogInValet.this, MyServices.class));
                    ImportJson importJson=new ImportJson(LogInCaptainActivity.this);
                    importJson.logInAuth(CodeNo.trim()+userName.getText().toString(),Password.getText().toString());

                }else {
                    Intent  intent = new Intent(LogInCaptainActivity.this, LogInCaptainActivity.class);
                    startActivity(intent);
                }


            }else {
                Password.setError("Required !");
            }

        }else {
            userName.setError("Required !");
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void intentToMain(){
        String idUser="",onOff="0";
        try {
             idUser = captainDatabase.getAllUser();
            onOff=captainDatabase.getAllUserOnOff();
        }catch (Exception e){
            idUser="";
            onOff="0";

        }

        if(!isMyServiceRunning(MyServicesForNotificationCaptain.class)){
            startService(new Intent(LogInCaptainActivity.this, MyServicesForNotificationCaptain.class));

        }

        if(singUpUserTableGlobal.getId().equals(idUser)){
            if(onOff.equals("0")){
             //   startService(new Intent(LogInCaptainActivity.this, MyServicesForNotificationCaptain.class));
                captainDatabase.updateUser(singUpUserTableGlobal.getId(),"1");
            }

        }else {
            try {
                captainDatabase.deleteUser();
            }catch (Exception e){

            }
            UserService userService=new UserService();
            userService.setUserid(singUpUserTableGlobal.getId());
            userService.setUserName(singUpUserTableGlobal.getUserName());
            userService.setUserPhoneNo(singUpUserTableGlobal.getPHONE_NO());
            userService.setOnOff("0");
            captainDatabase.addUserService(userService);
            Log.e("MyServicesFor",""+isMyServiceRunning(MyServicesForNotificationCaptain.class));

            if(onOff.equals("0")||onOff.equals("")){
               // startService(new Intent(LogInCaptainActivity.this, MyServicesForNotificationCaptain.class));
                captainDatabase.updateUser(singUpUserTableGlobal.getId(),"1");
            }else {
              //  startService(new Intent(LogInCaptainActivity.this, MyServicesForNotificationCaptain.class));

            }

        }
        Log.e("MyServicesFor_2",""+isMyServiceRunning(MyServicesForNotificationCaptain.class));

        Intent intent = new Intent(LogInCaptainActivity.this, MainCaptainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDes","close "  );
        importJson.updateActivate(0);
    }

    @Override
    protected void onPause() {
        Log.e("onDes2","close "  );
        importJson.updateActivate(0);
        super.onPause();
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
