package com.example.captainapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.example.captainapp.GlobalVairable.appActivate;
import static com.example.captainapp.GlobalVairable.captainClientTransfers;
import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

import com.example.captainapp.Adapter.ListAdapterOrder;
import com.example.captainapp.Json.ImportJson;
import com.polyak.iconswitch.IconSwitch;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainCaptainActivity extends AppCompatActivity implements View.OnClickListener {
    private IconSwitch iconSwitch;
    LinearLayout mainLinear,goLinear,profileLinear,parkingList;
    TextView activUser,userNameText;
    CaptainDatabase captainDatabase;
    String activity="0",ip="";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ImportJson importJson;
    CircleImageView carPicC;
    RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captain_main_activity_3);

        Initialization();
      //  importJson.GetBitmap();
    }

    private void Initialization() {
        appActivate="0";

        iconSwitch = (IconSwitch) findViewById(R.id.iconSwitch);

        mainLinear=findViewById(R.id.mainLinear);
        profileLinear=findViewById(R.id.profileLinear);
        goLinear=findViewById(R.id.goLinear);

        userNameText=findViewById(R.id.userNameText);
        userNameText.setText("Welcome Captain "+singUpUserTableGlobal.getUserName());
        mainLinear.setOnClickListener(this);
        profileLinear.setOnClickListener(this);
        goLinear.setOnClickListener(this);
        activUser=findViewById(R.id.activUser);
        parkingList=findViewById(R.id.parkingList);
        ratingBar=findViewById(R.id.ratingBar);
        parkingList.setOnClickListener(this);
        captainDatabase=new CaptainDatabase(MainCaptainActivity.this);
        activity=captainDatabase.getAllActivitySetting();
        ip=captainDatabase.getAllIPSetting();
        carPicC=findViewById(R.id.carPicC);
importJson=new ImportJson(MainCaptainActivity.this);
        if(!TextUtils.isEmpty(activity)){
            if (activity.equals("1")){
//                iconSwitch.setClickable(true);
                activUser.setText("ACTIVATE");
                iconSwitch.setChecked(IconSwitch.Checked.LEFT);

                Log.e("activity","1");
            }else if(activity.equals("0")){
                activUser.setText("NOT ACTIVATE");
                iconSwitch.setChecked(IconSwitch.Checked.RIGHT);

                Log.e("activity","3");
            }

        }else {
            activUser.setText("NOT ACTIVATE");
            iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
            Log.e("activity","2");

        }

        ratingBar.setFocusable(false);

//        iconSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if( iconSwitch.getChecked()== IconSwitch.Checked.LEFT){
//                       //showing simple toast message to the user
//                       captainDatabase.updateaCTIVE(ip, "1");
//                       activUser.setText("ACTIVATE");
//                       Toast.makeText(MainCaptainActivity.this, "ACTIVATE", Toast.LENGTH_SHORT).show();
//                       Log.e("activity", "52");
//
//
//               }else if( iconSwitch.getChecked()== IconSwitch.Checked.RIGHT){
//                   //showing simple toast message to the user
//                   activUser.setText("NOT ACTIVATE");
//                   captainDatabase.updateaCTIVE(ip, "0");
//                   Toast.makeText(MainCaptainActivity.this, "NOT ACTIVATE", Toast.LENGTH_SHORT).show();
//                   Log.e("activity", "62");
//
//               }
//            }
//        });

// IconSwitch  Checked Change Listener
        iconSwitch.setCheckedChangeListener(new IconSwitch.CheckedChangeListener()
        {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {


                //simple witch case
                switch (current) {

                    case LEFT:
                        //showing simple toast message to the user
                        captainDatabase.updateaCTIVE(ip, "1");
                        activUser.setText("ACTIVATE");
                        Toast.makeText(MainCaptainActivity.this, "ACTIVATE", Toast.LENGTH_SHORT).show();
                        Log.e("activity", "52");

                        break;

                    case RIGHT:
                        //showing simple toast message to the user
                        activUser.setText("NOT ACTIVATE");
                        captainDatabase.updateaCTIVE(ip, "0");
                        Toast.makeText(MainCaptainActivity.this, "NOT ACTIVATE", Toast.LENGTH_SHORT).show();
                        Log.e("activity", "62");

                        break;
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainLinear:
//                if(appActivate.equals("0")){

                    Toast.makeText(this, "In main", Toast.LENGTH_SHORT).show();
//                }else{
//                    Intent intentMain=new Intent(MainCaptainActivity.this,MA.class);
//                    startActivity(intentMain);
//                }

                break;
            case R.id.profileLinear:
                Intent intentProf=new Intent(MainCaptainActivity.this,ProfileActivate.class);
                startActivity(intentProf);
                break;
            case R.id.goLinear:


                if ( Build.VERSION.SDK_INT >= 23){
                    if (ActivityCompat.checkSelfPermission(MainCaptainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED  ){
                        requestPermissions(new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_ASK_PERMISSIONS);
                        return ;
                    }
                }

                goToCap();
                break;
            case R.id.parkingList:
                Intent intentParking=new Intent(MainCaptainActivity.this,ParkingListActivity.class);
                startActivity(intentParking);
                break;
        }
    }


    void goToCap(){
        Intent intent=new Intent(MainCaptainActivity.this,CaptainMapsActivity.class);
        startActivity(intent);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToCap();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Please Enable Access to Location " , Toast.LENGTH_SHORT)
                            .show();
                }
                break;


            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

public  void fillImage() {
        if(singUpUserTableGlobal.getPROFILE_PIC_Bitmap()!=null) {
            carPicC.setImageBitmap(singUpUserTableGlobal.getPROFILE_PIC_Bitmap());
        }


}
}
