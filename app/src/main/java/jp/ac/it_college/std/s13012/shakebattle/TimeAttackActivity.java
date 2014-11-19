package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.TextView;


public class TimeAttackActivity extends Activity {
    private TextView timer;
    private CountDown countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);

        timer = (TextView) findViewById(R.id.remaining_time);
        countDown = new CountDown(TimeSelectFragment.getRemainingTime(),1000);
        gameStart();
    }

    private void gameStart() {
        countDown.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            timer.setText(Long.toString(l/1000%60));
        }

        @Override
        public void onFinish() {
            timer.setText("0");
        }
    }
}
