package com.example.captainapp.Json;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.example.captainapp.CaptainDatabase;
import com.example.captainapp.CaptainMapsActivity;
import com.example.captainapp.Model.CaptainClientTransfer;
import com.example.captainapp.Model.ClientOrder;
import com.example.captainapp.Model.InformationOfClint;
import com.example.captainapp.Model.SingUpUserTable;
import com.example.captainapp.Model.ValetFireBaseItem;
import com.example.captainapp.R;
import com.example.captainapp.SingUpCaptain;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.captainapp.GlobalVairable.clientId;
import static com.example.captainapp.GlobalVairable.ids;
import static com.example.captainapp.GlobalVairable.isOk;
import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;


public class ExportJson {

    SweetAlertDialog swASingUp,swAarraive,swAarraive2,swAParking,swADelete,swAccept,sweetAlertDialogArr,sweetAlertDialogStu,sweetAlertDialogStuPar,sweetAlertDialogStu2;
    String URL_TO_HIT="";
    Context context;
    CaptainDatabase captainDatabase;

    public  ExportJson(Context context){
        this.context=context;
        this.captainDatabase=new CaptainDatabase(context);
    }

    public void SingUpCaptain (Context context, JSONObject jsonObject, SingUpUserTable singUpUserTable){

        new SingUpCaptainAsync_2(context,jsonObject,singUpUserTable).execute();

    }
    public void SavePic (Context context,String image){

        new savePic(context,image).execute();

    }
    public void addOrder (Context context, ClientOrder clientOrder){

        new AddOrderToCaptainAsync(context,clientOrder).execute();

    }
    public  void updateTransferArriveTime(Context context,String arriveTime){
        new updateAriveTime(context,arriveTime).execute();
    }

    public  void updateStatus(Context context,String CPS,String CT){
        new updateStatus(context,CPS,CT).execute();
    }

    public  void updateStatusParking(Context context,String CPS,String CT,String Location){
        new updateStatusParking(context,CPS,CT,Location).execute();
    }

    public  void updateStatus2(Context context, String CPS, String CT, String cap, CaptainClientTransfer clientOrder){
        new updateStatus2(context,CPS,CT,cap,clientOrder).execute();
    }

    public void CarParkedAsync (Context context, InformationOfClint jsonObject, String intentReSend){

        new CarParkedAsync(context,jsonObject,intentReSend).execute();

    }

    public void deleteAsync (Context context,InformationOfClint jsonObject){

        new deleteAsync(context,jsonObject).execute();

    }
    public void CarArrivedAsync (Context context,InformationOfClint jsonObject){

        new CarArrivedAsync(context,jsonObject).execute();

    }

    public void CarArrivedAcceptAsync (Context context,InformationOfClint jsonObject){

        new CarArrivedAcceptAsync(context,jsonObject).execute();

    }

    public void ClintCaptainStatus (Context context,JSONObject jsonObject){

        new ClintCaptainStatusAsync(context,jsonObject).execute();

    }

    private class SingUpCaptainAsync_2 extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;


        JSONObject jsonObject;
        SingUpUserTable singUpUserTable;

