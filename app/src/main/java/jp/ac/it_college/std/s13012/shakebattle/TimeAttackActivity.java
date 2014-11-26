package jp.ac.it_college.std.s13012.shakebattle;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class TimeAttackActivity extends ShakeActivity
        implements SensorEventListener{
    private TextView timer;
    private CountDown countDown;
    private TextView message;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mCountTextView;
    private int mCounter = 0;
    private ShakeDiscriminator shakeDiscriminator;
    private boolean mGameIsRunning = false;
    private int goal = 0;
    private boolean isSoloPlay = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);
        this.goal = getIntent().getIntExtra(BaseFragment.GOAL_VALUE, -1);

        //ソロプレイ時は対戦相手の表示を消す
        isSoloPlay = getIntent().getBooleanExtra(WaitOpponentActivity.IS_SOLO_PLAY, false);
        if (isSoloPlay) {
            findViewById(R.id.name_of_person).setVisibility(View.GONE);
            findViewById(R.id.label_during_the_competition).setVisibility(View.GONE);
        }

        String opponentName = getIntent().getStringExtra(DataTransferService.OPPONENT_NAME);
        ((TextView)findViewById(R.id.name_of_person)).setText(opponentName);
        mCountTextView = (TextView) findViewById(R.id.current_count);
        shakeDiscriminator = new ShakeDiscriminator();
        message = (TextView) findViewById(R.id.message);
        timer = (TextView) findViewById(R.id.remaining_time);
        timer.setText(String.valueOf(goal / 1000));
        countDown = new CountDown(goal, 1000);
        //センサーの準備
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        super.ready(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //センサーの取得
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //センサーマネージャーへイベントリスナーを登録
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void gameStart() {
        mGameIsRunning = true;
        countDown.start();
    }

    @Override
    public void gameEnd() {
        mGameIsRunning = false;
        message.setText("Finish!");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !mGameIsRunning) {
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (shakeDiscriminator.detectShake(sensorEvent) && mGameIsRunning) {
                //シェイク時の処理
                mCountTextView.setText(String.valueOf(mCounter++));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            timer.setText(Long.toString(l / 1000 % 60));
        }

        @Override
        public void onFinish() {
            timer.setText("0");
            gameEnd();
        }
    }
}
