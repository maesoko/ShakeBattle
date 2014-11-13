package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;

public abstract class BaseFragment extends Fragment {

    public static final String DESTINATION_CLASS = "destination_class";

    public void fragmentReplace(int id, Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(id, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void selectedTimeOrCount(String name, int value, Context context, Class destination) {
        Intent intent = new Intent(context, ParticipationWaitActivity.class)
                .putExtra(name, value)
                .putExtra(DESTINATION_CLASS, destination);
        startActivity(intent);
    }
}
