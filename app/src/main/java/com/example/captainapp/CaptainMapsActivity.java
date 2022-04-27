package com.example.captainapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.captainapp.Adapter.*;
import com.example.captainapp.Json.ExportJson;
import com.example.captainapp.Json.ImportJson;
import com.example.captainapp.Model.CaptainClientTransfer;
import com.example.captainapp.Model.ValetFireBaseItem;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.example.captainapp.GlobalVairable.appActivate;
import static com.example.captainapp.GlobalVairable.captainClientReturn;
import static com.example.captainapp.GlobalVairable.captainClientTransfers;
import static com.example.captainapp.GlobalVairable.clientId;
import static com.example.captainapp.GlobalVairable.clientLocation;
import static com.example.captainapp.GlobalVairable.clientPhoneNo;
import static com.example.captainapp.GlobalVairable.isOk;
import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;


public class CaptainMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button requestButton, scanQRCode, arriveOk, arriveToClient, parkingCar, direction, arriveToClientReturn, confirm, goneDeleteLin, directionR,
            call_1, call_2, CUSTOMER_RECEIVED, yourCarWay;
    public static List<LatLng> LatLngListMarker;
    private LatLngBounds.Builder builder;
    LatLngBounds bounds;
    boolean flag = false;
    Timer timer;
    List<CaptainClientTransfer> tempApp;

    boolean getFlag = true, isStall = true;
    double v1 = 31.951110, v2 = 35.917270, a1 = 0.0, a2 = 0.0;
    boolean trackOn=false;