        public SingUpCaptainAsync_2(Context context, JSONObject jsonObject, SingUpUserTable singUpUserTable) {

            this.singUpUserTable = singUpUserTable;
            this.jsonObject = jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swASingUp = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swASingUp.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swASingUp.setTitleText("PleaseWait" );
            swASingUp.setCancelable(false);
            swASingUp.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                String link = "http://"+ip+"/api/ValCaptain/saveCaptainUser?";

                String data = "phoneNo=" + singUpUserTable.getPHONE_NO().replace("+","")
                        + "&userName=" + singUpUserTable.getUserName()
                        + "&Password=" + singUpUserTable.getPASSWORD()
                        //  + "&Activiat=" + "0"
                        + "&PROFILE_PIC=" + "null"
                        + "&IDENTITY_PIC=" + "null"
                        + "&DRIVING_LICENCE_PIC=" + "null"
                        + "&PERMIT_PIC=" + "null"
                        + "&CAR_LICENCE_PIC=" + "null"
                        + "&PAYMENT_300_PIC=" + "null"
                        + "&PAYMENT_50_PIC=" + "null"
                        + "&DESEASE_FREE_PIC=" + "null"
                        + "&CRIMINAL_RECORE_PIC=" + "null";
                URL url = new URL(link+data );


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

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
            swASingUp.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Success_Add_Captain")) {
                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    SingUpCaptain singUpValet = (SingUpCaptain) context;
                    singUpValet.colorChange(4);

                    isOk=true;
                } else  {
                    isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sing Up Fail Please Try Again !!!")
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
            }else {isOk=true;}
        }
    }


    private class SingUpCaptainAsync extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        JSONObject jsonObject;
        SingUpUserTable singUpUserTable;

        public SingUpCaptainAsync(Context context, JSONObject jsonObject, SingUpUserTable singUpUserTable) {

            this.singUpUserTable = singUpUserTable;
            this.jsonObject = jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swASingUp = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swASingUp.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swASingUp.setTitleText("PleaseWait" );
            swASingUp.setCancelable(false);
            swASingUp.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                 URL_TO_HIT = "http://"+ip.trim()+"/api/ValCaptain/saveUser?more=2";
            } catch (Exception e) {

            }


            Log.e("URL_TO_HIT",""+URL_TO_HIT+"   "+jsonObject.toString());
            try {


                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(1);
                // nameValuePairs.add(new BasicNameValuePair("_ID", "3"));

                nameValuePairs.add(new BasicNameValuePair("",jsonObject.toString()));

                // nameValuePairs.add(new BasicNameValuePair("SERIAL",convertToEnglish(jsonObject.getSERIAL())));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                Log.e("URL_TO_HIT",""+URL_TO_HIT+"  "+jsonObject.toString());

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
                Log.e("exportCarPark", "JsonResponse\t" + JsonResponse);

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
            swASingUp.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Success_Add_Captain")) {
                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    SingUpCaptain singUpValet = (SingUpCaptain) context;
                    singUpValet.colorChange(4);

                    isOk=true;
                } else  {
isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sing Up Fail Please Try Again !!!")
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
            }else {isOk=true;}
        }
    }
    private class savePic extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;


        String pic="";

        public savePic(Context context,String pic) {

            this.pic=pic;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            swASingUp = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            swASingUp.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            swASingUp.setTitleText("PleaseWait" );
//            swASingUp.setCancelable(false);
//            swASingUp.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String link = "http://192.168.1.101:81/api/ValCaptain/savePic?";

                String data = "image=" + pic;
                URL url = new URL(link+data );


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

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
            swASingUp.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Success_Add_Captain")) {
                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    SingUpCaptain singUpValet = (SingUpCaptain) context;
                    singUpValet.colorChange(4);

                    isOk=true;
                } else  {
                    isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sing Up Fail Please Try Again !!!")
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
            }else {isOk=true;}
        }
    }

    private class AddOrderToCaptainAsync extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;


        JSONObject jsonObject;
        ClientOrder clientOrder;

        public AddOrderToCaptainAsync(Context context, ClientOrder clientOrder) {

            this.clientOrder = clientOrder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swASingUp = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swASingUp.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swASingUp.setTitleText("PleaseWait" );
            swASingUp.setCancelable(false);
            swASingUp.show();
isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                String link = "http://"+ip+"/api/ValCaptain/SaveStatusOfCaptain?";
//String captainName,String captainPhoneNo,String captainId,String TransferId,
//            String status ,String captainRate,String currentLocation,String clientName ,String clientNo,String date, String timeToArraive
                String data = "captainName=" + clientOrder.getCaptainName()
                        + "&captainPhoneNo=" + clientOrder.getCaptainPhoneNo()
                        + "&captainId=" + clientOrder.getCaptainId()
                        //  + "&Activiat=" + "0"
                        + "&TransferId=" + clientOrder.getTransferId()
                        + "&status=" + clientOrder.getStatus()
                        + "&captainRate=" + clientOrder.getCaptainRate()
                        + "&currentLocation=" + clientOrder.getCurrentLocation().replace(" ","")
                        + "&clientName=" + clientOrder.getClientName()
                        + "&clientNo=" + clientOrder.getClientNo()
                        + "&clientId=" + clientOrder.getClientId()
//                        + "&date=" + clientOrder.getDate()
                        + "&timeToArraive=" + clientOrder.getTimeToArraive();

                URL url = new URL(link+data );


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
                Log.e("url____kkk ",""+link+data);

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
            swASingUp.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Success_Add_CaptainStatus")) {
                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    CaptainMapsActivity captainMapsActivity = (CaptainMapsActivity) context;
                    captainMapsActivity.listAvilable(2);
                    captainDatabase=new CaptainDatabase(context);
                    ids=s.replace("Success_Add_CaptainStatus" ,"").trim();
                    try {
                        captainDatabase.delete();
                    } catch (Exception e) {

                    }
                    captainDatabase.addSetting(ids);

                    ValetFireBaseItem valetFireBaseItem = new ValetFireBaseItem();

                    valetFireBaseItem.setStatus("2");
                    valetFireBaseItem.setIfReturn("0");
                    valetFireBaseItem.setRawIdActivate(ids);
                    valetFireBaseItem.setUserId(singUpUserTableGlobal.getId());
                    valetFireBaseItem.setLongLocation("-1");
                    valetFireBaseItem.setLatLocation("-1");
                    valetFireBaseItem.setClientId(clientOrder.getClientId());
                    valetFireBaseItem.setClientName(clientOrder.getClientName());
                    valetFireBaseItem.setClientPhoneNo(clientOrder.getCaptainPhoneNo());
                    captainMapsActivity.writeInFireBaseCaptain(valetFireBaseItem);

                    captainMapsActivity.UpdateInFireBaseClient("0",clientOrder.getClientId());
                    captainMapsActivity.UpdateInFireBaseClient("3",clientOrder.getClientId());

                    isOk=true;

                } else   {

                    isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sing Up Fail Please Try Again !!!")
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
            }else isOk=true;
        }
    }


    private class updateAriveTime extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        ClientOrder clientOrder;

        Context context;
        String arriveTime;
        public updateAriveTime(Context context,String arriveTime) {

            this.clientOrder = clientOrder;
            this.context=context;
            this.arriveTime=arriveTime;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            sweetAlertDialogArr = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialogArr.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            sweetAlertDialogArr.setTitleText("PleaseWait" );
            sweetAlertDialogArr.setCancelable(false);
            sweetAlertDialogArr.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {   //int idT, int idCaptain, int idS, int idClient, int status
                String cap=captainDatabase.getAllSetting();
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/updateArraiveTime?"+"idT=" + cap.replace(" ","").replaceAll("\"","")
                + "&captainid=" + singUpUserTableGlobal.getId()
                        + "&arriveTime='" + arriveTime+"'";
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
//            swASingUp.dismissWithAnimation();
            if (s != null) {
                if (s.contains("Success_updating_Arrive_time")) {
//                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
//                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
//                    CaptainMapsActivity captainMapsActivity = (CaptainMapsActivity) context;
//                    captainMapsActivity.listAvilable(2);
//                    captainDatabase=new CaptainDatabase(context);
//                    ids=s.replace("Success_Add_CaptainStatus" ,"").trim();
//                    captainDatabase.delete();
//                    captainDatabase.addSetting(ids);
                    isOk = true;
                    sweetAlertDialogArr.dismissWithAnimation();

                } else {
                    isOk = true;
                    sweetAlertDialogArr.dismissWithAnimation();
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sing Up Fail Please Try Again !!!")
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
            }else {
                sweetAlertDialogArr.dismissWithAnimation();
                isOk=true;
            }
        }

    }



    private class updateStatus extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        ClientOrder clientOrder;
