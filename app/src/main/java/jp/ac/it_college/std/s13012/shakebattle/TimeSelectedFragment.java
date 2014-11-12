package jp.ac.it_college.std.s13012.shakebattle;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeSelectedFragment extends Fragment implements View.OnClickListener{

    public static final String SELECTED_TIME = "selected_time";

    public TimeSelectedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_time_selected, container, false);

        rootView.findViewById(R.id.button_time_10sec).setOnClickListener(this);
        rootView.findViewById(R.id.button_time_20sec).setOnClickListener(this);
        rootView.findViewById(R.id.button_time_30sec).setOnClickListener(this);
        rootView.findViewById(R.id.button_to_title).setOnClickListener(this);

        return rootView;
    }

    private void timeSelected(int time) {
        Intent intent = new Intent(getActivity(), TimeAttackActivity.class);
        intent.putExtra(SELECTED_TIME, time);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_time_10sec:
                timeSelected(10);
                break;
            case R.id.button_time_20sec:
                timeSelected(20);
                break;
            case R.id.button_time_30sec:
                timeSelected(30);
                break;
            case R.id.button_to_title:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ModeFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
        }
    }
}
