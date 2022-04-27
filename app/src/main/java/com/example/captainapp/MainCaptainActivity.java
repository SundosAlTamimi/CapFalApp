package com.example.captainapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.core.content.ContextCompat;

import static com.example.captainapp.GlobalVairable.appActivate;
import static com.example.captainapp.GlobalVairable.captainClientTransfers;
import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

import com.example.captainapp.Adapter.ListAdapterOrder;
import com.example.captainapp.Json.ImportJson;
import com.google.android.gms.maps.model.LatLng;
import com.polyak.iconswitch.IconSwitch;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainCaptainActivity extends AppCompatActivity implements View.OnClickListener {
    private IconSwitch iconSwitch;
    LinearLayout mainLinear,goLinear,profileLinear,parkingList;
    TextView activUser,userNameText,complete;
    CaptainDatabase captainDatabase;
    String activity="0",ip="";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ImportJson importJson;
    CircleImageView carPicC;
    RatingBar ratingBar;
    SweetAlertDialog swASingUp;

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
        complete=findViewById(R.id.Complete);
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
importJson.getComplete();
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
        getCorrectLoc();
    }

    void getCorrectLoc(){

        swASingUp = new SweetAlertDialog(MainCaptainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        swASingUp.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        swASingUp.setTitleText("Please Wait until get location " );
        swASingUp.setCancelable(false);
        swASingUp.show();
        int isIn=5;
//        for (int i=0;i<=isIn;i++) {
            Location location = getLastKnownLocation();
            try {
              double  v1 = location.getLatitude();
              double  v2 = location.getLongitude();

                Log.e("loc12"," "+isIn);

                Intent intent=new Intent(MainCaptainActivity.this,CaptainMapsActivity.class);
                startActivity(intent);

                swASingUp.dismissWithAnimation();
                //isIn=i;
//                break;
            } catch (Exception e) {
                isIn++;
                Log.e("loc133", " " + isIn);
                swASingUp.dismissWithAnimation();
                SweetAlertDialog swASing_ = new SweetAlertDialog(MainCaptainActivity.this, SweetAlertDialog.WARNING_TYPE);
                swASing_.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
                swASing_.setTitleText("Please Try Again (Can not get Location)!!");
                swASing_.setCancelable(true);
                swASing_.show();

//                if(isIn==1000) {
//
//                    Toast.makeText(MainCaptainActivity.this, "Can Not Get Location ", Toast.LENGTH_SHORT).show();
//               swASingUp.dismissWithAnimation();
////                break;
//                }
//            }
        }

    }

    private Location getLastKnownLocation() {
        Location l=null;
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);

            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
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
public void completeText(String noComp){
        complete.setText(noComp);
}

public  void fillImage() {
        if(singUpUserTableGlobal.getPROFILE_PIC_Bitmap()!=null) {
            carPicC.setImageBitmap(singUpUserTableGlobal.getPROFILE_PIC_Bitmap());
        }


}
}
