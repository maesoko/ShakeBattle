package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;


public class ParticipationWaitActivity extends Activity {

    private Class destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participation_wait);
        destination = (Class) getIntent().getSerializableExtra(BaseFragment.DESTINATION_CLASS);
        Log.v("class", String.valueOf(destination));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(this, destination);
            startActivity(intent);
            return true;
        }
        return super.onTouchEvent(event);
    }
}
