package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeSelectedFragment extends Fragment implements View.OnClickListener{

    public TimeSelectedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_selected, container, false);
    }

    @Override
    public void onClick(View view) {

    }
}
