package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;


public class CountAttackActivity extends Activity {

    private TimeSurfaceView mTimeSurfaceView;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_attack);

        ((TextView)findViewById(R.id.label_goal_number))
                .setText(String.valueOf(CountSelectFragment.getGoalCount()));

        SurfaceView surface = (SurfaceView) findViewById(R.id.label_elapsed_time_count);
        mTimeSurfaceView = new TimeSurfaceView(surface);
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
        mTimeSurfaceView.startMeasurement();
    }

    private void gameEnd() {
        mTimeSurfaceView.endMeasurement();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            gameEnd();
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onTouchEvent(event);
    }

}
