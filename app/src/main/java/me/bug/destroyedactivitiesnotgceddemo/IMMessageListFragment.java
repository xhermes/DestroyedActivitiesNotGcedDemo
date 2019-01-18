package me.bug.destroyedactivitiesnotgceddemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 会话页面
 *
 * @author Rod on 2017/4/12.
 */
public class IMMessageListFragment extends Fragment {

    private View mHeadView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

            int res = R.layout.fragment_bug;
            view = inflater.inflate(res, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView();
    }


    private void setView() {
//        mContainer = findViewById(R.id.pull_view_fl);
        mHeadView = LayoutInflater.from(getActivity())
                .inflate(R.layout.chat_pull_refresh_header_layout, null);
    }

}

