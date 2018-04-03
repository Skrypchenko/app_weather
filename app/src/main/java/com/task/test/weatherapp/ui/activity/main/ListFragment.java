package com.task.test.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.Settings;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.data.service.ApiCommonService;
import com.task.test.weatherapp.ui.activity.BaseFragment;
import com.task.test.weatherapp.util.Lgi;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListFragment extends BaseFragment {

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
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lgi.p("clcik");
                Toast.makeText(getActivity(), "clicl", Toast.LENGTH_SHORT).show();
                mApi.loadWeatherForLatLon("49.984626", "36.361280", "metric", mSettings.getApiKey(), "ru")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<OWeatherPojo>() {
                            @Override
                            public void onCompleted() {
                                Lgi.p();
                                Lgi.toastLong(getActivity(), "complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                /*Lgi.err(e.getMessage(), e);
                                Lgi.toastLong(getActivity(), "error");*/
                                e.printStackTrace();
                                Log.d("fatal", e.getMessage());

                            }

                            @Override
                            public void onNext(OWeatherPojo oWeatherPojo) {
                                Lgi.p("success");
                                Lgi.toastLong(getActivity(), "onNext");
                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
