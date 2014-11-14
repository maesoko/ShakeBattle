package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;


public class TimeAttackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);

        ((TextView)findViewById(R.id.remaining_time))
                .setText(String.valueOf(TimeSelectFragment.getRemainingTime()));
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
}