//double v1=0.0, v2=0.0,a1=0.0,a2=0.0;

    SweetAlertDialog pdaSweet;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ListView orderList, returnCar;
    ListAdapterOrder listAdapterOrder;
    int flags = 1;
    ListAdapterReturn listAdapterReturn;
    LinearLayout _1DialogLocation, _2DialogLocation, _3DialogLocation, _4DialogLocation, _5DialogLocation, _6DialogLocation, _8DialogLocation, _9DialogLocation, _10DialogLocation, _11DialogLocation, carReturn, linearReturn, _151DialogLocation, deleteLinear, regenaratQr;
    ImportJson importJson;
    Timer T;
    CaptainDatabase captainDatabase;
    ExportJson exportJson;
    EditText hour, min, sec;
    ImageView barcode;
    private static final int REQUEST_PHONE_CALL = 1;
    private final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 0x00AF;
    LocationManager locationManager;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    int randomNumber;
    public static TextView returnText;
    String activity = "";
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valet_captain_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        initialization();

        mapFragment.getMapAsync(this);


    }

    private void initialization() {
        appActivate = "2";
        // requestButton=findViewById(R.id.requestButton);
//        scanBarcode=findViewById(R.id.scanBarcode);
        //  requestButton.setOnClickListener(onClickListener);
//        scanBarcode.setOnClickListener(onClickListener);

//        scanBarcode.setVisibility(View.GONE);
//        requestButton.setVisibility(View.VISIBLE);


//        try {
//            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        String provider = locationManager.getBestProvider(criteria, true);
//
//
//            String locationProvider = LocationManager.NETWORK_PROVIDER;
//            // I suppressed the missing-permission warning because this wouldn't be executed in my
//            // case without location services being enabled
//            //  @SuppressLint("MissingPermission")
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//         //   android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
//        Location location = locationManager.getLastKnownLocation(provider);
////            try {
////            v1 = location.getLatitude();
////            v2 = location.getLongitude();
////            } catch (Exception e) {
//////                v1 = 31.951110;
//////                v2 = 35.917270;
////            }
//
////        }catch (Exception e){
////            Log.e("LocationLanLag", "  Exception");
////        }
//


        tempApp = new ArrayList();
        LatLngListMarker = new ArrayList<>();
        LatLngListMarker.clear();
        LatLng latLng = new LatLng(v1, v2);
        LatLngListMarker.add(latLng);
        builder = new LatLngBounds.Builder();
        orderList = findViewById(R.id.orderList);
        _1DialogLocation = findViewById(R.id._1DialogLocation);
        _2DialogLocation = findViewById(R.id._2DialogLocation);
        _3DialogLocation = findViewById(R.id._3DialogLocation);
        _4DialogLocation = findViewById(R.id._4DialogLocation);
        _5DialogLocation = findViewById(R.id._5DialogLocation);
        _6DialogLocation = findViewById(R.id._6DialogLocation);
        _8DialogLocation = findViewById(R.id._8DialogLocation);
        _9DialogLocation = findViewById(R.id._9DialogLocation);
        _10DialogLocation = findViewById(R.id._10DialogLocation);//waite
        _11DialogLocation = findViewById(R.id._11DialogLocation);//confirm
        _151DialogLocation = findViewById(R.id._151DialogLocation);
        regenaratQr = findViewById(R.id.regenaratQr);
        //call_1=findViewById(R.id.call_1);
        call_2 = findViewById(R.id.call_2);
        returnText = findViewById(R.id.returnText);
        CUSTOMER_RECEIVED = findViewById(R.id.CUSTOMER_RECEIVED);
        goneDeleteLin = findViewById(R.id.goneDeleteLin);
        directionR = findViewById(R.id.directionR);
        arriveToClientReturn = findViewById(R.id.arriveToClientReturn);
        scanQRCode = findViewById(R.id.scanQRCode);
        hour = findViewById(R.id.hour);
        min = findViewById(R.id.minut);
        sec = findViewById(R.id.sec);
        parkingCar = findViewById(R.id.parkingCar);
        arriveToClient = findViewById(R.id.arriveToClient);
        confirm = findViewById(R.id.confirm);
        arriveOk = findViewById(R.id.arriveOk);
        barcode = findViewById(R.id.barcode);
        carReturn = findViewById(R.id.carReturn);
        direction = findViewById(R.id.direction);
        returnCar = findViewById(R.id.returnCar);
        linearReturn = findViewById(R.id.linearReturn);
        linearReturn.setVisibility(View.GONE);
        deleteLinear = findViewById(R.id.deleteLinear);
        yourCarWay = findViewById(R.id.yourCarWay);
        captainDatabase = new CaptainDatabase(CaptainMapsActivity.this);
        listAvilable(0);
        listAdapterOrder = new ListAdapterOrder(CaptainMapsActivity.this, captainClientTransfers);

        importJson = new ImportJson(CaptainMapsActivity.this);
        //importJson.GetOrders();

        exportJson = new ExportJson(CaptainMapsActivity.this);

        importJson = new ImportJson(CaptainMapsActivity.this);
//        T = new Timer();
//        T.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                if (isOk){
//                importJson.getStatuss();
//            }
//            }
//
//        }, 0, 1000);


        LatLngListMarker.clear();
        LatLngListMarker.add(new LatLng(v1, v2));


        databaseReference = FirebaseDatabase.getInstance().getReference();

        regenaratQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatorQRCode();
            }
        });


        Query query3 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child("INeedCaptain");
//        query2.

        ValueEventListener listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    // String mychild = dataSnapshot.getValue().toString();
                    // textView.setText(mychild);
                    activity = captainDatabase.getAllActivitySetting();
                    if (activity.equals("1")) {
                        String mychild = dataSnapshot.getValue().toString();

                        if (mychild.equals("1")) {
                            String app = captainDatabase.getAllSetting();
                            Log.e("dataGone", "" + app);
                            if (TextUtils.isEmpty(app)) {
                                Log.e("dataGone", "in  " + app);

                                if (getFlag) {
                                    importJson.GetOrders();
                                }
                            }
                        }
                    }
                    //  Toast.makeText(CaptainMapsActivity.this, "succ " + mychild, Toast.LENGTH_SHORT).show();

                } catch (Exception y) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query3.addValueEventListener(listener2);


        Query query6 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child("DeleteRequest");
//        query2.

        ValueEventListener listener6 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    activity = captainDatabase.getAllActivitySetting();
                    if (activity.equals("1")) {
                        String mychild = dataSnapshot.getValue().toString();

                        if (mychild.equals("1")) {
                            importJson.getStatuss();
                        }
                    }
                } catch (Exception y) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query6.addValueEventListener(listener6);


        //tracking
