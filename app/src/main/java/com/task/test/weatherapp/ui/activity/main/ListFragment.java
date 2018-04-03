package com.task.test.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    @BindView(R.id.test_view)
    View mTestView;
    Unbinder unbinder;

    @Inject
    ApiCommonService mApi;

    @Inject
    Settings mSettings;

    @Inject
    ListPresenter mPresenter;

    private List<CityWrapper> mCities = new ArrayList<>();
    {
        mCities.add(new CityWrapper("Kharkiv"));
        mCities.add(new CityWrapper("Kiyv"));
        mCities.add(new CityWrapper("Dnipro"));
        mCities.add(new CityWrapper("London"));
    }



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
        mPresenter.load(mCities);
        mRecyclerMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        CityAdapter adapter = new CityAdapter(mCities);
        mRecyclerMain.setAdapter(adapter);

        mTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.load(mCities);
            }
        });

        mRelAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = mEtCityName.getText().toString();
                if (cityName.length() > 0) {
                    CityWrapper wrapper = new CityWrapper(cityName);
                    ((CityAdapter) mRecyclerMain.getAdapter()).addNewWrapper(wrapper);
                    mCities.add(new CityWrapper(cityName));
                    mPresenter.load(mCities);
                    mEtCityName.setText("");
                }
            }
        });

    }

    @Override
    public void updateItem(int id, OWeatherPojo pojo) {
        ((CityAdapter) mRecyclerMain.getAdapter()).updateCityWrapperById(id, pojo);
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
