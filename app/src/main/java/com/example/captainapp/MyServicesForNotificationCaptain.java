package com.example.captainapp;

import static com.example.captainapp.GlobalVairable.captainClientTransfers;
import static com.example.captainapp.GlobalVairable.isOk;
import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.captainapp.Model.CaptainClientTransfer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;

public class MyServicesForNotificationCaptain extends Service {
    private static final String TAG = "BackgroundFireServiceS";
    MediaPlayer player;
    FirebaseDatabase db;
    static String id;
    String activity;
    public static final String ServiceIntent = "MyServices";
    public static boolean ServiceWork = true;
    CaptainDatabase captainDatabase;
    Timer timer;
    DatabaseReference databaseReference;
    public static List<CaptainClientTransfer> captainClientTransfersService=new ArrayList<>();
    double v1,v2;

    public IBinder onBind(Intent arg0) {
        Log.e(TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(MyServicesForNotificationCaptain.this);
        captainDatabase = new CaptainDatabase(this);
        //userService=new UserService();
        id=captainDatabase.getAllUser();
        allTaskInFireBase();
        Log.e(TAG, "onCreate() , service started..."+id);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //requestLocationUpdates();
        id=captainDatabase.getAllUser();
        allTaskInFireBase();
        onDestroy();
        Log.e(TAG, "onStartCommand() , service started..."+id);

        return START_STICKY;

    }


    public IBinder onUnBind(Intent arg0) {
        Log.e(TAG, "onUnBind()");
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        // TODO Auto-generated method stub
        Log.e(TAG, "onStop() , service Stop..."+id);
        return super.stopService(name);

    }

    public void onPause() {
        Log.e(TAG, "onPause()");
    }

    @Override
    public void onDestroy() {
        id=captainDatabase.getAllUser();
        allTaskInFireBase();
        Log.e(TAG, "onCreatedd() , service stopped..."+id);
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory()");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        allTaskInFireBase();
        Log.e(TAG, "In onTaskRemoved");
    }


    void allTaskInFireBase() {
        id=captainDatabase.getAllUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

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
                                String idT = captainDatabase.getAllSetting();

                                if (TextUtils.isEmpty(idT)) {

                                   new GetOrder().execute();
                                }
//                                if (getFlag) {
//                                    importJson.GetOrders();
//                                }
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


        Query query61 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(id + "_LATE");
//        query2.

        ValueEventListener listener61 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    activity = captainDatabase.getAllActivitySetting();
                    if (activity.equals("1")) {
                        String mychild = dataSnapshot.getValue().toString();
                        if (!mychild.equals("-1")) {
                            String[] a = mychild.split("/");
                            String idr=dataSnapshot.getKey().toString();
                            Log.e(TAG,"root"+idr+"    "+id+"_LATE");
                            if((id+"_LATE").equals(idr)) {
                                Intent intent = new Intent(MyServicesForNotificationCaptain.this, CaptainMapsActivity.class);
                                showNotification(MyServicesForNotificationCaptain.this, "Captain App \n You Are Late  ", "Ms/Mr (" + a[1] + ")  Note {" + a[0] + "} ", intent);
                                writeInFireBaseCaptainLate();
                            }

                        }
                    }
                } catch (Exception y) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query61.addValueEventListener(listener61);


        Query query5 = databaseReference.child("ValetAppRealDB").child("StatusValetGroup").child(id + "_VALET").child("ifReturn");

        ValueEventListener listener5 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    // String mychild = dataSnapshot.getValue().toString();
                    // textView.setText(mychild);
                    String mychild = dataSnapshot.getValue().toString();

                    if (mychild.equals("1")) {
                        activity = captainDatabase.getAllActivitySetting();
                        if (activity.equals("1")) {
                            Log.e(TAG,"ifReturn99"+"    "+id+"_VALET");
                            String idr=dataSnapshot.getRef().getParent().toString();
                            Log.e(TAG,"ifReturn"+idr+"    "+id+"_VALET");
                            Log.e(TAG,"ifReturn2"+idr.substring(idr.lastIndexOf("/")).replace("/",""));

                            idr=idr.substring(idr.lastIndexOf("/")).replace("/","");
                            if((id+"_VALET").equals(idr)) {
                                Intent intent = new Intent(MyServicesForNotificationCaptain.this, CaptainMapsActivity.class);
                                showNotification(MyServicesForNotificationCaptain.this, "Captain App \n I Need My Car ", "لديك طلبات لارجاع السيارة الى الزبون", intent);
                                // importJson.GetReturnOrdersNo();
                                UpdateInFireBaseCaptainReturn("0");
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
        query5.addValueEventListener(listener5);


    }

    public void UpdateInFireBaseCaptainReturn(String valetFireBase) {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ValetAppRealDB");
        databaseReference.child("StatusValetGroup").child(id + "_VALET").child("ifReturn").setValue(valetFireBase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //  Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-03";
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
        databaseReference.child("StatusValetGroup").child(id + "_LATE").setValue("-1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //       Toast.makeText(CaptainMapsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class GetOrder extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
String URL_TO_HIT="";
            try {

//                if (!ipAddress.equals("")) {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/getOrder?apmid="+id.toString()+"&acp=6";
//                }
            } catch (Exception e) {

            }

            Log.e("getorder = ", "" + URL_TO_HIT.toString());
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_allcheques", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                    //    Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            if (s != null) {
                if (s.contains("ClientName")) {
                    Gson gson = new Gson();

                    Type collectionType = new TypeToken<Collection<CaptainClientTransfer>>(){}.getType();
                    Collection<CaptainClientTransfer> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                    captainClientTransfersService.clear();
                    // captainClientTransfers.addAll(enums.getOrderList());
                    captainClientTransfersService= (List<CaptainClientTransfer>) enums;
                    //  Log.e( "captainClientTransfers "," = "+captainClientTransfers.get(0).getClients().getCAR_COLOR());

                    listOfClient2();

                } else {


                    Log.e("onPostExecute", "" + s.toString());

                }
            }else {
                isOk=true;
            }
        }

    }



    public void listOfClient2() {

        try {
            Location location = getLastKnownLocation();
            v1 = location.getLatitude();
            v2 = location.getLongitude();
            Log.e("Location_1S", " " + v1+ " " + v2);

        }catch (Exception e){

        }
        for (int i = 0; i < captainClientTransfersService.size(); i++) {
            String a = captainClientTransfersService.get(i).getCaptainClientTransfer().getToLocation().replace("lat/lng:", "").replace("(", "").replace(")", "").replace(" ", "");

            Log.e("hh", "" + a);

            float[] f = new float[1];
            String[] latLong = a.split(",");
            Log.e("Location_1", " " + latLong[0] + " " + latLong[1]);
            Log.e("Location_2", " " + v1 + " " + v2);


            Location.distanceBetween(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]), v1, v2, f);
            Log.e("Location", "" + (f[0] / 1000));
            double decKm = f[0] / 1000;
            if (decKm <= 1) {
                Intent intent = new Intent(MyServicesForNotificationCaptain.this, CaptainMapsActivity.class);
                showNotification(MyServicesForNotificationCaptain.this, " I Need Captain ", "Client Need Captain", intent);

                Log.e("Location_3S", " true" + " " + decKm + "   " );
            } else {
                Log.e("Location_4S", " false" + " " + decKm + "  " + captainClientTransfersService.size() );

                // captainClientTransfers.remove(i);
                Log.e("Location_5S", " false" + " " + decKm + "  " + captainClientTransfersService.size() );

            }

        }

//            Location.distanceBetween(captainClientTransfers.get(0));

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