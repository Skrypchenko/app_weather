package com.task.test.weatherapp.ui.activity.main;

import android.support.v4.app.Fragment;

/**
 * Created by Yevgen on 04.04.2018.
 */

public interface FragmentHost {
    void openFragment(Fragment fragment, String fragmentName);
}
