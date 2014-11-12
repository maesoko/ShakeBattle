package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.os.Bundle;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new ModeFragment())
                    .commit();
        }
    }
}