String Cp="";
String Ct="";
        public updateStatus(Context context,String Cp ,String Ct) {

            this.Cp=Cp;
            this.Ct=Ct;
            this.clientOrder = clientOrder;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialogStu = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialogStu.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            sweetAlertDialogStu.setTitleText("PleaseWait" );
            sweetAlertDialogStu.setCancelable(false);
            sweetAlertDialogStu.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {   //int idT, int idCaptain, int idS, int idClient, int status
                String cap=captainDatabase.getAllSetting();
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/updateStatusRaws?"
                        +"idT=" + cap.replace(" ","").replaceAll("\"","")
                        + "&IdCapt=" + singUpUserTableGlobal.getId()
                        + "&rawStatuS=" + Cp
                        + "&rawStatusT=" + Ct;
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
                if (s.contains("Success_updating_status_Raw")) {
//                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
//                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    CaptainMapsActivity captainMapsActivity = (CaptainMapsActivity) context;
//                    captainMapsActivity.listAvilable(2);
//                    captainDatabase=new CaptainDatabase(context);
//                    ids=s.replace("Success_Add_CaptainStatus" ,"").trim();
//                    captainDatabase.delete();
//                    captainDatabase.addSetting(ids);

                    if(Cp.equals("7")||Cp.equals("14")){
                        captainDatabase.delete();
                         captainMapsActivity = (CaptainMapsActivity) context;
                        captainMapsActivity.listAvilable(0);

                    }



                    captainMapsActivity.UpdateInFireBaseClient(Ct,clientId);
                    captainMapsActivity.UpdateInFireBaseCaptain(Cp);

                    sweetAlertDialogStu.dismissWithAnimation();
                    isOk=true;
                } else {

                    sweetAlertDialogStu.dismissWithAnimation();
                    isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Please Try Again !!!")
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
            }else {
                sweetAlertDialogStu.dismissWithAnimation();
                isOk=true;}
        }

    }

    private class updateStatusParking extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        ClientOrder clientOrder;
        String Cp="";
        String Ct="";
        String Location="";
        public updateStatusParking(Context context,String Cp ,String Ct,String Location) {

            this.Cp=Cp;
            this.Ct=Ct;
            this.clientOrder = clientOrder;
            this.Location=Location;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialogStuPar = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialogStuPar.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            sweetAlertDialogStuPar.setTitleText("PleaseWait" );
            sweetAlertDialogStuPar.setCancelable(false);
            sweetAlertDialogStuPar.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {
            String data="";
            try {

//                if (!ipAddress.equals("")) {   //int idT, int idCaptain, int idS, int idClient, int status
                String cap=captainDatabase.getAllSetting();
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/updateStatusRawsParking?";
                        data ="idTP=" + cap.replace(" ","").replaceAll("\"","")
                        + "&IdCaptP=" + singUpUserTableGlobal.getId()
                        + "&rawStatuSP=" + Cp
                        + "&rawStatusTP=" + Ct
                        + "&parkingLocation=" + Location.replace(" ","")
                + "&park=" + "1";
//                }
            } catch (Exception e) {
                Log.e("URL_TO_HIT99", "JsonResponse\t" + URL_TO_HIT+data);
            }


            Log.e("URL_TO_HITnew", "JsonResponse\t" + URL_TO_HIT+data.trim());
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPut request = new HttpPut();
                request.setURI(new URI(URL_TO_HIT+data.trim()));

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
                if (s.contains("Success_updating_status_Raw")) {
//                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
//                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    CaptainMapsActivity captainMapsActivity = (CaptainMapsActivity) context;
//                    captainMapsActivity.listAvilable(2);
//                    captainDatabase=new CaptainDatabase(context);
//                    ids=s.replace("Success_Add_CaptainStatus" ,"").trim();
//                    captainDatabase.delete();
//                    captainDatabase.addSetting(ids);

                    if(Cp.equals("7")||Cp.equals("14")){
                        captainDatabase.delete();
                         captainMapsActivity = (CaptainMapsActivity) context;
                        captainMapsActivity.listAvilable(0);

                    }

                    captainMapsActivity.UpdateInFireBaseClient(Ct,clientId);
                    captainMapsActivity.UpdateInFireBaseCaptain(Cp);
                    sweetAlertDialogStuPar.dismissWithAnimation();
                    isOk=true;
                } else {
                    sweetAlertDialogStuPar.dismissWithAnimation();
                    isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Please Try Again !!!")
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
            }else {
                sweetAlertDialogStuPar.dismissWithAnimation();
                isOk=true;}
        }

    }

    private class updateStatus2 extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        CaptainClientTransfer clientOrder;
        String Cp="";
        String Ct="";
        String cap="";
        public updateStatus2(Context context,String Cp ,String Ct,String cap,CaptainClientTransfer clientOrder) {
            this.cap=cap;
            this.Cp=Cp;
            this.Ct=Ct;
            this.clientOrder = clientOrder;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialogStu2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialogStu2.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            sweetAlertDialogStu2.setTitleText("PleaseWait" );
            sweetAlertDialogStu2.setCancelable(false);
            sweetAlertDialogStu2.show();
            isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {   //int idT, int idCaptain, int idS, int idClient, int status
                String ip=captainDatabase.getAllIPSetting();//192.168.1.101:81
                URL_TO_HIT = "http://"+ip.trim() +"/api/ValCaptain/updateStatusRawsClient?"
                        +"idTrans2=" + cap.replace(" ","").replaceAll("\"","")
                        + "&IdCaptains=" + singUpUserTableGlobal.getId()
                        + "&rawS2=" + Cp
                        + "&rawT2=" + Ct;
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
                if (s.contains("Success_updating_status_Raw")) {
//                    Log.e("salesManInfo", "NEW_CAPTAINS SUCCESS\t" + s.toString());
//                    Toast.makeText(context, "NEW CAPTAINS SUCCESS", Toast.LENGTH_SHORT).show();
                    CaptainMapsActivity captainMapsActivity = (CaptainMapsActivity) context;
//                    captainMapsActivity.listAvilable(2);
//                    captainDatabase=new CaptainDatabase(context);
                    ids = s.replace("Success_updating_status_Raw", "").trim();
                    captainDatabase.delete();
                    captainDatabase.addSetting(ids);

                    captainMapsActivity.UpdateInFireBaseClient(Ct, clientId);

                    if (Cp.equals("9")){
                        ValetFireBaseItem valetFireBaseItem = new ValetFireBaseItem();

                    valetFireBaseItem.setStatus(Cp);
                    valetFireBaseItem.setIfReturn("1");
                    valetFireBaseItem.setRawIdActivate(ids);
                    valetFireBaseItem.setUserId(singUpUserTableGlobal.getId());
                    valetFireBaseItem.setLongLocation("-1");
                    valetFireBaseItem.setLatLocation("-1");
                    valetFireBaseItem.setClientId(""+clientOrder.getClientId());
                    valetFireBaseItem.setClientName(clientOrder.getClientName());
                    valetFireBaseItem.setClientPhoneNo(clientOrder.getCaptainPhoneNo());
                    captainMapsActivity.writeInFireBaseCaptain(valetFireBaseItem);
                }else {
                    captainMapsActivity.UpdateInFireBaseCaptain(Cp);
                }

                    //

//                    if(Cp.equals("9")){
//                        captainMapsActivity.UpdateInFireBaseCaptainReturn("0");
//                    }

                    captainMapsActivity.goneReturnList();
                    sweetAlertDialogStu2.dismissWithAnimation();
                    isOk=true;
                } else {
                    sweetAlertDialogStu2.dismissWithAnimation();
                    isOk=true;
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Sing Up Fail Please Try Again !!!")
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
            }else {
                sweetAlertDialogStu2.dismissWithAnimation();
                isOk=true;}
        }

    }

    private class ClintCaptainStatusAsync extends AsyncTask<String, String, String> {

        JSONObject jsonObject;
        public ClintCaptainStatusAsync(Context context,JSONObject jsonObject) {

            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swAccept = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swAccept.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swAccept.setTitleText("PleaseWait" );
            swAccept.setCancelable(false);
            swAccept.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
//                if (!ipAddress.equals("")) {
               // URL_TO_HIT = "http://" + "5.189.130.98:8085" + "/exportt.php".trim();
                URL_TO_HIT = "http://192.168.1.101:81/api/ValCaptain " ;

//                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(1);
                // nameValuePairs.add(new BasicNameValuePair("_ID", "3"));
              //  nameValuePairs.add(new BasicNameValuePair("Captain",jsonObject.toString()));

//                nameValuePairs.add(new BasicNameValuePair("phoneNo",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("userName",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("Password",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("Activiat",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("PROFILE_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("IDENTITY_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("DRIVING_LICENCE_PIC",jsonObject.toString()));
//
//                nameValuePairs.add(new BasicNameValuePair("PERMIT_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("CAR_LICENCE_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("PAYMENT_300_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("PAYMENT_50_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("DESEASE_FREE_PIC",jsonObject.toString()));
//                nameValuePairs.add(new BasicNameValuePair("CRIMINAL_RECORE_PIC",jsonObject.toString()));
//

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


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
                Log.e("tag_requestState", "JsonResponse\t" + JsonResponse);

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
            swAccept.dismissWithAnimation();
            if (s != null) {
                if (s.contains("ADD_CAPTAIN_STATUS_SUCCESS")) {
                    Log.e("salesManInfo", "ADD_CAPTAIN_STATUS  SUCCESS\t" + s.toString());
                    Toast.makeText(context, "ADD CAPTAIN STATUS  SUCCESS", Toast.LENGTH_SHORT).show();
                    //context.clearTextFun();

                }
            }
        }

    }

    private class CarParkedAsync extends AsyncTask<String, String, String> {

        InformationOfClint jsonObject;
        String intentReSend;
        public CarParkedAsync(Context context,InformationOfClint jsonObject,String intentReSend) {

            this.jsonObject=jsonObject;
            this.intentReSend=intentReSend;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swAParking = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swAParking.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swAParking.setTitleText("PleaseWait" );
            swAParking.setCancelable(false);
            swAParking.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
//                if (!ipAddress.equals("")) {
                URL_TO_HIT = "http://" + "5.189.130.98:8085" + "/exportt.php".trim();
//                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(1);
                // nameValuePairs.add(new BasicNameValuePair("_ID", "3"));

                nameValuePairs.add(new BasicNameValuePair("CAR_PARKED","1"));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NAME",jsonObject.getUSERNAME()));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NO",convertToEnglish(jsonObject.getPHONE_NO())));
                nameValuePairs.add(new BasicNameValuePair("CURRENT_DATE",convertToEnglish(jsonObject.getDATE_())));
                nameValuePairs.add(new BasicNameValuePair("CURRENT_TIME",convertToEnglish(jsonObject.getTIME())));
               // nameValuePairs.add(new BasicNameValuePair("SERIAL",convertToEnglish(jsonObject.getSERIAL())));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


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
                Log.e("exportCarPark", "JsonResponse\t" + JsonResponse);

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
            swAParking.dismissWithAnimation();
            if (s != null) {
                if (s.contains("CAR_PARKED_SUCCESS")) {
                    Log.e("salesManInfo", "ADD_CAPTAIN_STATUS  SUCCESS\t" + s.toString());
                    Toast.makeText(context, "status"+intentReSend, Toast.LENGTH_SHORT).show();
                    //context.clearTextFun();
//                    MapsActivity mapsActivity=(MapsActivity) context;
//
//                    if (intentReSend != null && (intentReSend.equals("9"))) {
//                        mapsActivity.close();
//                    }else {
//                        mapsActivity.close();
//                        finish.setText("1");
//                    }

                }
            }
        }

    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }
    private class deleteAsync extends AsyncTask<String, String, String> {

        InformationOfClint jsonObject;
        public deleteAsync(Context context,InformationOfClint jsonObject) {

            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swADelete = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swADelete.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swADelete.setTitleText("PleaseWait" );
            swADelete.setCancelable(false);
            swADelete.show();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
//                if (!ipAddress.equals("")) {
                URL_TO_HIT = "http://" + "5.189.130.98:8085" + "/exportt.php".trim();
//                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(1);
                // nameValuePairs.add(new BasicNameValuePair("_ID", "3"));

                nameValuePairs.add(new BasicNameValuePair("DELETE_ACTION","1"));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NAME",jsonObject.getUSERNAME()));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NO",convertToEnglish(jsonObject.getPHONE_NO())));
                nameValuePairs.add(new BasicNameValuePair("CURRENT_DATE",convertToEnglish(jsonObject.getDATE_())));
                nameValuePairs.add(new BasicNameValuePair("CURRENT_TIME",convertToEnglish(jsonObject.getTIME())));
