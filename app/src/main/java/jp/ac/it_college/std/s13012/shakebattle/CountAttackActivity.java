package jp.ac.it_college.std.s13012.shakebattle;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


public class CountAttackActivity extends ShakeActivity
        implements SensorEventListener{

    private TimeSurfaceView mTimeSurfaceView;
    private TextView message;
    private TextView mCountTextView;

    private SensorManager mSensorManager;
    private Sensor mSensor;


    private int mCounter = 0;
    private ShakeDiscriminator shakeDiscriminator;
    private boolean mGameIsRunning = false;
    private int goal = 0;
    private boolean isSoloPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_attack);

        this.goal = getIntent().getIntExtra(BaseFragment.GOAL_VALUE, -1);

        //ソロプレイ時は対戦相手の表示を消す
        isSoloPlay = getIntent().getBooleanExtra(WaitOpponentActivity.IS_SOLO_PLAY, false);
        if (isSoloPlay) {
            findViewById(R.id.name_of_person).setVisibility(View.GONE);
            findViewById(R.id.label_during_the_competition).setVisibility(View.GONE);
        }

        ((TextView)findViewById(R.id.label_goal_number))
                .setText(String.valueOf(goal));

        String opponentName = getIntent().getStringExtra(DataTransferService.OPPONENT_NAME);
        ((TextView)findViewById(R.id.name_of_person)).setText(opponentName);

        message = (TextView) findViewById(R.id.message);
        SurfaceView surface = (SurfaceView) findViewById(R.id.label_elapsed_time_count);
        mTimeSurfaceView = new TimeSurfaceView(surface);
        shakeDiscriminator = new ShakeDiscriminator();
        mCountTextView = (TextView) findViewById(R.id.current_count);
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
        mTimeSurfaceView.startMeasurement();
        mGameIsRunning = true;
    }

    @Override
    public void gameEnd() {
        mTimeSurfaceView.endMeasurement();
        mGameIsRunning = false;
        message.setText("Finish!");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !mGameIsRunning) {
            gameEnd();
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //振った回数が目標回数と同じになったらメソッドを抜ける
            if (Integer.parseInt(mCountTextView.getText().toString()) ==
                    goal) {
                gameEnd();
                return;
            }
            if (shakeDiscriminator.detectShake(sensorEvent) && mGameIsRunning) {
                mCountTextView.setText(String.valueOf(mCounter++));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
