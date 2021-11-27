package com.example.captainapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.captainapp.CaptainMapsActivity;
import com.example.captainapp.GlobalVairable;
import com.example.captainapp.Json.ExportJson;
import com.example.captainapp.Model.CaptainClientTransfer;
import com.example.captainapp.Model.ClientOrder;
import com.example.captainapp.R;

import java.util.List;

import static com.example.captainapp.GlobalVairable.singUpUserTableGlobal;


public class ListAdapterReturn extends BaseAdapter {
    private Context context;
    List<CaptainClientTransfer> itemsList;
    GlobalVairable globalVairable;

    public ListAdapterReturn(Context context, List<CaptainClientTransfer> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        globalVairable=new GlobalVairable(context);
    }

    public ListAdapterReturn() {

    }

    public void setItemsList(List<CaptainClientTransfer> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView fromLoc, toLoc,clientName,phoneNo,moreInfo,carType,carModel,carColor
                ,carLot,specifiedLocation;
        Button accept;
        LinearLayout linear_info;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.return_order_raw, null);

        holder.fromLoc =  view.findViewById(R.id.fromLoc);
        holder.toLoc =  view.findViewById(R.id.toLoc);
        holder.accept=view.findViewById(R.id.accept);
        holder.clientName=view.findViewById(R.id.clientName);
        holder.phoneNo=view.findViewById(R.id.phoneNo);
        //holder.clientName=view.findViewById(R.id.clientName);
        holder.moreInfo=view.findViewById(R.id.moreInfo);
        holder.carType=view.findViewById(R.id.carType);
        holder.carModel=view.findViewById(R.id.carModel);
        holder.carColor=view.findViewById(R.id.carColor);
        holder.carLot=view.findViewById(R.id.carLot);
        holder.specifiedLocation=view.findViewById(R.id.specifiedLocation);
        holder.linear_info=view.findViewById(R.id.linear_info);



        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.linear_info.getVisibility()==View.GONE){
                    holder.moreInfo.setText("Less Info ...");
                    holder.linear_info.setVisibility(View.VISIBLE);
                }else {
                    holder.moreInfo.setText("More Info ...");
                    holder.linear_info.setVisibility(View.GONE);

                }

            }
        });

        holder.clientName.setText( itemsList.get(i).getCaptainClientTransfer().getClientName());
        holder.phoneNo.setText( itemsList.get(i).getClients().getPHONE_NO());
        holder.carType.setText( itemsList.get(i).getClients().getCAR_TYPE());
        holder.carModel.setText( itemsList.get(i).getClients().getCAR_MODEL());
        holder.carColor.setText( itemsList.get(i).getClients().getCAR_COLOR());
        holder.carLot.setText( itemsList.get(i).getClients().getCAR_LOT());
        holder.specifiedLocation.setText( itemsList.get(i).getCaptainClientTransfer().getLocationName());

        String from="",to="";
        try {

            String a = itemsList.get(i).getCaptainClientTransfer().getFromLoc().replace("lat/lng:", "").replace("(", "").replace(")", "").replace(" ", "");
            String b = itemsList.get(i).getCaptainClientTransfer().getToLocation().replace("lat/lng:", "").replace("(", "").replace(")", "").replace(" ", "");

            Log.e("hh", "" + a);

            String[] latLongA = a.split(",");
            String[] latLongB = b.split(",");
            from = globalVairable.getNameFromLocation(Double.parseDouble(latLongA[0]), Double.parseDouble(latLongA[1]));
            to = globalVairable.getNameFromLocation(Double.parseDouble(latLongB[0]), Double.parseDouble(latLongB[1]));

        }catch (Exception e){

        }

        holder.fromLoc.setText(from);
        holder.toLoc.setText(to);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ClientOrder clientOrder=new ClientOrder();
//                clientOrder.setCaptainId(singUpUserTableGlobal.getId());
//                clientOrder.setCaptainName(singUpUserTableGlobal.getUserName());
//                clientOrder.setCaptainPhoneNo(singUpUserTableGlobal.getPHONE_NO());
//                clientOrder.setCaptainRate("3");
//                clientOrder.setDate("11");
//                clientOrder.setClientName(itemsList.get(i).getClientName());
//                clientOrder.setClientNo(itemsList.get(i).getClientPhoneNo());
//                clientOrder.setClientId(String.valueOf(itemsList.get(i).getClientId()));
//                clientOrder.setCurrentLocation("3");
//                clientOrder.setStatus("0");
//                clientOrder.setTimeToArraive("1111");
//                clientOrder.setTransferId(""+itemsList.get(i).getId());
                ExportJson exportJson=new ExportJson(context);
//             //   exportJson.addOrder(context,clientOrder);

                exportJson.updateStatus2(context,"9","10",""+itemsList.get(i).getCaptainClientTransfer().getId(),itemsList.get(i).getCaptainClientTransfer());



            }
        });
        return view;
    }




}