//                nameValuePairs.add(new BasicNameValuePair("SERIAL",convertToEnglish(jsonObject.getSERIAL())));
//                nameValuePairs.add(new BasicNameValuePair("STATUS",convertToEnglish(jsonObject.getUpdateCaptainStatus())));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


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
                Log.e("exportCarPark", "JsonResponse\t" + JsonResponse);

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
            swADelete.dismissWithAnimation();
            if (s != null) {
                if (s.contains("DELETE_ACTION_SUCCESS")) {
                    Log.e("salesManInfo", "DELETE_ACTION_SUCCESS  SUCCESS\t" + s.toString());
                    Toast.makeText(context, "DELETE_ACTION_SUCCESS", Toast.LENGTH_SHORT).show();



                }
            }
        }

    }

    private class CarArrivedAsync extends AsyncTask<String, String, String> {

        InformationOfClint jsonObject;
        public CarArrivedAsync(Context context,InformationOfClint jsonObject) {

            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swAarraive2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swAarraive2.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swAarraive2.setTitleText("PleaseWait" );
            swAarraive2.setCancelable(false);
            swAarraive2.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
//                if (!ipAddress.equals("")) {
                URL_TO_HIT = "http://" + "5.189.130.98:8085" + "/exportt.php".trim();
//                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(1);
                // nameValuePairs.add(new BasicNameValuePair("_ID", "3"));

                nameValuePairs.add(new BasicNameValuePair("REQUESTED_CAR_READY","1"));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NAME",jsonObject.getUSERNAME()));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NO",convertToEnglish(jsonObject.getPHONE_NO())));
                nameValuePairs.add(new BasicNameValuePair("CURRENT_DATE",convertToEnglish(jsonObject.getDATE_())));
