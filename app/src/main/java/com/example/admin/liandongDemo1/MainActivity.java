package com.example.admin.liandongDemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText loginip;
Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginip=findViewById(R.id.one);
        btnlogin=findViewById(R.id.two);
        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.two:
                String ip=loginip.getText().toString();
                ControlUtils.setUser("bizideal","123456",ip);
                SocketClient.getInstance().creatConnect();
                SocketClient.getInstance().login(new LoginCallback() {
                    @Override
                    public void onEvent(final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (s.equals(ConstantUtil.Success)){
                                    startActivity(new Intent(MainActivity.this,linkageActivity.class));
                                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                break;
        }
    }
}
