package jp.ac.it_college.std.s13012.shakebattle;

import android.hardware.SensorEvent;

public class ShakeDiscriminator {

    private static final int SPEED_THRESHOLD = 1000;
    private long mLastTime = 0;
    private float mLastX, mLastY, mLastZ = 0;

    public boolean detectShake(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        boolean isShake = false;

        // シェイク時間のチェック
        long now = System.currentTimeMillis();
        if(mLastTime == 0){
            mLastTime = now;
        }

        // 速度を算出する
        long diff = now - mLastTime;
        float speed = Math.abs(x + y + z - mLastX - mLastY - mLastZ) / diff * 10000;

        if(speed > SPEED_THRESHOLD){
            isShake = true;
        }

        mLastTime = now;
        mLastX = x;
        mLastY = y;
        mLastZ = z;
        return isShake;
    }
}
