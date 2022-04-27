package com.example.captainapp.Json;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.captainapp.CaptainDatabase;
import com.example.captainapp.CaptainMapsActivity;
import com.example.captainapp.LogInCaptainActivity;
import com.example.captainapp.MainCaptainActivity;
import com.example.captainapp.Model.CaptainClientTransfer;
import com.example.captainapp.Model.ClientOrder;
import com.example.captainapp.Model.InformationOfClint;
import com.example.captainapp.Model.SingUpUserTable;
import com.example.captainapp.ParkingListActivity;
import com.example.captainapp.ProfileActivate;
import com.example.captainapp.R;
import com.example.captainapp.SingUpCaptain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.captainapp.CaptainMapsActivity.returnText;
import static com.example.captainapp.GlobalVairable.captainClientReturn;
import static com.example.captainapp.GlobalVairable.captainClientTransfers;
import static com.example.captainapp.GlobalVairable.captainParkingList;
import static com.example.captainapp.GlobalVairable.clientId;
import static com.example.captainapp.GlobalVairable.clientLocation;
import static com.example.captainapp.GlobalVairable.clientPhoneNo;
import static com.example.captainapp.GlobalVairable.informationOfClints;
import static com.example.captainapp.GlobalVairable.isOk;
import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

import androidx.annotation.RequiresApi;


public class ImportJson {
SweetAlertDialog swALogIn,swACaptain,swAReturn;
String URL_TO_HIT="";
    Context context;
    CaptainDatabase captainDatabase;
    public ImportJson(Context context) {
        this.context=context;
        captainDatabase=new CaptainDatabase(context);
    }

    public void logInAuth(String userName,String password){
        new LogInAuthAsync(userName,password).execute();
    }
    public  void getRaw(){
        new getRaw().execute();
    }
    public  void getComplete(){
        new getCompleate().execute();
    }


    public  void updateActivate(int activ){
        new updateActivate(activ).execute();
    }

    public void Request(){
        new RequestAsync().execute();
    }

    public void getListOfRequest(){
        new getRequest().execute();
    }


    public void CaptainProfile(){
      //  new CaptainProfile().execute();
    }
    public void getStatuss(){
        new GetStatus().execute();
    }

    public void GetOrders(){
          new GetOrder().execute();
    }


    public void GetParking(){
        new GetParkingList().execute();
    }
    public void GetBitmap(){
        new BitmapImage2().execute();
    }



    public void GetReturnOrders(){
        new GetIfCaptainInWork(1).execute();
    }

    public void GetReturnOrdersNo(){
        new GetReturnOrder(2).execute();
    }
    public void GetIfCaptainInWork(){
        new GetIfCaptainInWork(2).execute();
    }
    private class LogInAuthAsync extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        private String userName = "",password="";


