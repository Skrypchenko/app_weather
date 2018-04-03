package com.task.test.weatherapp.ui.activity.main.parts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.test.weatherapp.R;
import com.task.test.weatherapp.data.model.CityWrapper;
import com.task.test.weatherapp.data.model.OWeatherPojo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

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

    public void updateCityWrapperById(int id, OWeatherPojo pojo) {
        if (id < items.size() && id >= 0) {
            CityWrapper cityWrapper = items.get(id);
            cityWrapper.setWeatherPojo(pojo);
            notifyDataSetChanged();
        }
    }

    public void removeItemById(int id) {
        if (id < items.size() && id >= 0) {
            items.remove(id);
            notifyDataSetChanged();
        }
    }

    public void addNewWrapper(CityWrapper wrapper) {
        items.add(wrapper);
        notifyDataSetChanged();
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
                    EventBus.getDefault().post(new EventDelItem(getAdapterPosition()));
                }
            });

        }

        public void bindCity(CityWrapper item) {
            this.mCityWrapper = item;

            mTvItemCityName.setText(item.getCityName());

            if (item.getWeatherPojo() != null) {
                String temp = item.getWeatherPojo().getMain().getTemp() + " C";
                mTvItemTemp.setText(temp);
            } else {
                mTvItemTemp.setText("undefined");
            }


        }
    }

}
