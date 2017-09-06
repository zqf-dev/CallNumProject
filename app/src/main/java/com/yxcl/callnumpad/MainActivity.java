package com.yxcl.callnumpad;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxcl.callnumpad.utils.CallNumService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends Activity {

    private DatagramPacket outPacket;
    private DatagramSocket mSendSocket;
    public static final String DEST_IP = "127.0.0.1";
    public int DEST_PORT = 15020;
    private CloseActivityReceiver mReceiver;
    private TextView mCallnum_detail_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registclosereceive();
        Intent intent = new Intent(this, CallNumService.class);
        startService(intent);
        initView();
    }

    /**
     * 注册广播
     */
    private void registclosereceive() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.action.close.activity");
        filter.addAction("android.accept.data");
        mReceiver = new CloseActivityReceiver();
        registerReceiver(mReceiver, filter);
    }

    /**
     * 自定义广播接收者
     */
    private class CloseActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.action.close.activity")) {
                finish();
            } else if (intent.getAction().equals("android.accept.data")) {
                mCallnum_detail_tv.setText("");
                String ss = intent.getStringExtra("CallNum_Msg");
                Log.e("CallTag", "接收广播的数据" + ss);
                String Num = ss.split(",")[0];
                String windows = ss.split(",")[1];
                mCallnum_detail_tv.setText("\n" + "预约号:" + Num + "\n" + "窗口:" + windows);
            }
        }
    }

    private void initView() {
        TextView detail_tv = (TextView) findViewById(R.id.detail_tv);
        mCallnum_detail_tv = (TextView) findViewById(R.id.callnum_detail_tv);
        detail_tv.setText(R.string.callalreadlystart_str);
        Button mClose = (Button) findViewById(R.id.close_btn);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button mSend = (Button) findViewById(R.id.send_btn);
        mSend.setVisibility(View.GONE);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SendData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void SendData() throws Exception {
        // 初始化发送用的DatagramSocket，它包含一个长度为0的字节数组
        mSendSocket = new DatagramSocket();
        outPacket = new DatagramPacket(new byte[0], 0
                , InetAddress.getByName(DEST_IP), DEST_PORT);
        byte[] buff = "3502,13".getBytes();
        outPacket.setData(buff);
        // 发送数据报
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSendSocket.send(outPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        if (mSendSocket != null) {
            if (!mSendSocket.isClosed()) {
                mSendSocket.close();
            }
            mSendSocket.disconnect();
        }
    }
}
