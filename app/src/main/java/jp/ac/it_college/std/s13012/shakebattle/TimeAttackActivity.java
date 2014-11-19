package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;


public class TimeAttackActivity extends Activity {
    private TextView timer;
    private CountDown countDown;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);

        timer = (TextView) findViewById(R.id.remaining_time);
        timer.setText(String.valueOf(TimeSelectFragment.getRemainingTime() / 1000));
        countDown = new CountDown(TimeSelectFragment.getRemainingTime(),1000);
        ready();
    }

    private void ready() {
        message = (TextView) findViewById(R.id.message);
        message.setVisibility(View.VISIBLE);
        message.setText("Ready?");
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(
                new ScaleAnimation(1,2,1,2,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        );

        animationSet.setDuration(2000);
        animationSet.setFillAfter(true);
        message.setAnimation(animationSet);
        final Handler handler = new Handler();
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                message.setText("Go!");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        message.setText("");
                        gameStart();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
            timer.setText(Long.toString(l / 1000 % 60));
        }

        @Override
        public void onFinish() {
            timer.setText("0");
            message.setText("Finish!");
        }
    }


}
