package com.task.test.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.ui.activity.BaseFragment;
import com.task.test.weatherapp.ui.activity.main.parts.CityAdapter;
import com.task.test.weatherapp.ui.activity.main.parts.EventDelItem;
import com.task.test.weatherapp.ui.presenter.ListPresenter;
import com.task.test.weatherapp.ui.view.ListMvpView;
import com.task.test.weatherapp.util.Lgi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListFragment extends BaseFragment implements ListMvpView {

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerMain;
    @BindView(R.id.et_city_name)
    EditText mEtCityName;
    @BindView(R.id.rel_add_city)
    RelativeLayout mRelAddCity;
    @BindView(R.id.iv_test_view)
    ImageView mTestView;
    Unbinder unbinder;

    @Inject
    ApiCommonService mApi;

    @Inject
    Settings mSettings;

    @Inject
    ListPresenter mPresenter;

    private CityAdapter mCityAdapter;





    public static ListFragment newInstance() {
        ListFragment f = new ListFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        mPresenter.attachView(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<CityWrapper> list = new ArrayList<>();
        mCityAdapter =  new CityAdapter(list);
        mRecyclerMain.setAdapter(mCityAdapter);

        mPresenter.test();

        mTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.load(mCityAdapter.getList());
            }
        });

        mRelAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = mEtCityName.getText().toString();
                if (cityName.length() > 0) {
                    CityWrapper wrapper = new CityWrapper(cityName);
                    mCityAdapter.addNewWrapper(wrapper);
                    mPresenter.load(wrapper.getUid(), wrapper);
                    mEtCityName.setText("");
                }
            }
        });

    }

    @Override
    public void updateItem(UUID id, OWeatherPojo pojo) {
        mCityAdapter.updateCityWrapperById(id, pojo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCategoryClick(EventDelItem eventDelItem) {
        ((CityAdapter) mRecyclerMain.getAdapter()).removeItemById(eventDelItem.mId);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
