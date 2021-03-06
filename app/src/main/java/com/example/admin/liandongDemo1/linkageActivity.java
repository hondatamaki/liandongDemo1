package com.example.admin.liandongDemo1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.SocketClient;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class linkageActivity extends AppCompatActivity  {
    public  static  String tag="linkageActivity";
    public  static  String[] sensorString=new String[] {"0","0","0","0"};
Spinner fengshan1,fengshan2,guangzhao1,guangzhao2;
EditText etfengshan,etguangzhao;
CheckBox fengshan,guangzhao;
TextView q,w,e,r;
int i,o,p,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkage);
        fengshan1=findViewById(R.id.sn_fengshan1);
        fengshan2=findViewById(R.id.sn_fengshan2);
        guangzhao1=findViewById(R.id.sn_guangzhao1);
        guangzhao2=findViewById(R.id.sn_guangzhao2);
        etfengshan=findViewById(R.id.et_fengshan);
        etguangzhao=findViewById(R.id.et_guangzhao);
        fengshan=findViewById(R.id.cb_fengshan);
        guangzhao=findViewById(R.id.cb_guangzhao);
        q=findViewById(R.id.q);
        w=findViewById(R.id.w);
        e=findViewById(R.id.e);
        r=findViewById(R.id.r);
        fengshan1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                if (index==0){
                    i=1;
                    q.setText("??????");
                }
                if (index==1){
                    i=2;
                    q.setText("??????");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fengshan2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                if (index==0){
                    o=1;
                    w.setText("??????");
                }
                if (index==1){
                    o=2;
                    w.setText("??????");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        guangzhao1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                if (index==0){
                    p=1;
                    e.setText("??????");
                }
                if (index==1){
                    p=2;
                    e.setText("??????");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        guangzhao2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                if (index==0){
                    l=1;
                    r.setText("?????????");
                }
                if (index==1){
                    l=2;
                    r.setText("??????");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ControlUtils.getData();
        SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

            @Override
            public void onResult(final DeviceBean deviceBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<DeviceBean.Devices> list=deviceBean.getDevice();
                        if (list.size()>0){
                            for (int i=0;i<list.size();i++){
                                String zhi=list.get(i).getValue();
                                if (list.get(i).getBoardId().equals("1")&&list.get(i).getSensorType().equals(ConstantUtil.Temperature)){
                                    sensorString[0]=zhi;
                                }//??????
                                if (list.get(i).getBoardId().equals("13")&&list.get(i).getSensorType().equals(ConstantUtil.Illumination)){
                                    sensorString[1]=zhi;
                                }//??????

                            }
                        }
                    }
                });
            }


        });
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                liandong();
            }
        };
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Message msg=new Message();
                handler.sendMessage(msg);
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,0,1000);
    }

    private void liandong() {
//        String shuzhi1=etfengshan.getText().toString();
        if (fengshan.isChecked()){
            if (TextUtils.isEmpty(etfengshan.getText().toString())){
                Log.i("????????????",tag);
                Toast.makeText(linkageActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                return;
            }else {
                String shuzi=etfengshan.getText().toString();
                Pattern p=Pattern.compile("[a-zA-Z]*");
                Matcher m=p.matcher(shuzi);
                if (m.matches()){
                    Toast.makeText(linkageActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                    Log.i("???????????????????????????",tag);
                    return;
                }
            }
            if (i==1&&o==1){
                String shuzhi=etfengshan.getText().toString();
                Log.i("????????????",tag);
                if (Double.parseDouble(linkageActivity.sensorString[0])>Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }else {
                    ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                }
                Log.i(linkageActivity.sensorString[0],tag);
            }else {
                ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
            }
            if (i==1&&o==2){
                String shuzhi=etfengshan.getText().toString();
                Log.i("????????????",tag);
                if (Double.parseDouble(linkageActivity.sensorString[0])<Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }else {
                    ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                }
                Log.i(linkageActivity.sensorString[0],tag);
            }else {
                ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
            }
            if (i==2&&o==1){
                Log.i("????????????",tag);
                String shuzhi=etfengshan.getText().toString();
                if (Double.parseDouble(linkageActivity.sensorString[1])>Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }else {
                    ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                }
                Log.i(linkageActivity.sensorString[1],tag);
            }else {
                ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
            }
            if (i==2&&o==2){
                String shuzhi=etfengshan.getText().toString();
                if (Double.parseDouble(linkageActivity.sensorString[1])<Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"11",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                    Log.i("????????????",tag);
                }else {
                    ControlUtils.control(ConstantUtil.Relay,"11",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
                    Log.i("????????????",tag);
                }
                Log.i(linkageActivity.sensorString[1],tag);
                Log.i(etfengshan.getText().toString(),tag);
            }else {
                ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
            }
        } else {
            ControlUtils.control(ConstantUtil.Relay,"14",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
        }
//        if (fengshan.isChecked()&& TextUtils.isEmpty(etfengshan.getText().toString())){
//            Toast.makeText(linkageActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
//            return;
//        }


        if (guangzhao.isChecked()){
            if (TextUtils.isEmpty(etguangzhao.getText().toString())){
                Log.i("????????????",tag);
                Toast.makeText(linkageActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                return;
            }else {
                String shuzi=etguangzhao.getText().toString();
                Pattern p=Pattern.compile("[a-zA-Z]*");
                Matcher m=p.matcher(shuzi);
                if (m.matches()){
                    Toast.makeText(linkageActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
                    Log.i("???????????????????????????",tag);
                    return;
                }
            }
          /*  if (TextUtils.isEmpty(etguangzhao.getText().toString())){
            }*/
            if (p==1&&l==1){
                Log.i("???????????????????????????",tag);
                String shuzhi=etguangzhao.getText().toString();
                if (Double.parseDouble(linkageActivity.sensorString[1])>Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"11",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }
                Log.i(linkageActivity.sensorString[1],tag);
            }
            if (p==2&&l==1){
                Log.i("???????????????????????????",tag);
                String shuzhi=etguangzhao.getText().toString();
                if (Double.parseDouble(linkageActivity.sensorString[1])<Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"11",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }
                Log.i(linkageActivity.sensorString[1],tag);
            }
            if (p==1&&l==2){
                Log.i("????????????????????????",tag);
                String shuzhi=etguangzhao.getText().toString();
                if (Double.parseDouble(linkageActivity.sensorString[1])>Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"10",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }
                Log.i(linkageActivity.sensorString[1],tag);
            }
            if (p==2&&l==2){
                Log.i("????????????????????????",tag);
                String shuzhi=etguangzhao.getText().toString();
                if (Double.parseDouble(linkageActivity.sensorString[1])<Double.parseDouble(shuzhi)){
                    ControlUtils.control(ConstantUtil.Relay,"10",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Open);
                }
                Log.i(linkageActivity.sensorString[1],tag);
            }
        }else  {
//            Log.i("?????????",tag);
            ControlUtils.control(ConstantUtil.Relay,"11",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
            ControlUtils.control(ConstantUtil.Relay,"10",ConstantUtil.CmdCode_1,ConstantUtil.Channel_ALL,ConstantUtil.Close);
        }

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
//        switch (view.getId()){
//            case R.id.sn_fengshan1:
//                break;
//            case R.id.sn_fengshan2:
//                break;
//            case R.id.sn_guangzhao1:
//                break;
//            case R.id.sn_guangzhao2:
//                break;
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
