package com.task.test.weatherapp.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.task.test.weatherapp.data.model.CityWrapper;

/**
 * Created by Yevgen on 04.04.2018.
 */

public class CityCursorWrapper extends CursorWrapper {

    public CityCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public CityWrapper getCityWrapper() {
        String name = getString(getColumnIndex(CityWrapperSchema.FIELDS.NAME));
        long lastUpdate = getLong(getColumnIndex(CityWrapperSchema.FIELDS.LAST_UPDATE));
        long currentTemp = getLong(getColumnIndex(CityWrapperSchema.FIELDS.CURRENT_TEMP));
        long maxTemp = getLong(getColumnIndex(CityWrapperSchema.FIELDS.MAX_TEMP));
        long minTemp = getLong(getColumnIndex(CityWrapperSchema.FIELDS.MIN_TEMP));
        long humidity = getLong(getColumnIndex(CityWrapperSchema.FIELDS.HUMIDITY));
        long pressure = getLong(getColumnIndex(CityWrapperSchema.FIELDS.PRESSURE));
        long wind = getLong(getColumnIndex(CityWrapperSchema.FIELDS.WIND));
        String descr = getString(getColumnIndex(CityWrapperSchema.FIELDS.DESCR));
        String icon = getString(getColumnIndex(CityWrapperSchema.FIELDS.ICON));

        CityWrapper cityWrapper = new CityWrapper(name);
        cityWrapper.setLastUpdate(lastUpdate);
        cityWrapper.setCurrentTemp(currentTemp);
        cityWrapper.setMaxTemp(maxTemp);
        cityWrapper.setMinTemp(minTemp);
        cityWrapper.setHumidity(humidity);
        cityWrapper.setPressure(pressure);
        cityWrapper.setWind(wind);
        cityWrapper.setWeatherDescription(descr);
        cityWrapper.setIconName(icon);

        return cityWrapper;
    }
}
