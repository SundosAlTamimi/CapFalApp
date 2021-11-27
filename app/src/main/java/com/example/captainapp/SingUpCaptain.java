package com.example.captainapp;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.captainapp.Json.ExportJson;
import com.example.captainapp.Model.SingUpUserTable;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class SingUpCaptain extends AppCompatActivity {
    Button logInButton;
    boolean driverValet=true;
    Button linear_1Button,linear_2Button,linear_22Button,linear_3Button,linear_4Button,
            linear_1_next,linear_2_next,linear_3_next,linear_4_next,linear_22_next;

    LinearLayout  linear_1,linear_2,linear_3,linear_4,linear_22;
    private static final int SELECT_IMAGE = 3;
    private Uri fileUri;
    int flagPic=0;
    Bitmap YourPicBitmap1=null,YourPicBitmap2=null,YourPicBitmap3=null,YourPicBitmap4=null,YourPicBitmap5=null,YourPicBitmap6=null,YourPicBitmap7=null,YourPicBitmap8=null,YourPicBitmap9=null;
    ImageView YourPicImage1,YourPicImage2,YourPicImage3,YourPicImage4,YourPicImage5,YourPicImage6,YourPicImage7,YourPicImage8,YourPicImage9;

    EditText phoneNo,codeT_1,codeT_2,codeT_3,codeT_4,codeT_5,userName,password;
    CountryCodePicker CantryNo;
    String randomNumber;
      final String CHANNEL_ID="2";
    public static final String YES_ACTION = "YES";
    public static final String Request_ACTION = "Request";
    public static final String STOP_ACTION = "STOP";
    SingUpUserTable singUpUserTable;
    String CodeNo="+962";
    String pic_="",pic_2="",pic_3="",pic_4="",pic_5="",pic_6="",pic_7="",pic_8="",pic_9="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captain_sign_up);

        initialization();


    }

    private void initialization() {

        linear_1Button=findViewById(R.id.linear_1Button);
        linear_2Button=findViewById(R.id.linear_2Button);
        linear_22Button=findViewById(R.id.linear_22Button);
        linear_3Button=findViewById(R.id.linear_3Button);
        linear_4Button=findViewById(R.id.linear_4Button);

        linear_1_next=findViewById(R.id.linear_1_next);
        linear_2_next=findViewById(R.id.linear_2_next);
        linear_22_next=findViewById(R.id.linear_22_next);
        linear_3_next=findViewById(R.id.linear_3_next);
        linear_4_next=findViewById(R.id.linear_4_next);

        linear_1_next.setOnClickListener(onClickListener);
        linear_2_next.setOnClickListener(onClickListener);
        linear_22_next.setOnClickListener(onClickListener);
        linear_3_next.setOnClickListener(onClickListener);
        linear_4_next.setOnClickListener(onClickListener);

        linear_1=findViewById(R.id.linear_1);
        linear_2=findViewById(R.id.linear_2);
        linear_3=findViewById(R.id.linear_3);
        linear_4=findViewById(R.id.linear_4);
        linear_22=findViewById(R.id.linear_22);

        YourPicImage1=findViewById(R.id.YourPicImage1);
        YourPicImage2=findViewById(R.id.YourPicImage2);
        YourPicImage3=findViewById(R.id.YourPicImage3);
        YourPicImage4=findViewById(R.id.YourPicImage4);
        YourPicImage5=findViewById(R.id.YourPicImage5);
        YourPicImage6=findViewById(R.id.YourPicImage6);
        YourPicImage7=findViewById(R.id.YourPicImage7);
        YourPicImage8=findViewById(R.id.YourPicImage8);
        YourPicImage9=findViewById(R.id.YourPicImage9);

        YourPicImage1.setOnClickListener(onClickListener);
        YourPicImage2.setOnClickListener(onClickListener);
        YourPicImage3.setOnClickListener(onClickListener);
        YourPicImage4.setOnClickListener(onClickListener);
        YourPicImage5.setOnClickListener(onClickListener);
        YourPicImage6.setOnClickListener(onClickListener);
        YourPicImage7.setOnClickListener(onClickListener);
        YourPicImage8.setOnClickListener(onClickListener);
        YourPicImage9.setOnClickListener(onClickListener);

        CantryNo=findViewById(R.id.CantryNo);
        phoneNo=findViewById(R.id.phoneNo);

        codeT_1=findViewById(R.id.codeT_1);
        codeT_2=findViewById(R.id.codeT_2);
        codeT_3=findViewById(R.id.codeT_3);
        codeT_4=findViewById(R.id.codeT_4);
        codeT_5=findViewById(R.id.codeT_5);
        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);


        singUpUserTable=new SingUpUserTable();
        colorChange(1);
        CantryNo.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                Toast.makeText(SingUpCaptain.this, "Updated " + selectedCountry.getPhoneCode(), Toast.LENGTH_SHORT).show();
                CodeNo=selectedCountry.getPhoneCode();
            }
        });
    }


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.linear_1_next:
                    linear_1NextFunction();

                    break;

                case R.id.linear_2_next:
                    linear_2NextFunction();
                    break;
                case R.id.linear_22_next:
                    linear_22NextFunction();
                    break;
                case R.id.linear_3_next:

                    linear_3NextFunction();
                    break;
                case R.id.linear_4_next:
                    linear_4NextFunction();
