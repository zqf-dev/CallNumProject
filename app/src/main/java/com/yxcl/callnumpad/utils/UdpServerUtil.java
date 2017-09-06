package com.yxcl.callnumpad.utils;

import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * class from
 * Created by zqf
 * Time 2017/8/2 10:53
 */
public class UdpServerUtil {
    private static UdpServerUtil instance = null;
//    public int DEST_PORT = 5020;
        public int DEST_PORT = 15020;
    private int INTERVAL = 450;

    /* 私有构造方法，防止被实例化 */
    public UdpServerUtil() {

    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static UdpServerUtil getInstance() {
        if (instance == null) {
            synchronized (UdpServerUtil.class) {
                if (instance == null) {
                    instance = new UdpServerUtil();
                }
            }
        }
        return instance;
    }

    public void StatUdpService() {
        SocketThread socketThread = new SocketThread();
        Thread t = new Thread(socketThread);
        t.start();
    }

    private class SocketThread implements Runnable {

        @Override
        public void run() {
            try {
                DatagramSocket mSocket = new DatagramSocket(DEST_PORT);
                while (true) {
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    try {
                        Log.e("CallTag", "进行阻塞" + packet.getLength());
                        mSocket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("CallTag", "解除阻塞" + packet.getLength());
                    String getMsg = new String(buf, 0, packet.getLength());
                    Log.e("CallTag", "发送的数据为：" + getMsg);
                    Intent intent = new Intent("android.accept.data");
                    intent.putExtra("CallNum_Msg", getMsg);
                    CallNumService.mContext .sendBroadcast(intent);
                    String[] numbers = getMsg.split(",");
                    String[] chars = new String[numbers[0].length()];
                    for (int i = 0; i < numbers[0].length(); i++) {
                        chars[i] = numbers[0].substring(i, i + 1);
                    }
                    int m = chars.length;
                    int[] callnum = new int[m + 6];
                    SynchronizedCallPlayMethod(numbers, chars, callnum);
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步锁叫号播放声音
     *
     * @param numbers --Socket接收的字符使用","分隔的数组
     * @param chars   --分隔预约号的字符数组
     * @param callnum --组合完全的播放字符数组
     */
    private void SynchronizedCallPlayMethod(String[] numbers, String[] chars, int[] callnum) {
        callnum[0] = 20;
        for (int i = 1; i < chars.length + 1; i++) {
            if (Integer.valueOf(chars[(i - 1)]) == 0) {
                callnum[i] = 24;
            } else {
                callnum[i] = Integer.valueOf(chars[(i - 1)]);
            }
        }
        callnum[(callnum.length - 1)] = 23;//窗口
        callnum[(callnum.length - 2)] = 21;//号
        callnum[(callnum.length - 3)] = Integer.valueOf(numbers[1]);
        callnum[(callnum.length - 4)] = 22;//到
        callnum[(callnum.length - 5)] = 21;//号
        if (Integer.valueOf(numbers[1]) > 0 && Integer.valueOf(numbers[1]) <= 9) {
            INTERVAL = 450;
        } else {
            INTERVAL = 500;
        }
        synchronized (UdpServerUtil.this) {
            for (int i = 0; i < callnum.length; i++) {
                SoundPlayUtils.play(callnum[i]);
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
