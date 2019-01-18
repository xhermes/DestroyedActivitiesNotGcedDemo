package me.bug.destroyedactivitiesnotgceddemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * @author hexun on 2019/1/17
 */
public class BugActivity extends BaseSingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_single_fragment);
    }

    @Override
    protected Fragment initFragment(Intent data) {
        Fragment fragment = null;
        try {
            fragment = Fragment.instantiate(this, IMMessageListFragment.class.getName(), data.getExtras());
        } catch (Exception e) {
            Log.e("IMMessageListActivity", e.toString());
        }
        return fragment;
    }
}