//                    Intent intent=new Intent(SingUpValet.class,)
                    break;


                case R.id.YourPicImage1:
                    flagPic=1;
                    openGallery();
                    break;
                case R.id.YourPicImage2:
                    flagPic=2;
                    openGallery();
                    break;
                case R.id.YourPicImage3:
                    flagPic=3;
                    openGallery();
                    break;
                case R.id.YourPicImage4:
                    flagPic=4;
                    openGallery();
                    break;
                case R.id.YourPicImage5:
                    flagPic=5;
                    openGallery();
                    break;
                case R.id.YourPicImage6:
                    flagPic=6;
                    openGallery();
                    break;
                case R.id.YourPicImage7:
                    flagPic=7;
                    openGallery();
                    break;
                case R.id.YourPicImage8:
                    flagPic=8;
                    openGallery();
                    break;
                    case R.id.YourPicImage9:
                        flagPic=9;
                        openGallery();
                    break;




            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    void linear_1NextFunction (){

        //if(!TextUtils.isEmpty(CodeNo)){
            if(!TextUtils.isEmpty(phoneNo.getText().toString())) {

                Random r = new Random();
                 randomNumber = String.format("%05d", r.nextInt(99999));

                Log.e("randomNumber",""+randomNumber);
                //createNotificationChannel();
                //addNotification();
                //notificationShow("valetApp");
                singUpUserTable.setPHONE_NO(CodeNo.replace("+","").trim()+phoneNo.getText().toString().trim());
                Intent intent =new Intent(this,MainActivity.class);

                showNotification(SingUpCaptain.this,"Captain App \n Code No","رمزك "+randomNumber+" لا تشاركه مع احد بما في ذلك نحن ",intent);

            }else {
                phoneNo.setError("Required !");
            }

//      }  else {
//           // CantryNo.setError("Required !");
//        }
    }

    void linear_22NextFunction (){

        if(!TextUtils.isEmpty(userName.getText().toString())){
            if(!TextUtils.isEmpty(password.getText().toString())) {

                colorChange(3);
                singUpUserTable.setUserName(userName.getText().toString());
                singUpUserTable.setPASSWORD(password.getText().toString());

                        }else {
                            password.setError("Required !");
                        }
                    }else {
                        userName.setError("Required !");
                    }

        }


    void linear_2NextFunction (){

        if(!TextUtils.isEmpty(codeT_1.getText().toString())){
            if(!TextUtils.isEmpty(codeT_2.getText().toString())) {
                if(!TextUtils.isEmpty(codeT_3.getText().toString())) {
                    if(!TextUtils.isEmpty(codeT_4.getText().toString())) {
                        if(!TextUtils.isEmpty(codeT_5.getText().toString())) {
                            colorChange(5);
                            String password = codeT_1.getText().toString()+codeT_2.getText().toString()+
                                    codeT_3.getText().toString()+codeT_4.getText().toString()+codeT_5.getText().toString();
                         //   singUpUserTable.setPASSWORD(password);
                        }else {
                            codeT_5.setError("Required !");
                        }
                    }else {
                        codeT_4.setError("Required !");
                    }
                }else {
                    codeT_3.setError("Required !");
                }

            }else {
                codeT_2.setError("Required !");
            }
        }else {
            codeT_1.setError("Required !");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void linear_3NextFunction (){
        if(YourPicBitmap1!=null){
            if(YourPicBitmap2!=null) {
                if(YourPicBitmap3!=null) {
                    if(YourPicBitmap4!=null) {
                        if(YourPicBitmap5!=null) {
                            if(YourPicBitmap6!=null) {
                                if(YourPicBitmap7!=null) {
                                    if(YourPicBitmap8!=null) {
                                        if(YourPicBitmap9!=null) {
                                            singUpUserTable.setCAR_LICENCE_PIC(bitMapToString(YourPicBitmap5));
                                            singUpUserTable.setCRIMINAL_RECORE_PIC(bitMapToString(YourPicBitmap9));
                                            singUpUserTable.setIDENTITY_PIC(bitMapToString(YourPicBitmap2));
                                            singUpUserTable.setPERMIT_PIC(bitMapToString(YourPicBitmap4));
                                            singUpUserTable.setPROFILE_PIC(bitMapToString(YourPicBitmap1));
                                            singUpUserTable.setDESEASE_FREE_PIC(bitMapToString(YourPicBitmap8));
                                            singUpUserTable.setDRIVING_LICENCE_PIC(bitMapToString(YourPicBitmap3));
                                            singUpUserTable.setPAYMENT_50_PIC(bitMapToString(YourPicBitmap7));
                                            singUpUserTable.setPAYMENT_300_PIC(bitMapToString(YourPicBitmap6));


                                            Log.e("pic_your ","kk"+"   "+bitMapToString(YourPicBitmap1));
                                            SaveSingUp();
                                            //colorChange(4);
                                        }else {
                                            Toast.makeText(this, "please select Photo 9", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(this, "please select Photo 8", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(this, "please select Photo 7", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(this, "please select Photo 6", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(this, "please select Photo 5", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "please select Photo 4", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "please select Photo 3", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "please select Photo 2", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "please select Photo 1", Toast.LENGTH_SHORT).show();
        }
    }


    void linear_4NextFunction (){

        finish();
//        Intent intent=new Intent(SingUpCaptain.this,LogInValet.class);
//        startActivity(intent);


    }

    public void SaveSingUp(){

        JSONObject jsonObject =singUpUserTable.getSingUpJson();

        JSONArray jsonArray=new JSONArray();
      //  jsonArray=jsonObject.
        ExportJson exportJson=new ExportJson(SingUpCaptain.this);
        exportJson.SingUpCaptain(SingUpCaptain.this,jsonObject,singUpUserTable);
       // exportJson.SavePic(SingUpCaptain.this,pic_);
    }


    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"),SELECT_IMAGE);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    fileUri = data.getData(); //added this line
                    try {

                        switch (flagPic){
                            case 1:
                                YourPicBitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage1.setImageBitmap(YourPicBitmap1);
                                pic_=bitMapToString(YourPicBitmap1);
                                singUpUserTable.setPROFILE_PIC(bitMapToString(YourPicBitmap1));
                                Log.e("pic_youra ","kkaaa"+"   "+bitMapToString(YourPicBitmap1));
                                break;
                            case 2:
                                YourPicBitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage2.setImageBitmap(YourPicBitmap2);
                                pic_2=bitMapToString(YourPicBitmap2);
                                break;
                            case 3:
                                YourPicBitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage3.setImageBitmap(YourPicBitmap3);
                                break;
                            case 4:
                                YourPicBitmap4 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage4.setImageBitmap(YourPicBitmap4);
                                break;
                            case 5:
                                YourPicBitmap5 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage5.setImageBitmap(YourPicBitmap5);
                                break;
                            case 6:
                                YourPicBitmap6 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage6.setImageBitmap(YourPicBitmap6);
                                break;
                            case 7:
                                YourPicBitmap7 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage7.setImageBitmap(YourPicBitmap7);
                                break;
                            case 8:
                                YourPicBitmap8 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage8.setImageBitmap(YourPicBitmap8);
                                break;
                            case 9:
                                YourPicBitmap9= MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                                YourPicImage9.setImageBitmap(YourPicBitmap9);
                                break;

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Cancelled",     Toast.LENGTH_SHORT).show();
            }

    }



   public void colorChange (int linearNo){

        switch (linearNo){
            case 1:
                linear_1Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.circ));
                linear_2Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_22Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_3Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_4Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));


//                linear_1Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_2Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_22Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_3Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_4Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));

                linear_1.setVisibility(View.VISIBLE);
                linear_2.setVisibility(View.GONE);
                linear_3.setVisibility(View.GONE);
                linear_4.setVisibility(View.GONE);
                linear_22.setVisibility(View.GONE);


                break;
            case 2:

                linear_1Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_2Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.circ));
                linear_22Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_3Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_4Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));

//                linear_1Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_2Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_22Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_3Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_4Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));

                linear_1.setVisibility(View.GONE);
                linear_2.setVisibility(View.VISIBLE);
                linear_3.setVisibility(View.GONE);
                linear_4.setVisibility(View.GONE);
                linear_22.setVisibility(View.GONE);



                String randomNumberS=randomNumber+"";
                if(Integer.parseInt(randomNumber)!=0&&randomNumberS.length()==5) {
                    Log.e("randomNumber",""+randomNumberS.charAt(0));
                    codeT_1.setText(""+randomNumberS.charAt(0));
                    codeT_2.setText(""+randomNumberS.charAt(1));
                    codeT_3.setText(""+randomNumberS.charAt(2));
                    codeT_4.setText(""+randomNumberS.charAt(3));
                    codeT_5.setText(""+randomNumberS.charAt(4));

                }

                break;
            case 3:
                linear_1Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_2Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_22Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_3Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.circ));
                linear_4Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));

//                linear_1Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_2Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_22Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_3Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_4Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));

                linear_1.setVisibility(View.GONE);
                linear_2.setVisibility(View.GONE);
                linear_3.setVisibility(View.VISIBLE);
                linear_4.setVisibility(View.GONE);
                linear_22.setVisibility(View.GONE);


                break;
            case 4:
                linear_1Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_2Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_22Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_3Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_4Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.circ));

//                linear_1Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_2Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_22Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_3Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_4Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));

                linear_1.setVisibility(View.GONE);
                linear_2.setVisibility(View.GONE);
                linear_3.setVisibility(View.GONE);
                linear_4.setVisibility(View.VISIBLE);
                linear_22.setVisibility(View.GONE);

                break;

            case 5:
                linear_1Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_2Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_22Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.circ));
                linear_3Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));
                linear_4Button.setBackground(SingUpCaptain.this.getResources().getDrawable(R.drawable.bac_green));

//                linear_1Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_2Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_22Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_3Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));
//                linear_4Button.setTextColor(SingUpCaptain.this.getResources().getColor(R.color.black));

                linear_1.setVisibility(View.GONE);
                linear_2.setVisibility(View.GONE);
                linear_3.setVisibility(View.GONE);
                linear_4.setVisibility(View.GONE);
                linear_22.setVisibility(View.VISIBLE);


                break;

        }


    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
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
        colorChange(2);
    }

    public String getBase64ImageString(Bitmap photo) {
        String imgString;
        if(photo != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] profileImage = outputStream.toByteArray();

            imgString = Base64.encodeToString(profileImage,
                    Base64.NO_WRAP);
        }else{
            imgString = "";
        }

        return imgString;
    }

    void clearPhoto(){
        YourPicBitmap1=null;
        YourPicBitmap2=null;
        YourPicBitmap3=null;
        YourPicBitmap4=null;
        YourPicBitmap5=null;
        YourPicBitmap6=null;
        YourPicBitmap7=null;
        YourPicBitmap8=null;
        YourPicBitmap9=null;

    }


    public String bitMapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] arr = baos.toByteArray();
            String result = Base64.encodeToString(arr, Base64.DEFAULT);
            return result;
        }

        return "";
    }
}