//        Query query88 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(clientId + "_LocationTracking");
////        query2.
//
//        ValueEventListener listener88 = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                try {
//                    activity = captainDatabase.getAllActivitySetting();
//                    if (activity.equals("1")) {
//                        String mychild = dataSnapshot.getValue().toString();
//                        try {
//                            if(trackOn){
//                            String[] loc = locationClient();
//                            String[] a = mychild.split("/");
//                            trackingFire(a[0], a[1],loc[0],loc[1]);
//                            }
//                        }catch (Exception e){
//                            Log.e("errorNoTracking","AppFireBase");
//                        }
//                    }
//                } catch (Exception y) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        query88.addValueEventListener(listener88);

        Query query = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_VALET");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    activity = captainDatabase.getAllActivitySetting();
                    if (activity.equals("1")) {
                        ValetFireBaseItem mychild = dataSnapshot.getValue(ValetFireBaseItem.class);
//                    clientId=mychild.getClientId();
//                    Log.e("clin ",""+clientId+"   "+mychild.getClientId());
//                   ListShow(mychild.getStatus());
                        importJson.getStatuss();
                    }
                    //  Toast.makeText(CaptainMapsActivity.this, "succ _VALET" + mychild, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("dataGoneError ", "hh");
                    //   activity = captainDatabase.getAllActivitySetting();
//                    if (activity.equals("1")) {
                    importJson.GetOrders();
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query.addValueEventListener(listener);


//        Query query61 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_LATE");
////        query2.
//
//        ValueEventListener listener61 = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                try {
//                    activity = captainDatabase.getAllActivitySetting();
//                    if (activity.equals("1")) {
//                        String mychild = dataSnapshot.getValue().toString();
//
//
//                        if (!mychild.equals("-1")) {
//                            String[] a = mychild.split("/");
//                            Intent intent = new Intent(CaptainMapsActivity.this, CaptainMapsActivity.class);
//                            showNotification(CaptainMapsActivity.this, "Captain App \n You Are Late  ", "Ms/Mr (" + a[1] + ")  Note {" + a[0] + "} ", intent);
//
//                            writeInFireBaseCaptainLate();
//                        }
//                    }
//                } catch (Exception y) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        query61.addValueEventListener(listener61);


