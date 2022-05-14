package com.ltdd.weatherforecastapp.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.weatherforecastapp.R;
import com.ltdd.weatherforecastapp.database.DBHelper;
import com.ltdd.weatherforecastapp.ui.home.WeatherDayOfWeek;
import com.ltdd.weatherforecastapp.ui.home.WeatherDayOfWeekAdapter;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    private List<SearchModel> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private iClickItem mIClickItem;

    public interface iClickItem{
        void onClickItemCity(SearchModel searchModel);
    }

    public CityAdapter(Context aContext, List<SearchModel> listData, iClickItem listener) {
        this.context = aContext;
        this.listData = listData;
        this.mIClickItem = listener;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_list_city, null);
            holder = new CityAdapter.ViewHolder();
            holder.city = (TextView) view.findViewById(R.id.we_city);
            holder.kw = (TextView) view.findViewById(R.id.kw);
            holder.temp = (TextView) view.findViewById(R.id.we_temp);
            holder.icon = (ImageView) view.findViewById(R.id.we_ic);
//            holder.delete = (ImageButton) view.findViewById(R.id.btn_delete_city);

            view.setTag(holder);

        } else {
            holder = (CityAdapter.ViewHolder) view.getTag();
        }

        SearchModel searchModel = this.listData.get(position);
        holder.city.setText(searchModel.getNameCity());
        holder.kw.setText(searchModel.getKw());
        holder.temp.setText(searchModel.getTempCity());

        int imageId = this.getMipmapResIdByName(searchModel.getIconCity());

        holder.icon.setImageResource(imageId);

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DBHelper db = new DBHelper(context);
//                String kw = searchModel.getKw().replaceAll("[^a-zA-Z0-9]+", "");
//                db.deleteCitydata(kw);
//                Log.e("hihi", kw);
//                listData.remove(position);
//                notifyDataSetChanged();
//            }
//        });

        return view;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    static class ViewHolder {
        ImageView icon;
        TextView city, temp, kw;
//        ImageButton delete;
    }
}
