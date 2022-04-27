package com.example.captainapp;

import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.IBinder;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.Manifest;
import android.location.Location;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.app.PendingIntent;
import android.app.Service;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class TrackingService extends Service {

    private static final String TAG = TrackingService.class.getSimpleName();
    FirebaseDatabase db;
    public static final String ServiceIntent = "custom.MY_SERVICE";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();
        requestLocationUpdates();
    }

//Create the persistent notification//

    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);

// Create the persistent notification//
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("tracking_enabled_notif")

//Make this notification ongoing so it can’t be dismissed by the user//

                .setOngoing(true)
                .setContentIntent(broadcastIntent);
               // .setSmallIcon(R.drawable.tracking_enabled);
        startForeground(1, builder.build());
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(stopReceiver);

//Stop the Service//

            stopSelf();
        }
    };

//    private void loginToFirebase() {
//
////Authenticate with Firebase, using the email and password we created earlier//
//
//        String email = getString(R.string.test_email);
//        String password = getString(R.string.test_password);
//
////Call OnCompleteListener if the user is signed in successfully//
//
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(
//                email, password).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(Task&lt;AuthResult&gt; task) {
//
////If the user has been authenticated...//
//
//                if (task.isSuccessful()) {
//
////...then call requestLocationUpdates//
//
//                    requestLocationUpdates();
//                } else {
//
////If sign in fails, then log the error//
//
//                    Log.d(TAG, "Firebase authentication failed");
//                }
//            }
//        });
//    }

//Initiate the request to track the device's location//

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();

//Specify how often your app should request the device’s location//

        request.setInterval(10000);
//Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
       // databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(singUpUserTableGlobal.getId()+"_VALET").child("ifReturn");

        final String path = ("firebase_path");
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the app currently has access to the location permission...//

        if (permission == PackageManager.PERMISSION_GRANTED) {

//...then request location updates//

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

//Get a reference to the database, so your app can perform read and write operations//
                    Location location = locationResult.getLastLocation();
                    if (location != null) {

//Save the location data to the database//

                        writeInFireBaseCaptainLate();
                    }
                }
            }, null);
        }
    }


    public void writeInFireBaseCaptainLate(){
        db= FirebaseDatabase.getInstance();
       DatabaseReference databaseReference=db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(singUpUserTableGlobal.getId()+"_LATE").setValue("-1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //       Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}