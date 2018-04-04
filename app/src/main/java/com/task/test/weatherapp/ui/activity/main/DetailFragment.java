package com.task.test.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.ui.activity.BaseFragment;
import com.task.test.weatherapp.util.Lgi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailFragment extends BaseFragment {

    @BindView(R.id.tv_city_name)
    TextView mTvCityName;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_descr)
    TextView mTvDescr;
    @BindView(R.id.tv_current_temp)
    TextView mTvCurrentTemp;
    @BindView(R.id.tv_max_temp)
    TextView mTvMaxTemp;
    @BindView(R.id.tv_min_temp)
    TextView mTvMinTemp;
    @BindView(R.id.tv_humidity)
    TextView mTvHumidity;
    @BindView(R.id.tv_pressure)
    TextView mTvPressure;
    @BindView(R.id.tv_wind)
    TextView mTvWind;
    Unbinder unbinder;

    public static DetailFragment newInstance(CityWrapper cityWrapper) {
        DetailFragment f = new DetailFragment();
        f.setCityWrapper(cityWrapper);
        return f;
    }

    private CityWrapper mCityWrapper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mCityWrapper != null) {
            mTvCityName.setText(mCityWrapper.getCityName());
            long t = mCityWrapper.getCurrentTemp();
            if (t != Long.MIN_VALUE) {
                String ts = (t >= 0 ? "+" + t : "-" + t) + " C";
                mTvCurrentTemp.setText(mCityWrapper.getCurrentTemp() + " C");
            } else {
                mTvCurrentTemp.setText(mTvCurrentTemp.getContext().getText(R.string.str_error));
            }

            mTvDescr.setText(mCityWrapper.getWeatherDescription());
            if (mCityWrapper.getIconName() != null && mCityWrapper.getIconName().length() > 0) {
                mIvIcon.setImageDrawable(mIvIcon.getContext().getResources().getDrawable(getIconIdRes(mCityWrapper.getIconName())));
            }

            mTvWind.setText(mCityWrapper.getWind() > 0 ? mCityWrapper.getWind() + " km / h" : "0");

            String press = mCityWrapper.getPressure() + " mm";
            mTvPressure.setText(press);

            mTvMinTemp.setText(mCityWrapper.getMinTemp());

            mTvMaxTemp.setText(mCityWrapper.getMaxTemp());

            mTvHumidity.setText(mCityWrapper.getHumidity());



        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public CityWrapper getCityWrapper() {
        return mCityWrapper;
    }

    public void setCityWrapper(CityWrapper cityWrapper) {
        mCityWrapper = cityWrapper;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
