package jp.ac.it_college.std.s13012.shakebattle;

import android.app.Fragment;
import android.app.FragmentTransaction;

public abstract class BaseFragment extends Fragment {

    public void fragmentReplace(int id, Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(id, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }
}
