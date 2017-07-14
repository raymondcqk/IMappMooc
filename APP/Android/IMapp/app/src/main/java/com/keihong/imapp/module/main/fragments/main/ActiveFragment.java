package com.keihong.imapp.module.main.fragments.main;


import android.view.View;
import android.widget.Toast;

import com.keihong.imapp.R;
import com.keihong.imapp.common.app.BaseFragment;
import com.keihong.imapp.common.widget.GalleyView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends BaseFragment {


    @BindView(R.id.galleyView)
    GalleyView mGalleyView;


    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mGalleyView.setup(getLoaderManager(), new GalleyView.SelectChangedListener() {
            @Override
            public void onSelectedCountChanged(int count) {
                Toast.makeText(getActivity(), "选择了" + count + "张图片", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