//                nameValuePairs.add(new BasicNameValuePair("SERIAL",convertToEnglish(jsonObject.getSERIAL())));
//                nameValuePairs.add(new BasicNameValuePair("CURRENT_TIME",jsonObject.getTIME()));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


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
                Log.e("exportCarPark", "JsonResponse\t" + JsonResponse);

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
            swAarraive2.dismissWithAnimation();
            if (s != null) {
                if (s.contains("REQUESTED_CAR_READY_SUCCESS")) {
                    Log.e("salesManInfo", "REQUESTED_CAR_READY_SUCCESS  SUCCESS\t" + s.toString());
                    try {
                        ImportJson importJson=new ImportJson(context);
                        importJson.getListOfRequest();
                    }catch (Exception e){
                        Log.e("exportCarPark2=", "J" );
                    }
                    Toast.makeText(context, "Arrive", Toast.LENGTH_SHORT).show();
                    //context.clearTextFun();


                }
            }
        }

    }

    private class CarArrivedAcceptAsync extends AsyncTask<String, String, String> {

        InformationOfClint jsonObject;
        public CarArrivedAcceptAsync(Context context,InformationOfClint jsonObject) {

            this.jsonObject=jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            swAarraive = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swAarraive.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swAarraive.setTitleText("PleaseWait" );
            swAarraive.setCancelable(false);
            swAarraive.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
//                if (!ipAddress.equals("")) {
                URL_TO_HIT = "http://" + "5.189.130.98:8085" + "/exportt.php".trim();
//                }
            } catch (Exception e) {

            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                request.setURI(new URI(URL_TO_HIT));

                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(1);
                // nameValuePairs.add(new BasicNameValuePair("_ID", "3"));

                nameValuePairs.add(new BasicNameValuePair("REQUESTED_CAR_ACCEPT","1"));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NAME",jsonObject.getUSERNAME()));
                nameValuePairs.add(new BasicNameValuePair("CLIENT_NO",convertToEnglish(jsonObject.getPHONE_NO())));
                nameValuePairs.add(new BasicNameValuePair("CURRENT_DATE",convertToEnglish(jsonObject.getDATE_())));
//                nameValuePairs.add(new BasicNameValuePair("SERIAL",convertToEnglish(jsonObject.getSERIAL())));
//                nameValuePairs.add(new BasicNameValuePair("TIME_TO_ARRIVE",convertToEnglish(jsonObject.getArriveTime())));

                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


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
                Log.e("exportCarPark", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        try {
//                            RequestListOfItem requestListOfItem=(RequestListOfItem) context;
//                            requestListOfItem.notification();
                        }catch (Exception e){
                            Log.e("exportCarPark=", "J" );
                        }

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

            if (s != null) {
                if (s.contains("REQUESTED_CAR_ACCEPT_SUCCESS")) {
//                    try {
//                        RequestListOfItem requestListOfItem=(RequestListOfItem) context;
//                        requestListOfItem.fillAdapter();
//                    }catch (Exception e){
//                        Log.e("exportCarPark3=", "J" );
//                    }

                    ImportJson importJson=new ImportJson(context);
                    importJson.getListOfRequest();
                    swAarraive.dismissWithAnimation();

                    Log.e("salesManInfo", "REQUESTED_CAR_READY_SUCCESS  SUCCESS\t" + s.toString());
                    Toast.makeText(context, "Arrive", Toast.LENGTH_SHORT).show();
                    //context.clearTextFun();
//                    RequestListOfItem requestListOfItem=(RequestListOfItem) context;
//                    requestListOfItem.notification();


                }else {
                    swAarraive.dismissWithAnimation();
                }
            }else {
                swAarraive.dismissWithAnimation();
            }
        }

    }

}
