package com.yxcl.callnumpad.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.yxcl.callnumpad.R;

/**
 * class from 叫号后台运行服务
 * Created by zqf
 * Time 2017/7/31 14:06
 */

public class CallNumService extends Service {
    private UdpServerUtil mUdpServerUtil;
    public static Context mContext;

    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * UdpServerUtil Create Instance
         * UdpServerUtil创建实例
         */
        mUdpServerUtil = UdpServerUtil.getInstance();
        /**
         * Initialize the preloaded audio utility class
         * 初始化预加载音频工具类
         */
        SoundPlayUtils.init(this);
        /**
         * Open the response receiving service
         * 开启响应接收服务
         */
        mUdpServerUtil.StatUdpService();
        Log.e("CallTag", "onCreate");

        Toast.makeText(this, R.string.callstartcomplete_str, Toast.LENGTH_SHORT).show();
        /**
         * Send a broadcast shutdown App
         * 发送广播关闭App
         */
        sendBroadcast(new Intent("android.action.close.activity"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("CallTag", "onStartCommand");
        mContext = this;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("CallTag", "onDestroy");
        stopSelf();
    }
}
