package com.yxcl.callnumpad.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * class from
 * Created by zqf
 * Time 2017/7/31 14:22
 */

public class SelfStartBroadCast extends BroadcastReceiver {
    //开机广播
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.e("CallTag", "接收到开机广播.");
            //启动app
//            Intent start = new Intent(context, MainActivity.class);
//            start.setAction("android.intent.action.MAIN");
//            start.addCategory("android.intent.category.LAUNCHER");
//            start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(start);
            //启动服务
            Intent service = new Intent(context, CallNumService.class);
            context.startService(service);
        }
    }
}
