package jp.ac.it_college.std.s13012.shakebattle;


import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public abstract class ShakeActivity extends Activity{

    public void ready(TextView message) {
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
        final TextView finalMessage = message;
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finalMessage.setText("Go!");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finalMessage.setText("");
                        gameStart();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public abstract void gameStart();
    public abstract void gameEnd();
}