        public LogInAuthAsync(String userName,String password) {
            this.userName = userName.replace("+","");
            this.password=password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swALogIn = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swALogIn.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swALogIn.setTitleText("PleaseWait" );
            swALogIn.setCancelable(false);
            swALogIn.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                String link = "http://"+ip+"/api/ValCaptain/getCaptainAuthuraization?";


                String data = "phoneNo="+userName+
                "&password="+password;
                URL url = new URL(link+data );


//
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("GET");
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            swALogIn.dismissWithAnimation();
            JSONObject result = null;
            String impo = "";
            boolean found=false;
            if (s != null) {
                if (s.contains("PhoneNo")) {
                    isOk=true;
                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");

                    //{"CAPTAINS":[{"PHONE_NO":"+962222222222","PASSWORD":"28485","PROFILE_PIC":null}

                    try {
                        JSONObject j=new JSONObject(s);

                        SingUpUserTable  singUpUserTable=new SingUpUserTable();
                        singUpUserTable.setId(j.getString("id"));

                        singUpUserTable.setPHONE_NO(j.getString("PhoneNo"));

                        singUpUserTable.setUserName(j.getString("UserName"));
                        singUpUserTable.setPASSWORD(j.getString("Password"));
//                        singUpUserTable.set(j.getString("Activiat"));
                        singUpUserTable.setPROFILE_PIC(j.getString("PROFILE_PIC"));
                        singUpUserTable.setIDENTITY_PIC(j.getString("IDENTITY_PIC"));
                        singUpUserTable.setDRIVING_LICENCE_PIC(j.getString("DRIVING_LICENCE_PIC"));
                        singUpUserTable.setPERMIT_PIC(j.getString("PERMIT_PIC"));
                        singUpUserTable.setCAR_LICENCE_PIC(j.getString("CAR_LICENCE_PIC"));
                        singUpUserTable.setPAYMENT_300_PIC(j.getString("PAYMENT_300_PIC"));
                        singUpUserTable.setPAYMENT_50_PIC(j.getString("PAYMENT_50_PIC"));
                        singUpUserTable.setDESEASE_FREE_PIC(j.getString("DESEASE_FREE_PIC"));
                        singUpUserTable.setCRIMINAL_RECORE_PIC(j.getString("CRIMINAL_RECORE_PIC"));
                        try {
                            singUpUserTable.setActivityUser(j.getInt("ActivityUser"));
                        }catch (Exception r){
                            singUpUserTable.setActivityUser(0);
                        }


//                        if(found){
                        LogInCaptainActivity logInValet=(LogInCaptainActivity) context;

//                        if(singUpUserTable.getActivityUser()==0) {
                            singUpUserTableGlobal = singUpUserTable;
                            logInValet.intentToMain();
                            getRaw();
//                            updateActivate(1);
//                        }else {
//                            Toast.makeText(logInValet, "This Account Open In another Device", Toast.LENGTH_LONG).show();
//                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                } else {
                    isOk=true;
                    Toast.makeText(context, "User Name Or Password inCorrect", Toast.LENGTH_SHORT).show();

                    Log.e("onPostExecute", "" + s.toString());
                }
            }else {
                isOk=true;
            }
        }
    }

    private class getRaw extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        private String userName = "",password="";


        public getRaw() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            swALogIn = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            swALogIn.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            swALogIn.setTitleText("PleaseWait" );
//            swALogIn.setCancelable(false);
//            swALogIn.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                String link = "http://"+ip+"/api/ValCaptain/getCaptainEndRawId?";


                String data = "CaptainEndRaw="+singUpUserTableGlobal.getId();

                URL url = new URL(link+data );


//
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("GET");
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // swALogIn.dismissWithAnimation();

            isOk=true;
            if (s != null) {
                if (s.contains("id")) {
captainDatabase=new CaptainDatabase(context);

                    try{
                        captainDatabase.delete();
                    }catch (Exception r){

                    }

                    String id=s.replace("id","").replaceAll("\"","").replaceAll("\\n","").replaceAll(" ","");;
                    if(!TextUtils.isEmpty(id)) {
                        captainDatabase.addSetting(id);
                    }else {
                        captainDatabase.addSetting("-1");
                        captainDatabase.delete();
                    }

                } else {
                    Toast.makeText(context, "User Name Or Password inCorrect", Toast.LENGTH_SHORT).show();

                    Log.e("onPostExecute", "" + s.toString());
                }
            }
        }
    }
    private class getCompleate extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        private String userName = "",password="";


        public getCompleate() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            swALogIn = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            swALogIn.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            swALogIn.setTitleText("PleaseWait" );
//            swALogIn.setCancelable(false);
//            swALogIn.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                String link = "http://"+ip+"/api/ValCaptain/getCompleate?";


                String data = "idCapCompleat="+singUpUserTableGlobal.getId()+
                        "&Nore=22";

                URL url = new URL(link+data );


