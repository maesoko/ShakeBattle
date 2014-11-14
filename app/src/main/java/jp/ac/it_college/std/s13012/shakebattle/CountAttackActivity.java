package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;


public class CountAttackActivity extends Activity {

    private TimeSurfaceView mTimeSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_attack);

        ((TextView)findViewById(R.id.label_goal_number))
                .setText(String.valueOf(CountSelectFragment.getGoalCount()));

        SurfaceView surface = (SurfaceView) findViewById(R.id.label_elapsed_time_count);
        mTimeSurfaceView = new TimeSurfaceView(surface);
        gameStart();
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
