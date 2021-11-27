package com.example.captainapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.captainapp.Adapter.ListAdapterOrder;
import com.example.captainapp.Adapter.ListAdapterParking;
import com.example.captainapp.Json.ImportJson;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static com.example.captainapp.GlobalVairable.captainClientTransfers;
import static com.example.captainapp.GlobalVairable.captainParkingList;
import static com.example.captainapp.GlobalVairable.clientPhoneNo;

public class ParkingListActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int REQUEST_PHONE_CALL = 1;


    String directionParking,callPhone;
    ListView parkingListView;
    ImportJson importJson ;
    ListAdapterParking listAdapterParking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_activity_layout);
        Initialization();

    }

    private void Initialization() {
        importJson =new ImportJson(ParkingListActivity.this);
        parkingListView =findViewById(R.id.parkingListView);

        importJson.GetParking();
    }


    public  void Call (String number){

        callPhone=number;
        if(isPermissionGranted()){
            // You can use the API that requires the permission.
            CallPer(number);
        }else {
            Toast.makeText(this, "Please Enable Call Phone Permission", Toast.LENGTH_SHORT).show();
        }


    }


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    void CallPer(String no){
        callPhone=no;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+no));
        startActivity(callIntent);
    }


    public  void DirectionA (String a){
        directionParking=a;
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(ParkingListActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        // getLocation();

        Directions(a);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(flags==1) {
                        Directions(directionParking);
//                    }else {
//                        ParkingLoc();
//                    }
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Please Enable Access to Location " , Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case REQUEST_PHONE_CALL :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallPer(callPhone);
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;

//            case MY_PERMISSIONS_REQUEST_USE_CAMERA:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //  Log.d(TAG,"permission was granted! Do your stuff");
//                    barcodeReadPer();
//                } else {
//                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//                    // Log.d(TAG,"permission denied! Disable the function related with permission.");
//                }
//                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    void Directions(String dir){
        String a=dir.replace("lat/lng:","").replace("(","").replace(")","").replace(" ","");

        Log.e("hh",""+a);

        String[] latLong =a.split(",");
        Log.e("hh[] = ",""+latLong[0]+"/"+latLong[1]);
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latLong[0]+","+latLong[1] + "&mode=d");
        Intent mapIntent =new  Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }else {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr="+latLong[0]+","+latLong[1]));
            startActivity(intent);
        }
    }

    public void ListOfParking() {
        listAdapterParking = new ListAdapterParking(ParkingListActivity.this, captainParkingList);
        parkingListView.setAdapter(listAdapterParking);
    }
}
