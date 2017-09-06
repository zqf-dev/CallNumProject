package com.yxcl.callnumpad.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.yxcl.callnumpad.R;

/**
 * class from
 * Created by zqf
 * Time 2017/8/1 13:52
 */

public class SoundPlayUtils {

    // SoundPool对象
    public static SoundPool mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    public static SoundPlayUtils soundPlayUtils;
    // 上下文
    public static Context mContext;

    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPlayUtils init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils();
        }
        // 初始化声音
        mContext = context;
        mSoundPool.load(mContext, R.raw.one, 1);// 1
        mSoundPool.load(mContext, R.raw.two, 1);// 2
        mSoundPool.load(mContext, R.raw.three, 1);// 3
        mSoundPool.load(mContext, R.raw.four, 1);// 4
        mSoundPool.load(mContext, R.raw.five, 1);// 5
        mSoundPool.load(mContext, R.raw.six, 1);// 6
        mSoundPool.load(mContext, R.raw.seven, 1);// 7
        mSoundPool.load(mContext, R.raw.eight, 1);// 8
        mSoundPool.load(mContext, R.raw.nine, 1);// 9
        mSoundPool.load(mContext, R.raw.ten, 1);// 10
        mSoundPool.load(mContext, R.raw.eleven, 1);// 11
        mSoundPool.load(mContext, R.raw.twelve, 1);// 12
        mSoundPool.load(mContext, R.raw.thirteen, 1);// 13
        mSoundPool.load(mContext, R.raw.fourteen, 1);// 14
        mSoundPool.load(mContext, R.raw.fifteen, 1);// 15
        mSoundPool.load(mContext, R.raw.sixteen, 1);// 16
        mSoundPool.load(mContext, R.raw.seventeen, 1);// 17
        mSoundPool.load(mContext, R.raw.eighteen, 1);// 18
        mSoundPool.load(mContext, R.raw.nineteen, 1);// 19
        mSoundPool.load(mContext, R.raw.please, 1);// 20
        mSoundPool.load(mContext, R.raw.hao, 1);// 21
        mSoundPool.load(mContext, R.raw.dao, 1);// 22
        mSoundPool.load(mContext, R.raw.windows, 1);// 23
        mSoundPool.load(mContext, R.raw.zero, 1);// 24
        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public static void play(int soundID) {
        mSoundPool.play(soundID, 1, 1, 0, 0, 1);
    }
}