//        Query query5 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_VALET").child("ifReturn");
//
//        ValueEventListener listener5 = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                try {
//                    // String mychild = dataSnapshot.getValue().toString();
//                    // textView.setText(mychild);
//                    String mychild = dataSnapshot.getValue().toString();
//
//                    if (mychild.equals("1")) {
//                        activity = captainDatabase.getAllActivitySetting();
//                        if (activity.equals("1")) {
//                            Intent intent = new Intent(CaptainMapsActivity.this, CaptainMapsActivity.class);
//                            showNotification(CaptainMapsActivity.this, "Captain App \n I Need My Car ", "لديك طلبات لارجاع السيارة الى الزبون", intent);
//                            importJson.GetReturnOrdersNo();
//                            UpdateInFireBaseCaptainReturn("0");
//                        }
//
//                    }
//                    //  Toast.makeText(CaptainMapsActivity.this, "succ " + mychild, Toast.LENGTH_SHORT).show();
//
//                } catch (Exception y) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        query5.addValueEventListener(listener5);


        goneDeleteLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                importJson.GetOrders();

                captainDatabase.delete();
                deleteCaptain();
                flag = false;
                getFlag = true;
                try {
                    mMap.clear();
                    LatLngListMarker.clear();
                } catch (Exception e) {
                }
                deleteLinear.setVisibility(View.GONE);

            }
        });

        arriveOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(hour.getText().toString())) {
                    if (!TextUtils.isEmpty(min.getText().toString())) {
                        if (!TextUtils.isEmpty(sec.getText().toString())) {

                            String hours = String.format("%02d", Integer.parseInt(hour.getText().toString()));
                            String mins = String.format("%02d", Integer.parseInt(min.getText().toString()));
                            String secs = String.format("%02d", Integer.parseInt(sec.getText().toString()));
                            String time = hours + ":" + mins + ":" + secs;
                            exportJson.updateTransferArriveTime(CaptainMapsActivity.this, time);

                        } else {
                            sec.setError("Required");
                        }
                    } else {
                        min.setError("Required");
                    }
                } else {
                    hour.setError("Required");
                }
            }
        });


        yourCarWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeInFireBaseCaptainWay(clientId);
            }
        });

        arriveToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportJson.updateStatus(CaptainMapsActivity.this, "5", "6");
            }
        });

        CUSTOMER_RECEIVED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportJson.updateStatus(CaptainMapsActivity.this, "5", "6");
            }
        });

        parkingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flags = 2;

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(CaptainMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }
                }

                // getLocation();


                ParkingLoc();

            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flags = 1;

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(CaptainMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }
                }

                // getLocation();

                Directions();

            }

            //lat/lng: (27.44687331582928,33.19101959466934)

        });


        directionR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flags = 1;

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(CaptainMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }
                }

                // getLocation();

                Directions();

            }

            //lat/lng: (27.44687331582928,33.19101959466934)

        });

        carReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("carReturn", "carReturn == ");
                if (isStall) {
                    if (linearReturn.getVisibility() == View.GONE) {
                        importJson.GetReturnOrders();
                    } else if (linearReturn.getVisibility() == View.VISIBLE) {
                        linearReturn.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(CaptainMapsActivity.this, "Please Finish Your Work Before", Toast.LENGTH_SHORT).show();
                }

            }
        });

        arriveToClientReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportJson.updateStatus(CaptainMapsActivity.this, "11", "12");

            }
        });

        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readBarCode();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportJson.updateStatus(CaptainMapsActivity.this, "14", "15");
            }
        });

        call_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call("0786812709");
            }
        });

    }


    void ParkingLoc() {
        locationManager = (LocationManager) CaptainMapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
        //  @SuppressLint("MissingPermission")
        if (ActivityCompat.checkSelfPermission(CaptainMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CaptainMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        try {
            v1 = lastKnownLocation.getLatitude();
            v2 = lastKnownLocation.getLongitude();
        } catch (Exception e) {
        }

        LatLng latLng = new LatLng(v1, v2);
        Log.e("LocationLanLag", "  loo");
        Log.e("LocationLanLag", "  n  " + v1 + "   " + v2);


//        try{
//            mMap.clear();
//            LatLngListMarker.clear();
//        }catch (Exception e){}

        exportJson.updateStatusParking(CaptainMapsActivity.this, "7", "8", "" + latLng);

    }

    void Directions() {
        String a = clientLocation.replace("lat/lng:", "").replace("(", "").replace(")", "").replace(" ", "");

        Log.e("hh", "" + a);

        String[] latLong = a.split(",");
        Log.e("hh[] = ", "" + latLong[0] + "/" + latLong[1]);
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLong[0] + "," + latLong[1] + "&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + latLong[0] + "," + latLong[1]));
            startActivity(intent);
        }
    }


    String[] locationClient() {
        String a = clientLocation.replace("lat/lng:", "").replace("(", "").replace(")", "").replace(" ", "");

        Log.e("hh", "" + a);

        String[] latLong = a.split(",");
        Log.e("hh[] = ", "" + latLong[0] + "/" + latLong[1]);


        try {
            LatLng lat = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 20));
        } catch (Exception e) {
            // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
        }

        return latLong;


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.requestButton:
//                   //barcodeOpen(); progress dialog
////                    if(LatLngListMarker.size()==1) {
//                        openProgressDialog();
////                    }else {
////                        Toast.makeText(DriverMapsActivity.this, "Please Where are You Go ??", Toast.LENGTH_SHORT).show();
////                    }
//
//                    break;

//                case R.id.scanBarcode:
//                    readBarCode();
//                    break;

            }
        }
    };

    public void readBarCode() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Log.d("","Permission not available requesting permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_USE_CAMERA);
        } else {
            //Log.d(TAG,"Permission has already granted");
            barcodeReadPer();
        }

    }

    public void barcodeReadPer() {
        //        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(CaptainMapsActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.cancel));
            } else {

                Log.e("MainActivity", "" + Result.getContents());
                Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();


                if (clientId.equals(Result.getContents())) {
                    exportJson.updateStatus(CaptainMapsActivity.this, "12", "13");
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(CaptainMapsActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("Barcode Reader");
                    sweetAlertDialog.setContentText("This Not Same Client ");
                    sweetAlertDialog.setConfirmText("Ok");
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                        }
                    });
                    sweetAlertDialog.show();
                }


