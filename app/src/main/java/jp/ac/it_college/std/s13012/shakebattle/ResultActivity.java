package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ResultActivity extends Activity {

    public static final String GAME_MODE = "game_mode";
    public static final String GOAL_VALUE = "goal_value";
    public static final String GAME_RESULT = "shake_count";
    public static final String IS_SOLO_PLAY = "is_solo_play";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        boolean isSoloPlay = getIntent().getBooleanExtra(IS_SOLO_PLAY, false);

        if (isSoloPlay) {
            findViewById(R.id.label_result_of_opponent).setVisibility(View.GONE);
            findViewById(R.id.label_number_of_times_opponent_shook).setVisibility(View.GONE);
            findViewById(R.id.label_opponent_result_unit).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.label_result)).setText(getString(R.string.game_result));
        }

        String gameMode = getIntent().getStringExtra(GAME_MODE);
        ((TextView)findViewById(R.id.label_mode)).setText(gameMode);
        changeUnit(gameMode);

        int goalValue = getIntent().getIntExtra(GOAL_VALUE, -1);
        ((TextView)findViewById(R.id.label_difficulty)).setText(String.valueOf(goalValue));

        String gameResult = getIntent().getStringExtra(GAME_RESULT);
        ((TextView)findViewById(R.id.label_game_result)).setText(gameResult);

        findViewById(R.id.label_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TitleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void changeUnit(String name) {
        if (name.equals(getString(R.string.count_attack))) {
            ((TextView)findViewById(R.id.label_mode_unit))
                    .setText(getString(R.string.unit_count));
            ((TextView)findViewById(R.id.label_opponent_result_unit))
                    .setText(getString(R.string.unit_time));
            ((TextView)findViewById(R.id.label_game_result_unit))
                    .setText(getString(R.string.unit_time));
            ((TextView)findViewById(R.id.label_result_time_or_count))
                    .setText(R.string.elapsed_time);
        }

        if (name.equals(getString(R.string.time_attack))){
            ((TextView)findViewById(R.id.label_mode_unit))
                    .setText(getString(R.string.unit_time));
            ((TextView)findViewById(R.id.label_opponent_result_unit))
                    .setText(getString(R.string.unit_count));
            ((TextView)findViewById(R.id.label_game_result_unit))
                    .setText(getString(R.string.unit_count));
            ((TextView)findViewById(R.id.label_result_time_or_count))
                    .setText(R.string.shake_was_number_of_times);
        }
    }
}
