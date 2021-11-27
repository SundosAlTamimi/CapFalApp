package com.example.captainapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.captainapp.Json.ImportJson;


public class LogInCaptainActivity extends AppCompatActivity {
    Button logInButton;
    boolean driverValet=true;
    TextView userName,Password;
    //CountryCodePicker ccp;
    String CodeNo="+962";
    ImportJson importJson ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captain_log_in_activity_3);

        initialization();

    }

    private void initialization() {
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

    public void intentToMain(){
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

}
