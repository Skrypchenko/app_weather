package com.task.test.weatherapp.ui.activity.main.parts;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;
import com.task.test.weatherapp.util.Lgi;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {

    private List<CityWrapper> items;

    public CityAdapter(List<CityWrapper> list) {
        this.items = list;
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new CityHolder(v);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        holder.bindCity(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<CityWrapper> getList() {
        return items;
    }

    public void updateCityWrapperByName(String cityName, OWeatherPojo pojo) {
        if (items != null && items.size() > 0) {
            for (CityWrapper cityWrapper : items) {
                String name = cityWrapper.getCityName().toLowerCase();
                String newCityName = cityName.toLowerCase();
                if (name != null && cityName != null && newCityName.equals(name)) {
                    cityWrapper.setWeatherPojo(pojo);
                    notifyDataSetChanged();
                    return;
                }
            }

            CityWrapper cityWrapper = new CityWrapper(pojo);
            items.add(cityWrapper);

        } else {
            CityWrapper cityWrapper = new CityWrapper(pojo);
            items.add(cityWrapper);
        }
        notifyDataSetChanged();
    }

    public void updateCityWrapper(CityWrapper cityWrapperNew) {
        if (items != null && items.size() > 0) {
            for (CityWrapper cityWrapper : items) {
                if (cityWrapper.getCityName().toLowerCase().equals(cityWrapperNew.getCityName().toLowerCase())) {
                    return;
                }
            }
            items.add(cityWrapperNew);
        } else {
            items.add(cityWrapperNew);
        }
        notifyDataSetChanged();
    }

    public void removeItemById(int id) {
        if (id < items.size() && id >= 0) {
            items.remove(id);
            notifyDataSetChanged();
        }
    }

    public int addNewWrapper(CityWrapper wrapper) {
        items.add(wrapper);
        notifyDataSetChanged();
        return items.size() - 1;
    }

    public static class CityHolder extends RecyclerView.ViewHolder {

        TextView mTvItemCityName;
        ImageView mIvItemIcon;
        TextView mTvItemTemp;

        ImageView mIvDel;


        private CityWrapper mCityWrapper;

        public CityHolder(View itemView) {
            super(itemView);
            mTvItemCityName = (TextView) itemView.findViewById(R.id.tv_item_city_name);
            mIvItemIcon = (ImageView) itemView.findViewById(R.id.iv_item_icon);
            mTvItemTemp = (TextView) itemView.findViewById(R.id.tv_item_temp);

            mIvDel = (ImageView) itemView.findViewById(R.id.iv_item_del);
            mIvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventDelItem(getAdapterPosition(), mCityWrapper.getCityName()));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EventOpenCity(mCityWrapper));
                }
            });

        }

        public void bindCity(CityWrapper item) {
            this.mCityWrapper = item;

            mTvItemCityName.setText(item.getCityName());



            long t = item.getCurrentTemp();
            if (t != Long.MIN_VALUE) {
                String temp = (t > 0 ? "+" + t : "-" + t) + " C";
                mTvItemTemp.setText(temp);
            } else {
                mTvItemTemp.setText(mTvItemTemp.getContext().getText(R.string.str_error));
            }

            String iconName = item.getIconName();

            if (iconName != null && iconName.length() > 0) {
                int resId = mIvItemIcon.getContext()
                        .getResources()
                        .getIdentifier(
                                iconName,
                                "drawable",
                                mIvItemIcon.getContext().getPackageName()
                        );

                if (resId != 0) {
                    mIvItemIcon.setImageDrawable(mIvItemIcon.getContext().getResources().getDrawable(resId));
                }
            }

        }
    }

}
