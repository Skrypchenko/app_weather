package com.task.test.weatherapp.data.model;

import com.task.test.weatherapp.data.model.parts.Main;
import com.task.test.weatherapp.data.model.parts.Weather;
import com.task.test.weatherapp.data.model.parts.Wind;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CityWrapper {

    public static List<CityWrapper> createCityWrapper(List<String> cityNames) {
        ArrayList<CityWrapper> list = new ArrayList<>();
        for (String cityName : cityNames) {
            list.add(new CityWrapper(cityName));
        }
        return list;
    }

    private OWeatherPojo mWeatherPojo;

    //TODO:----------------- TO SQLITE
    private String mCityName;
    private UUID mUid;

    private long mLastUpdate;

    private long mMaxTemp;
    private long mMinTemp;
    private long mHumidity;
    private long mPressure;

    // m/s
    private long mWind;


    private String mWeatherDescription;

    private String mIconName;
    //TODO: -------------- END




    public CityWrapper(String cityName) {
        mCityName = cityName;
        mUid = UUID.randomUUID();

    }

    public CityWrapper(OWeatherPojo pojo) {
        mWeatherPojo = pojo;
        mUid = UUID.randomUUID();
        update();
    }

    public UUID getUid() {
        return mUid;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public OWeatherPojo getWeatherPojo() {
        return mWeatherPojo;
    }

    public void setWeatherPojo(OWeatherPojo weatherPojo) {
        mWeatherPojo = weatherPojo;
        update();
    }


    public String getLastUpdate() {
        return new SimpleDateFormat("dd MMMM yyyy\n'at' HH:mm:ss", Locale.getDefault()).format(new Date(mLastUpdate));
    }

    public String getMaxTemp() {
        return mMaxTemp > 0 ? "+" + mMaxTemp : "-" + mMaxTemp;
    }

    public String getMinTemp() {
        return mMinTemp > 0 ? "+" + mMinTemp : "-" + mMinTemp;
    }

    public String getHumidity() {
        return mHumidity + " %";
    }

    // мм рт ст
    public long getPressure() {
        return Math.round(mPressure * 0.75d);
    }

    // КМ / ч
    public long getWind() {
        return mWind * 60 * 60 / 1000;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public String getIconName() {
        return mIconName;
    }

    private void update() {
        mCityName = mWeatherPojo.getName();
        mLastUpdate = System.currentTimeMillis();

        if (mWeatherPojo.getMain() != null) {
            mMaxTemp = mWeatherPojo.getMain().getTempMax();
            mMinTemp = mWeatherPojo.getMain().getTempMin();
            mHumidity = mWeatherPojo.getMain().getHumidity();
            mPressure = mWeatherPojo.getMain().getPressure();
        }


        if (mWeatherPojo.getWeather() != null &&  mWeatherPojo.getWeather().size() > 0) {
            Weather weather = mWeatherPojo.getWeather().get(0);

            mWeatherDescription = weather.getDescription();
            mIconName = "r_" + weather.getIcon();
        }

        if (mWeatherPojo.getWind() != null) {
            Wind wind = mWeatherPojo.getWind();
            mWind = wind.getSpeed();
        }
    }




    /*public static class CityWrapperComparator implements Comparator<CityWrapper> {
        @Override
        public int compare(CityWrapper city1, CityWrapper city2) {
            OWeatherPojo pojo1 = city1.getWeatherPojo();
            OWeatherPojo pojo2 = city2.getWeatherPojo();

            if (pojo1 == null && pojo2 == null) {
                return 0;
            } else if (pojo2 == null) {
                return 1;
            } else if

            int firstPosition = first.getPositionInList();
            int secondPosition = second.getPositionInList();

            if (firstPosition == secondPosition) {
                return 0;
            } else if (firstPosition > secondPosition) {
                return 1;
            } else {
                return -1;
            }
        }
    }*/
}