//
//                    Log.e("SweetAlertDialog 724", "" + "JSONTask");
            }


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    void trackingFire(String lat, String lng,String fromLa,String fromLn) {
        try {
            mMap.clear();

        } catch (Exception e) {

        }
        double d1 = Double.parseDouble(lat);
        double d2 = Double.parseDouble(lng);
        double fLa = Double.parseDouble(fromLa);
        double fLn = Double.parseDouble(fromLn);

        LatLng latLng = new LatLng(d1, d2);
        // LatLng latLng2=new LatLng(a1,a2);
        LatLngListMarker.clear();
        LatLngListMarker.add(latLng);
        // LatLngListMarker.add(latLng2);
        location(1);
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng((fLa), (fLn)), new LatLng(d1, d2))
                .width(3)
                .color(Color.RED));

    }


    void tracking(String v, String v3) {
        timer = new Timer();
        v1 = Double.parseDouble(v);
        v2 = Double.parseDouble(v3);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (flag) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // globelFunction.getSalesManInfo(SalesmanMapsActivity.this,2);
                            try {
                                mMap.clear();

                            } catch (Exception e) {

                            }

                            v1 = v1 + 0.000010;
                            v2 = v2 + 0.000050;

                            Log.e("Location", "loc" + v1 + "  " + v2 + "   " + a1 + "   " + a2);
                            LatLng latLng = new LatLng(v1, v2);
                            // LatLng latLng2=new LatLng(a1,a2);
                            LatLngListMarker.clear();
                            LatLngListMarker.add(latLng);
                            // LatLngListMarker.add(latLng2);
                            location(1);
                            Polyline line = mMap.addPolyline(new PolylineOptions()
                                    .add(new LatLng(Double.parseDouble(v), Double.parseDouble(v3)), new LatLng(v1, v2))
                                    .width(3)
                                    .color(Color.RED));

                        }
                    });

                }
