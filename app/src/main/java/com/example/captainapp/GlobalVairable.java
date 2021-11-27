package com.example.captainapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.ListView;

import com.example.captainapp.Model.CaptainClientTransfer;
import com.example.captainapp.Model.InformationOfClint;
import com.example.captainapp.Model.SingUpUserTable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class GlobalVairable {

    public static List<InformationOfClint> drivenList=new ArrayList<>();
    public static List<InformationOfClint> RequestList=new ArrayList<>();
    public static List<CaptainClientTransfer> captainClientTransfers=new ArrayList<>();
    public static List<CaptainClientTransfer> captainClientReturn=new ArrayList<>();
    public static List<CaptainClientTransfer> captainParkingList=new ArrayList<>();

    public static  String  appActivate="0";
    public  static Context context;
    public static ListView gridViewGlobal;
    public static SingUpUserTable singUpUserTableGlobal=new SingUpUserTable();
    public static  String ids="0";
    public static  String clientId="";
    public static  String clientPhoneNo="";
    public static  String clientLocation="";
    public static boolean isOk=true;
    public  static  InformationOfClint informationOfClints=new InformationOfClint();
    public GlobalVairable(Context context) {

        this.context=context;
//        gridViewGlobal=new GridView(R.id.listItemGrid);
    }



    public String getNameFromLocation(double Lat, double Long){

        String name ="";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(Lat, Long, 1);
        } catch (IOException e) {
            Log.e("whereGo","Error");
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            name=""+addresses.get(0).getAddressLine(0);
            Log.e("whereGo",""+addresses.get(0).getCountryName());
        }
        else {
            // do your stuff
            Log.e("whereGo","noThinfg");
        }

        return  name;

    }

    public Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
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

   //static MainValetActivity mainValetActivity=new MainValetActivity();

   // public static ItemListAdapter itemListAdapterGlobal= new ItemListAdapter(mainValetActivity, drivenList); ;


}
