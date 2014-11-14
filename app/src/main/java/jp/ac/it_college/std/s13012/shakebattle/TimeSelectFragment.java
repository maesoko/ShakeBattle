package jp.ac.it_college.std.s13012.shakebattle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeSelectFragment extends BaseFragment implements View.OnClickListener {

    private static int remainingTime;

    public TimeSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_time_select, container, false);

        rootView.findViewById(R.id.button_time_10sec).setOnClickListener(this);
        rootView.findViewById(R.id.button_time_20sec).setOnClickListener(this);
        rootView.findViewById(R.id.button_time_30sec).setOnClickListener(this);
        rootView.findViewById(R.id.button_to_title).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_time_10sec:
                setRemainingTime(10);
                super.selectedTimeOrCount(getActivity(), TimeAttackActivity.class);
                break;
            case R.id.button_time_20sec:
                setRemainingTime(20);
                super.selectedTimeOrCount(getActivity(), TimeAttackActivity.class);
                break;
            case R.id.button_time_30sec:
                setRemainingTime(30);
                super.selectedTimeOrCount(getActivity(), TimeAttackActivity.class);
                break;
            case R.id.button_to_title:
                super.fragmentReplace(R.id.fragment_container, new ModeFragment());
                break;
        }
    }

    public static int getRemainingTime() {
        return remainingTime;
    }

    public static void setRemainingTime(int remainingTime) {
        TimeSelectFragment.remainingTime = remainingTime;
    }
}