//
            }

        }, 0, 1000);
    }

    void openProgressDialog() {
        pdaSweet = new SweetAlertDialog(CaptainMapsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pdaSweet.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdaSweet.setTitleText("Process...");
        pdaSweet.setCancelable(false);
        pdaSweet.show();

        for (int i = 0; i < 300000000; i++) {

        }
        pdaSweet.dismissWithAnimation();

//openDialogOfCaption();

    }

    public void generatorQRCode() {

        try {


            Random r = new Random();
            randomNumber = r.nextInt(999999999); //String.format("%05d", r.nextInt(99999));

            Log.e("randomNumber", "" + randomNumber);

            writeInFireBaseCaptainTime(clientId, singUpUserTableGlobal.getId() + "/" + randomNumber);


            String qrData = singUpUserTableGlobal.getId() + "/" + randomNumber;


            Bitmap bitmaps = encodeAsBitmap("" + qrData, BarcodeFormat.QR_CODE, 200, 200);
            barcode.setImageBitmap(bitmaps);
            regenaratQr.setVisibility(View.GONE);
            barcode.setVisibility(View.VISIBLE);
            CountTime();
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    void CountTime() {

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext

            }

            public void onFinish() {
                //    mTextField.setText("done!");
                regenaratQr.setVisibility(View.VISIBLE);
                barcode.setVisibility(View.GONE);
            }

        }.start();

    }


    private double caldistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


//    void openDialogOfCaption(){
//
//
//        final Dialog dialog = new Dialog(com.example.valetapp.DriverMapsActivity.this,R.style.Theme_Dialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.caption_dialog_info);
//
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        TextView accept=dialog.findViewById(R.id.accept);
//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                scanBarcode.setVisibility(View.VISIBLE);
//                requestButton.setVisibility(View.GONE);
//                tracking();
// dialog.dismiss();
//            }
//        });
//        dialog.show();
//
//    }
//    void barcodeOpen (){
//        final Dialog dialog = new Dialog(DriverMapsActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.qr_);
//
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        Button endParking=dialog.findViewById(R.id.endParking);
//        endParking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
////                Intent intent =new Intent(MapsActivity.this,MainValetActivity.class);
////                startActivity(intent);
//            }
//        });
//        dialog.show();
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap = googleMap;

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                Log.e("locationss",""+latLng.latitude + " : " + latLng.longitude);
//                // Clears the previously touched position
//                googleMap.clear();
//
//                // Animating to the touched position
//                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                googleMap.addMarker(markerOptions);
//            }
//        });

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng point) {
//                // Already two locations
//                if(LatLngListMarker.size()>1){
//                    LatLngListMarker.clear();
//                    mMap.clear();
//                }
//
//                // Adding new item to the ArrayList
//                LatLng latLng=new LatLng(v1,v2);
//                LatLngListMarker.add(latLng);
//                LatLngListMarker.add(point);
//                a1=point.latitude;
//                a2=point.longitude;
//                // Creating MarkerOptions
//                MarkerOptions options = new MarkerOptions();
//
//                // Setting the position of the marker
//                options.position(point);
//
//                /**
//                 * For the start location, the color of marker is GREEN and
//                 * for the end location, the color of marker is RED.
//                 */
//                if(LatLngListMarker.size()==1){
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                }else if(LatLngListMarker.size()==2){
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
//
//                // Add new marker to the Google Map Android API V2
//               // mMap.addMarker(options);
//
//                // Checks, whether start and end locations are captured
////                if(LatLngListMarker.size() >= 2){
////                    mOrigin = LatLngListMarker.get(0);
////                    mDestination = LatLngListMarker.get(1);
////                    drawRoute();
////                }
//                location(0);
//            }
//        });
        location(0);
    }


    public void location(int move) {

        try {

            if (move == 1) {

                mMap.clear();
            }
        } catch (Exception e) {
            Log.e("Problem", "problennnn" + e.getMessage());
        }

        if (move == 0) {
            Location location = getLastKnownLocation();
            v1 = location.getLatitude();
            v2 = location.getLongitude();
            LatLngListMarker.clear();
            LatLngListMarker.add(new LatLng(v1, v2));
        }

        // Add a marker in Sydney and move the camera
        Log.e("mmmmmm", "locationCall");
        LatLng sydney = null;
        builder = new LatLngBounds.Builder();


        for (int i = 0; i < LatLngListMarker.size(); i++) {

            //if (!salesManInfosList.get(i).getLatitudeLocation().equals("0") && !salesManInfosList.get(i).getLongitudeLocation().equals("0")) {
            sydney = LatLngListMarker.get(i);

            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(sydney).title("My Location"));
            builder.include(sydney);
            //}
        }
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        if (move == 0) {
            try {
                bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 20);
                mMap.animateCamera(cu);
            } catch (Exception e) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
            }
        }
        flag = true;
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0));
    }

    public void listOfClient2() {
        tempApp.clear();
        for (int i = 0; i < captainClientTransfers.size(); i++) {
            String a = captainClientTransfers.get(i).getCaptainClientTransfer().getToLocation().replace("lat/lng:", "").replace("(", "").replace(")", "").replace(" ", "");

            Log.e("hh", "" + a);

            float[] f = new float[1];
            String[] latLong = a.split(",");
            Log.e("Location_1", " " + latLong[0] + " " + latLong[1]);
            Log.e("Location_2", " " + v1 + " " + v2);


            Location.distanceBetween(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]), v1, v2, f);
            Log.e("Location", "" + (f[0] / 1000));
            double decKm = f[0] / 1000;
            if (decKm <= 1) {
                tempApp.add(captainClientTransfers.get(i));
                Log.e("Location_3", " true" + " " + decKm + "   " + tempApp.size());
            } else {
                Log.e("Location_4", " false" + " " + decKm + "  " + captainClientTransfers.size() + "  " + tempApp.size());

                // captainClientTransfers.remove(i);
                Log.e("Location_5", " false" + " " + decKm + "  " + captainClientTransfers.size() + "  " + tempApp.size());

            }

        }

        if (tempApp.size() != 0) {
            listOfClient();
        } else {
            listOfClientClearTemp();
        }
