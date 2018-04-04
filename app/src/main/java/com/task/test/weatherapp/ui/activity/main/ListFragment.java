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

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.ui.activity.BaseFragment;
import com.task.test.weatherapp.ui.activity.main.parts.CityAdapter;
import com.task.test.weatherapp.ui.activity.main.parts.EventDelItem;
import com.task.test.weatherapp.ui.activity.main.parts.EventOpenCity;
import com.task.test.weatherapp.ui.presenter.ListPresenter;
import com.task.test.weatherapp.ui.view.ListMvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    Unbinder unbinder;

    @Inject
    ApiCommonService mApi;

    @Inject
    Settings mSettings;

    @Inject
    ListPresenter mPresenter;


    private CityAdapter mCityAdapter;

    private FragmentHost mHost;


    public static ListFragment newInstance(FragmentHost host) {
        ListFragment f = new ListFragment();
        f.setFragmentHost(host);
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

        initAdapter();

        mPresenter.init();

        mIvDownload.setOnClickListener(new View.OnClickListener() {
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
                    String str = cityName.toLowerCase();
                    String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
                    mPresenter.addNewCity(cap);
                    mEtCityName.setText("");
                }
            }
        });

    }


    public void setFragmentHost(FragmentHost host) {
        mHost = host;
    }

    @Override
    public void updateItem(String cityName, OWeatherPojo pojo) {
        mCityAdapter.updateCityWrapperByName(cityName, pojo);
    }

    @Override
    public void updateItemOffline(CityWrapper cityWrapper) {
        mCityAdapter.updateCityWrapper(cityWrapper);
    }

    @Override
    public void initAdapterByList(List<CityWrapper> list) {
        mCityAdapter = new CityAdapter(list);
        mRecyclerMain.setAdapter(mCityAdapter);
    }

    @Override
    public boolean isOnline() {
        return isInetAvailable();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCategoryClick(EventDelItem eventDelItem) {
        ((CityAdapter) mRecyclerMain.getAdapter()).removeItemById(eventDelItem.mId);
        mPresenter.removeItem(eventDelItem.mCityName);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityOpen(EventOpenCity eventOpenCity) {
        DetailFragment f = DetailFragment.newInstance(eventOpenCity.mCityWrapper);
        mHost.openFragment(f, DetailFragment.class.getName());
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

    private void initAdapter() {
        mRecyclerMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCityAdapter = new CityAdapter(new ArrayList<CityWrapper>());
        mRecyclerMain.setAdapter(mCityAdapter);
    }
}
