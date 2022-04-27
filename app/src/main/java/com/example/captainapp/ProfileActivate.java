package com.example.captainapp;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.captainapp.Json.ImportJson;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.captainapp.GlobalVairable.appActivate;

import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;

public class ProfileActivate extends AppCompatActivity implements View.OnClickListener {

    ImportJson importJson;
    EditText password, userName, phoneNo;
    CircleImageView proImg, idCImage, drivImage, perSecImage, licenseImage,
            _50Image, _300Image, healImage, CertiImage,prof2;

    TextView user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_2);

        initialization();
        fillText();
      // fillImage();

    }


    private void initialization() {
        appActivate = "1";

        user=findViewById(R.id.user);
        password = findViewById(R.id.password);
        userName = findViewById(R.id.userName);
        phoneNo = findViewById(R.id.phoneNo);
        proImg = findViewById(R.id.proImg);
        idCImage = findViewById(R.id.idCImage);
        drivImage = findViewById(R.id.drivImage);
        perSecImage = findViewById(R.id.perSecImage);
        licenseImage = findViewById(R.id.licenseImage);
        _50Image = findViewById(R.id._50Image);
        _300Image = findViewById(R.id._300Image);
        healImage = findViewById(R.id.healImage);
        CertiImage = findViewById(R.id.CertiImage);
        prof2=findViewById(R.id.prof2);
        importJson=new ImportJson(ProfileActivate.this);
//        importJson.CaptainProfile();

    }

    void fillText() {
        userName.setText("" + singUpUserTableGlobal.getUserName());
        password.setText("" + singUpUserTableGlobal.getPASSWORD());
        phoneNo.setText("" + singUpUserTableGlobal.getPHONE_NO());
        user.setText(""+singUpUserTableGlobal.getUserName());
    }

    @Override
    public void onClick(View v) {

    }

    public void fillImage() {
        prof2.setImageBitmap(singUpUserTableGlobal.getPROFILE_PIC_Bitmap());
        proImg.setBackground(new BitmapDrawable(getResources(), singUpUserTableGlobal.getPROFILE_PIC_Bitmap()));
        idCImage.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getIDENTITY_PIC_Bitmap()));
        drivImage.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getDRIVING_LICENCE_PIC_Bitmap()));
        perSecImage.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getPERMIT_PIC_Bitmap()));
        licenseImage.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getCAR_LICENCE_PIC_Bitmap()));
        _50Image.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getPAYMENT_50_PIC_Bitmap()));
        _300Image.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getPAYMENT_300_PIC_Bitmap()));
        healImage.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getDESEASE_FREE_PIC_Bitmap()));
        CertiImage.setBackground(new BitmapDrawable(getResources(),singUpUserTableGlobal.getCRIMINAL_RECORE_PIC_Bitmap()));
    }
}
