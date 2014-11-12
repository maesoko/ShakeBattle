package jp.ac.it_college.std.s13012.shakebattle;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ModeFragment extends Fragment implements View.OnClickListener {


    public ModeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mode, container, false);

        rootView.findViewById(R.id.label_count_attack).setOnClickListener(this);
        rootView.findViewById(R.id.label_time_attack).setOnClickListener(this);
        rootView.findViewById(R.id.label_guest).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.label_count_attack:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CountSelectedFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
            case R.id.label_time_attack:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TimeSelectedFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                break;
            case R.id.label_guest:
                startActivity(new Intent(getActivity(), OpponentSearchActivity.class));
                break;
        }
    }
}