//            Location.distanceBetween(captainClientTransfers.get(0));

    }


    public void listOfClient() {
        listAvilable(1);
        //  _1DialogLocation.setVisibility(View.VISIBLE);
//        if( _1DialogLocation.getVisibility()==View.GONE) {
        listAdapterOrder = new ListAdapterOrder(CaptainMapsActivity.this, tempApp);
        orderList.setAdapter(listAdapterOrder);
//        Intent intent = new Intent(CaptainMapsActivity.this, CaptainMapsActivity.class);
//        showNotification(CaptainMapsActivity.this, " I Need Captain ", "Client Need Captain", intent);
//        Log.e("hhh12", "hj");

//            Location.distanceBetween(captainClientTransfers.get(0));

//
//        }else {
//            listAdapterOrder.notifyDataSetChanged();
//            //orderList.setAdapter(listAdapterOrder);
//            Log.e("hhh","hj");
//        }
    }

    public void listOfClientClear() {
        listAvilable(0);

        listAdapterOrder = new ListAdapterOrder(CaptainMapsActivity.this, captainClientTransfers);
        orderList.setAdapter(listAdapterOrder);

    }


    public void listOfClientClearTemp() {
        listAvilable(0);

        listAdapterOrder = new ListAdapterOrder(CaptainMapsActivity.this, tempApp);
        orderList.setAdapter(listAdapterOrder);

    }

    public void listOfReturn() {
        linearReturn.setVisibility(View.VISIBLE);
        //  _1DialogLocation.setVisibility(View.VISIBLE);
        listAdapterReturn = new ListAdapterReturn(CaptainMapsActivity.this, captainClientReturn);
        returnCar.setAdapter(listAdapterReturn);
    }


    public void listAvilable(int i) {


        switch (i) {
            case 0:
                _1DialogLocation.setVisibility(View.GONE);
                _2DialogLocation.setVisibility(View.GONE);
                _3DialogLocation.setVisibility(View.GONE);
                _4DialogLocation.setVisibility(View.GONE);
                _5DialogLocation.setVisibility(View.GONE);
                _6DialogLocation.setVisibility(View.GONE);
                _8DialogLocation.setVisibility(View.GONE);
                _9DialogLocation.setVisibility(View.GONE);
                _10DialogLocation.setVisibility(View.GONE);
                _11DialogLocation.setVisibility(View.GONE);
                _151DialogLocation.setVisibility(View.GONE);
                deleteLinear.setVisibility(View.GONE);
                getFlag = true;
                isStall = true;
                flag = false;
                break;
            case 1:
                if (_1DialogLocation.getVisibility() == View.GONE) {
                    _1DialogLocation.setVisibility(View.VISIBLE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (_2DialogLocation.getVisibility() == View.GONE) {
                    _2DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;
            case 3:
                if (_3DialogLocation.getVisibility() == View.GONE) {
                    _3DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;
            case 4:
                if (_4DialogLocation.getVisibility() == View.GONE) {
                    _4DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;
            case 5:
                if (_5DialogLocation.getVisibility() == View.GONE) {
                    _5DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;
            case 6:
                if (_6DialogLocation.getVisibility() == View.GONE) {
                    _6DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;

            case 8:
                if (_8DialogLocation.getVisibility() == View.GONE) {
                    _8DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;


            case 9:
                if (_9DialogLocation.getVisibility() == View.GONE) {
                    _9DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;


            case 10:
                if (_10DialogLocation.getVisibility() == View.GONE) {
                    _10DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _11DialogLocation.setVisibility(View.GONE);
                }
                break;
            case 11:
                if (_11DialogLocation.getVisibility() == View.GONE) {
                    _11DialogLocation.setVisibility(View.VISIBLE);
                    _1DialogLocation.setVisibility(View.GONE);
                    _2DialogLocation.setVisibility(View.GONE);
                    _3DialogLocation.setVisibility(View.GONE);
                    _4DialogLocation.setVisibility(View.GONE);
                    _5DialogLocation.setVisibility(View.GONE);
                    _6DialogLocation.setVisibility(View.GONE);
                    _8DialogLocation.setVisibility(View.GONE);
                    _9DialogLocation.setVisibility(View.GONE);
                    _10DialogLocation.setVisibility(View.GONE);

                }
                break;

        }
    }


    Bitmap iconSize() {
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.car_icon);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        return smallMarker;
    }

    public void ListShow(String id) {
trackOn=false;

        switch (id) {

            case "0":
                importJson.GetOrders();
                break;

            case "2":
                isStall = false;
                if (_2DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    isStall = false;
                    getFlag = false;
                    _2DialogLocation.setVisibility(View.VISIBLE);
                }
                break;

            case "3":

                if (_3DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    hour.setText("00");
                    min.setText("00");
                    sec.setText("00");
                    getFlag = false;
                    flag = true;
                    try {

                        trackOn=true;
                        String[] loc = locationClient();
                        tracking(loc[0], loc[1]);

                    } catch (Exception r) {

                    }
                    _3DialogLocation.setVisibility(View.VISIBLE);
                }
                break;

            case "4":

                if (_4DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    _4DialogLocation.setVisibility(View.VISIBLE);
                }
                break;
            case "151":
                if (_151DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    //generatorQRCode();
                    _151DialogLocation.setVisibility(View.VISIBLE);
                }
                break;
            case "5":
                if (_5DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    generatorQRCode();
                    _5DialogLocation.setVisibility(View.VISIBLE);
                }
                break;


            case "6":
                if (_6DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    _6DialogLocation.setVisibility(View.VISIBLE);
                }
                break;

            case "7":
                listAvilable(0);
                //  getFlag=false;
                importJson.GetOrders();
                break;

            case "9":
                if (_8DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    _8DialogLocation.setVisibility(View.VISIBLE);
                }
                break;
            case "11":
//                if(_9DialogLocation.getVisibility()==View.GONE) {
//                    listAvilable(0);
//                    _9DialogLocation.setVisibility(View.VISIBLE);
//                }
                if (_5DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    generatorQRCode();
                    _5DialogLocation.setVisibility(View.VISIBLE);
                }
                break;

            case "12":
                if (_10DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    _10DialogLocation.setVisibility(View.VISIBLE);
                }
                break;
            case "13":
                if (_11DialogLocation.getVisibility() == View.GONE) {
                    listAvilable(0);
                    getFlag = false;
                    _11DialogLocation.setVisibility(View.VISIBLE);
                }
                break;
            case "14":
                listAvilable(0);
                getFlag = false;
                importJson.GetOrders();
                break;
            case "-1":
                captainDatabase.delete();
                deleteCaptain();
                flag = false;
                getFlag = true;
                try {
                    mMap.clear();
                    LatLngListMarker.clear();
                } catch (Exception e) {
                }
                importJson.GetOrders();
                break;

            case "555":
                if (deleteLinear.getVisibility() == View.GONE) {
                    listAvilable(0);
                    deleteLinear.setVisibility(View.VISIBLE);
                }

                break;
            case "666":
                if (deleteLinear.getVisibility() == View.GONE) {
                    listAvilable(0);
                    deleteLinear.setVisibility(View.VISIBLE);
                }

                break;
        }

    }

    public void goneReturnList() {

        linearReturn.setVisibility(View.GONE);


    }


    void Call(String number) {

        if (isPermissionGranted()) {
            // You can use the API that requires the permission.
            CallPer();
        } else {
            Toast.makeText(this, "Please Enable Call Phone Permission", Toast.LENGTH_SHORT).show();
        }


    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    void CallPer() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + clientPhoneNo));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (flags == 1) {
                        Directions();
                    } else {
                        ParkingLoc();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Please Enable Access to Location ", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case REQUEST_PHONE_CALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallPer();
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;

            case MY_PERMISSIONS_REQUEST_USE_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  Log.d(TAG,"permission was granted! Do your stuff");
                    barcodeReadPer();
                } else {
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                    // Log.d(TAG,"permission denied! Disable the function related with permission.");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void writeInFireBaseCaptain(ValetFireBaseItem valetFireBase) {
        ValetFireBaseItem valetFireBaseItem = valetFireBase;
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_VALET").setValue(valetFireBaseItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //  Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeInFireBaseCaptainWay(String clientId) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(clientId + "_CarInWay").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //  Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeInFireBaseCaptainTime(String clientId, String Qr) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(clientId + "_QRCode").setValue(Qr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //   Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdateInFireBaseCaptain(String valetFireBase) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_VALET").child("status").setValue(valetFireBase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //     Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdateInFireBaseCaptainReturn(String valetFireBase) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_VALET").child("ifReturn").setValue(valetFireBase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpdateInFireBaseClient(String status, String clientId) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(clientId + "_Client").child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //  Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCaptain() {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_VALET").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //   Toast.makeText(CaptainMapsActivity.this, "Successful Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-02";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public void writeInFireBaseCaptainLate() {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(singUpUserTableGlobal.getId() + "_LATE").setValue("-1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //       Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Location getLastKnownLocation() {
        Location l = null;
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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


}
