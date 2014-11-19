package jp.ac.it_college.std.s13012.shakebattle;

import android.hardware.SensorEvent;

public class ShakeDiscriminator {

    private static final int SPEED_THRESHOLD = 25;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 3;
    private int mShakeCount = 0;
    private long mLastTime, mLastAccel, mLastShake = 0;
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
        // SHAKE_TIMEOUT までに次の加速を検知しなかったら mShakeCount をリセット
        if(now - mLastAccel > SHAKE_TIMEOUT){
            mShakeCount = 0;
        }
        // 速度を算出する
        long diff = now - mLastTime;
        float speed = Math.abs(x + y + z - mLastX - mLastY - mLastZ) / diff * 10000;
        if(speed > SPEED_THRESHOLD){
            // mShakeCount の加算、SHAKE_COUNT を超えているかのチェック
            // 最後のシェイク時間から SHAKE_DURATION 経過しているかチェック
            if(++mShakeCount >= SHAKE_COUNT && now - mLastShake > SHAKE_DURATION){
                mLastShake = now;
                mShakeCount = 0;
                isShake = true;
            }
            // SPEED_THRESHOLD を超える速度を検出した時刻をセット
            mLastAccel = now;
        }
        mLastTime = now;
        mLastX = x;
        mLastY = y;
        mLastZ = z;
        return isShake;
    }
}