//
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("GET");
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
                Log.e("url____",""+link+data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // swALogIn.dismissWithAnimation();

            isOk=true;
            if (s != null) {
                if (s != null) {
                    if (s.contains("rating")) {
                        String com=s.replace("rating =","").replace("\"","").replace(" ","");//"rating = 2";
                        MainCaptainActivity mainCaptainActivity=(MainCaptainActivity)  context;
                        mainCaptainActivity.completeText(com);
                        Log.e("url____",""+com);
                        isOk=true;
                    } else {

                        isOk=true;

                    }
                }else {
                    isOk=true;
                }
            }
        }
    }

    private class updateActivate extends AsyncTask<String, String, String> {

      int activate;
        public updateActivate(int activate) {

            this.activate=activate;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {   //int idT, int idCaptain, int idS, int idClient, int status
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81

                URL_TO_HIT = "http://"+ip+"/api/ValCaptain/updateActiviatyUser?idCaptainAc="+singUpUserTableGlobal.getId()+
                        "&ActiviatyUser="+activate;


//                }
            } catch (Exception e) {
                Log.e("URL_TO_HIT99", "JsonResponse\t" + URL_TO_HIT);
            }


            Log.e("URL_TO_HITnew", "JsonResponse\t" + URL_TO_HIT);
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPut request = new HttpPut();
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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
            //   swASingUp.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Success_updating_activiaty")) {

                    isOk=true;
                } else {

                    isOk=true;

                }
            }else {
                isOk=true;
            }
        }

    }



    private class RequestAsync extends AsyncTask<String, String, String> {

        private String custId = "";

//        public LogInAuthAsync(String customerId) {
//            this.custId = customerId;
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {

                URL_TO_HIT = "http://"+"5.189.130.98:8085".trim() +"/importt.php?FLAG=3&CAPTAIN_NO=";//+singUpUserTableGlobal.getPHONE_NO().replace("+","");
//                }
            } catch (Exception e) {

            }

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

                        Toast.makeText(context, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
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
                if (s.contains("REQUEST_VALET_LOG")) {

                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");

                    Gson gson = new Gson();
                    InformationOfClint gsonObj = gson.fromJson(s, InformationOfClint.class);
//                    drivenList.clear();
//                    drivenList.addAll(gsonObj.getREQUEST_VALET_LOG());
//                    MainValetActivity mainValetActivity = (MainValetActivity) context;
//                    mainValetActivity.fillItemAdapter();


                } else {
//                    drivenList.clear();
//                    MainValetActivity mainValetActivity = (MainValetActivity) context;
//                    mainValetActivity.fillItemAdapter();
                    Log.e("onPostExecute", "" + s.toString());
                }
            }else {
//                drivenList.clear();
//                MainValetActivity mainValetActivity = (MainValetActivity) context;
//                mainValetActivity.fillItemAdapter();
//                Log.e("onPostExecute", "" + s.toString());
            }
        }

    }

    private class getRequest extends AsyncTask<String, String, String> {

        private String custId = "";

//        public LogInAuthAsync(String customerId) {
//            this.custId = customerId;
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {

                URL_TO_HIT = "http://"+"5.189.130.98:8085".trim() +"/importt.php?FLAG=5&CAPTAIN_NO=";//+singUpUserTableGlobal.getPHONE_NO().replace("+","");
//                }
            } catch (Exception e) {

            }

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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
                if (s.contains("REQUEST_VALET_LOG")) {
                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");


                    Gson gson = new Gson();
                    InformationOfClint gsonObj = gson.fromJson(s, InformationOfClint.class);
    //                RequestList.clear();
//                    RequestList.addAll(gsonObj.getREQUEST_VALET_LOG());

                 // mainValetActivity.fillItemAdapterDialog();


                    try{
                       // RequestListOfItem requestListOfItem = (RequestListOfItem) context;
                     //   requestListOfItem.notification();

                    }catch (Exception e){
                        Log.e("onPostExecuteF=5 ", "" + e.toString());
                    }


                } else {
                    //RequestList.clear();
                    Log.e("onPostExecute", "" + s.toString());
                }
            } else {
                //RequestList.clear();
               // Log.e("onPostExecute", "" + s.toString());
            }
        }

    }

    private class CaptainProfile extends AsyncTask<String, String, String> {

        private String custId = "";

//        public LogInAuthAsync(String customerId) {
//            this.custId = customerId;
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {

                URL_TO_HIT = "http://"+"5.189.130.98:8085".trim() +"/importt.php?FLAG=4";
//                }
            } catch (Exception e) {

            }

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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
                if (s.contains("CAPTAINS")) {

                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");

                    Gson gson = new Gson();
                    InformationOfClint gsonObj = gson.fromJson(s, InformationOfClint.class);
//                    drivenList.clear();
//                    drivenList.addAll(gsonObj.getREQUEST_VALET_LOG());
//                    MainValetActivity mainValetActivity = (MainValetActivity) context;
//                    mainValetActivity.fillItemAdapter();


                } else Log.e("onPostExecute", "" + s.toString());
            }
        }

    }

    private class GetOrder extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/getOrder?apmid="+singUpUserTableGlobal.getId().toString()+"&acp=6";
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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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

                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");


                    Gson gson = new Gson();

                    Type collectionType = new TypeToken<Collection<CaptainClientTransfer>>(){}.getType();
                    Collection<CaptainClientTransfer> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                    captainClientTransfers.clear();
                    // captainClientTransfers.addAll(enums.getOrderList());
                    captainClientTransfers= (List<CaptainClientTransfer>) enums;
                  //  Log.e( "captainClientTransfers "," = "+captainClientTransfers.get(0).getClients().getCAR_COLOR());
                    CaptainMapsActivity mainValetActivity = (CaptainMapsActivity) context;
                    mainValetActivity.listOfClient2();

                    isOk=true;
                } else {
                    CaptainMapsActivity mainValetActivity = (CaptainMapsActivity) context;
                    mainValetActivity.listOfClientClear();
                    isOk=true;
                    Log.e("onPostExecute", "" + s.toString());

                }
            }else {
                isOk=true;
            }
        }

    }

    private class GetParkingList extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/getParkingList?idCapsParking="+singUpUserTableGlobal.getId().toString()+"&l=6&r=1";
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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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

                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");


                    Gson gson = new Gson();

                    Type collectionType = new TypeToken<Collection<CaptainClientTransfer>>(){}.getType();
                    Collection<CaptainClientTransfer> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                    captainParkingList.clear();
                    // captainClientTransfers.addAll(enums.getOrderList());
                    captainParkingList= (List<CaptainClientTransfer>) enums;
                    //  Log.e( "captainClientTransfers "," = "+captainClientTransfers.get(0).getClients().getCAR_COLOR());
                 try {
                     ParkingListActivity mainValetActivity = (ParkingListActivity) context;
                     mainValetActivity.ListOfParking();
                 }catch (Exception y){

                 }
                    isOk=true;
                } else {
                    isOk=true;
                    Log.e("onPostExecute", "" + s.toString());

                }
            }else {
                isOk=true;
            }
        }

    }

    private class GetReturnOrder extends AsyncTask<String, String, String> {

        int flags;
        public GetReturnOrder(int flags) {
            this.flags=flags;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swAReturn = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swAReturn.getProgressHelper().setBarColor(Color.parseColor("#FF4CAF50"));
            swAReturn.setTitleText("PleaseWait" );
            swAReturn.setCancelable(false);
            swAReturn.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/getReturnOrder?asa="+singUpUserTableGlobal.getId();
//                }
            } catch (Exception e) {

            }

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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
            swAReturn.dismissWithAnimation();
            if (s != null) {
                if (s.contains("ClientName")) {

                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");

                    Gson gson = new Gson();
                    try {


                        JSONArray jsonArray = new JSONArray(s);

                        Type collectionType = new TypeToken<Collection<CaptainClientTransfer>>() {
                        }.getType();
                        Collection<CaptainClientTransfer> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                        captainClientReturn.clear();
                        // captainClientTransfers.addAll(enums.getOrderList());
                        captainClientReturn = (List<CaptainClientTransfer>) enums;
                        CaptainMapsActivity mainValetActivity = (CaptainMapsActivity) context;
                        if (flags == 1) {
                            mainValetActivity.listOfReturn();
                            returnText.setText("Return \n ("+captainClientReturn.size()+")");
                        } else {
                            returnText.setText("Return \n ("+captainClientReturn.size()+")");
                        }
                    }catch (Exception E){

                    }

                    isOk=true;
                } else {
                    returnText.setText("Return \n ("+0+")");
                    isOk=true;
                    Log.e("onPostExecute", "" + s.toString());

                }
            }else {
                isOk=true;
            }
        }

    }
    private class GetIfCaptainInWork extends AsyncTask<String, String, String> {
int flag=-1;
        public GetIfCaptainInWork(int flag) {

            swACaptain = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swACaptain.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swACaptain.setTitleText("PleaseWait" );
            swACaptain.setCancelable(false);
            swACaptain.show();
            this.flag=flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/getIfCaptainInWor?captainIF="+singUpUserTableGlobal.getId();
                Log.e("URL_TO_HIT", "carReturn\t" + URL_TO_HIT);
//                }
            } catch (Exception e) {

            }

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
                Log.e("carReturn", "carReturn\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
            swACaptain.dismissWithAnimation();
            if (s != null) {
                if (s.contains("ClientName")) {

                    if(flag==1) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Can Not Open New Work Before Finish Your Work  !!!")
                                .setContentText("")
//                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                }
//                            })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.dismissWithAnimation();


                                    }
                                })
                                .show();
                    }
                    isOk=true;
                } else {

                    if(flag==1) {
                        new GetReturnOrder(1).execute();
                    }else if(flag==2){
                       GetOrders();
                    }
                    isOk=true;
                    Log.e("onPostExecute", "carReturn" );

                }
            }else {
                isOk=true;
                Log.e("onPostExecute77", "carReturn");
            }
        }

    }
    private class GetStatus extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {

                CaptainDatabase valetDatabase=new CaptainDatabase(context);
                String id=valetDatabase.getAllSetting();
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/getStatusC_P_id?idpS="+id.replace(" ","").replaceAll("\"","");
//                }
            } catch (Exception e) {
                Log.e("URL_TO_HIT111", "JsonResponse\t" + URL_TO_HIT);
            }


            Log.e("URL_TO_HIT", "JsonResponse\t" + URL_TO_HIT);
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

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
          //  isOk=true;

            Log.e("listhh123 ","gll= "+s);
            if (s != null) {
                if (s.contains("captainStatus")) {

                    Log.e("listhh1234 ","gll= ");
                    //{"id":2063,"CaptainName":"6","CaptainPhoneNo":"9626","CaptainId":"2013","TransferId":"3047","CaptainRate":"3","CurrentLocation":"3","ClientName":"9","ClientPhoneNo":"i","ClientId":"3015","Date":"10/10/2021 10:58:32 PM","TimeToArraive":"1111","Status":"1","StatusRaw":"4"}
                    s=s.replace("<br /><b>Warning</b>:  oci_connect(): OCI_SUCCESS_WITH_INFO: ORA-28002: the password will expire within 7 days in <b>C:\\xampp\\htdocs\\importt.php</b> on line <b>3</b><br />","");

                    Gson gson = new Gson();
//captainStatus
                    try {
                        JSONObject e=new JSONObject(s);

                        JSONObject d=e.getJSONObject("captainStatus");
                        String va=d.getString("StatusRaw");
                        clientId=d.getString("ClientId");
                        clientPhoneNo=d.getString("ClientPhoneNo");
                        clientLocation=d.getString("CurrentLocation");
                        JSONObject cli=e.getJSONObject("clientsInfo");

                        informationOfClints= gson.fromJson(String.valueOf(cli), InformationOfClint.class);

                        Log.e("listhh","gll= "+va);

                        CaptainMapsActivity mainValetActivity = (CaptainMapsActivity) context;
                        mainValetActivity.ListShow(va);
                    } catch (JSONException e) {
                        Log.e("listhhrr","gll= "+e.toString());

                        e.printStackTrace();
                    }



                } else {
                    CaptainMapsActivity mainValetActivity = (CaptainMapsActivity) context;
                    mainValetActivity.ListShow("0");
                    Log.e("onPostExecute", "" + s.toString());
                }
            }else {
                CaptainMapsActivity mainValetActivity = (CaptainMapsActivity) context;
                mainValetActivity.ListShow("0");
            }
        }

    }

    private class BitmapImage2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... pictures) {

            try {

//                if (!singUpUserTableGlobal.getCRIMINAL_RECORE_PIC().equals("null")) {
                String ip=captainDatabase.getAllIPSetting();
                    for (int i = 0; i < 10; i++) {
                        Bitmap bitmap = null;
                        URL url;
                        switch (i) {
                            case 0:
                                if (singUpUserTableGlobal.getPROFILE_PIC() != null) {//http://192.168.2.17:8088/woody/images/2342_img_1.png
                                    url = new URL("http://" + ip + "/imagesFile/" +singUpUserTableGlobal.getId()+"_PROFILE_PIC_1.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                      singUpUserTableGlobal.setPROFILE_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic11(bitmap);
                                    }
                                }
                                break;
                            case 1:
                                if (singUpUserTableGlobal.getIDENTITY_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_IDENTITY_PIC_2.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setIDENTITY_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic22(bitmap);
                                    }
                                }
                                break;
                            case 2:
                                if (singUpUserTableGlobal.getDRIVING_LICENCE_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_DRIVING_LICENCE_PIC_3.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setDRIVING_LICENCE_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic33(bitmap);
                                    }
                                }
                                break;
                            case 3:
                                if (singUpUserTableGlobal.getPERMIT_PIC() != null) {
                                    url = new URL("http://" + ip + "/imagesFile/" +singUpUserTableGlobal.getId()+"_PERMIT_PIC_4.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setPERMIT_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic44(bitmap);
                                    }
                                }
                                break;
                            case 4:
                                if (singUpUserTableGlobal.getCAR_LICENCE_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_CAR_LICENCE_PIC_5.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setCAR_LICENCE_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic55(bitmap);
                                    }
                                }
                                break;
                            case 5:
                                if (singUpUserTableGlobal.getPAYMENT_300_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_PAYMENT_300_PIC_6.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setPAYMENT_300_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic66(bitmap);
                                    }
                                }
                                break;
                            case 6:
                                if (singUpUserTableGlobal.getPAYMENT_50_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_PAYMENT_50_PIC_7.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setPAYMENT_50_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic77(bitmap);
                                    }
                                }
                                break;
                            case 7:
                                if (singUpUserTableGlobal.getDESEASE_FREE_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_DESEASE_FREE_PIC_8.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setDESEASE_FREE_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic88(bitmap);
                                    }
                                }
                                break;
                            case 8:
                                if (singUpUserTableGlobal.getCRIMINAL_RECORE_PIC() != null) {
                                    url = new URL("http://" + ip  + "/imagesFile/" +singUpUserTableGlobal.getId()+"_CRIMINAL_RECORE_PIC_9.jpg");
                                    try {
                                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        singUpUserTableGlobal.setCRIMINAL_RECORE_PIC_Bitmap(bitmap);
                                    } catch (Exception e) {
//                                        pictures[0].setPic88(bitmap);
                                    }
                                }
                                break;
                        }
                    }

//                    Bitmap finalBitmap = bitmap;


//                }
            } catch (Exception e) {
                Log.e("fromclass2", "exception:doInBackground " + e.getMessage());
                return "exception";
            }
            return "null";// BitmapFactory.decodeStream(in);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("fromclass2", "exception:onPostExecute: " + s);
            MainCaptainActivity profileActivate=(MainCaptainActivity) context;
            profileActivate.fillImage();
            if (s.contains("exception"))
                Toast.makeText(context, "No image found!", Toast.LENGTH_SHORT).show();

        }
    }


}
