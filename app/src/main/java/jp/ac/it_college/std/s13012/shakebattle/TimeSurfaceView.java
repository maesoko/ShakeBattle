package jp.ac.it_college.std.s13012.shakebattle;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TimeSurfaceView implements SurfaceHolder.Callback, Runnable{

    private Thread mThread;
    private SurfaceHolder mHolder;
    private boolean mIsAlive = false;
    private boolean mMeasurement = false;
    private long mStartTime;
    private long mEndTime;
    private long mElapsedTime;
    private Paint mPaint;

    public TimeSurfaceView(SurfaceView surfaceView) {
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
    }

    public void startMeasurement(){
        mMeasurement = true;
        mStartTime = System.currentTimeMillis();
    }

    public void endMeasurement(){
        mMeasurement = false;
    }

    public long getElapsedTime(){
        return mElapsedTime;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mThread = new Thread(this, "render_thread");
        mIsAlive = true;
        mPaint = new Paint();
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsAlive = false;
        if (mThread.isAlive()){
            try {
                mThread.join();
            } catch (InterruptedException e) {
                Log.e("TextSurfaceView", e.getMessage(), e);
            }
        }
    }

    @Override
    public void run() {
        while (mIsAlive) {
            try {
                if (mMeasurement) {
                    mEndTime = System.currentTimeMillis();
                    mElapsedTime = mEndTime - mStartTime;
                }
                Canvas canvas = mHolder.lockCanvas();
                //背景
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(0,0, canvas.getWidth(), canvas.getHeight(), mPaint);

                //中心座標
                float centerY = canvas.getHeight() / 2f;

                //文字
                String elapsedTimeText = String.format("%.2f", mElapsedTime / 1000f);
                mPaint.setColor(Color.BLACK);
                mPaint.setAntiAlias(true);
                mPaint.setTextSize(38);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float textWidth = mPaint.measureText(elapsedTimeText);
                float baseX = canvas.getWidth() - textWidth;
                float baseY = centerY - (fontMetrics.ascent + fontMetrics.descent) / 2;

                canvas.drawText(elapsedTimeText, baseX, baseY, mPaint);
                mHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(1000 / 30);
                } catch (InterruptedException e) {
                    Log.e("TimeSurfaceView", e.getMessage(), e);
                }
            } catch (NullPointerException e) {
                Log.e("TimeSurfaceView", "NullPointerException", e);
            }
        }
    }
}
