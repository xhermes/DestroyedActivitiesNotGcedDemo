package me.bug.destroyedactivitiesnotgceddemo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * This is an abstract activity that just contain one fragment and a common action bar.
 * you need create another activity extends this for a special fragment, and do some different logic.
 * <p/>
 * If your layout or container id is different from default, you need overwrite
 *
 * @author Rod on 2015/9/21.
 * @see #getContentViewResId() or
 * @see #getFragmentContainerId()
 * <p/>
 */
public abstract class BaseSingleFragmentActivity extends Activity {
    public static final String KEY_TITLE = "title";
    public static final String KEY_SHOW_BACK = "show_back";
    public static final String KEY_SHOW_DIVIDER = "show_divider";

    private Fragment mFragment;
    private String mFragmentTag;
    private int mContainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent data = getIntent();
        if (data != null) {
            String title = data.getStringExtra(KEY_TITLE);
            boolean showBackBtn = data.getBooleanExtra(KEY_SHOW_BACK, false);
            boolean showDivider = data.getBooleanExtra(KEY_SHOW_DIVIDER, true);

            initActionBar(title);
        }
        setContentView(getContentViewResId());
        mContainId = getFragmentContainerId();
        mFragment = getFragmentManager().findFragmentByTag(mFragmentTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (mFragment == null) {
            Intent data = getIntent();
            mFragment = initFragment(data);

            if (mFragment == null) {
                finish();
                return;
            }
            transaction.add(mContainId, mFragment, mFragmentTag);
        }

        transaction.show(mFragment).commitAllowingStateLoss();
    }

    protected void initActionBar(String title) {
        if (TextUtils.isEmpty(title)) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        } else {
            setTitle(title);
        }
    }

    protected Fragment getFragment() {
        return mFragment;
    }

    protected int getContentViewResId() {
        return R.layout.activity_single_fragment;
    }

    protected int getFragmentContainerId() {
        return R.id.container;
    }

    protected abstract Fragment initFragment(Intent data);


}
