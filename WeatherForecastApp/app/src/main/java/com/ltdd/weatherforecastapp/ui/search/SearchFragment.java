package com.ltdd.weatherforecastapp.ui.search;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ltdd.weatherforecastapp.Common.Common;
import com.ltdd.weatherforecastapp.MainActivity;
import com.ltdd.weatherforecastapp.R;
import com.ltdd.weatherforecastapp.api.ApiService;
import com.ltdd.weatherforecastapp.database.DBHelper;
import com.ltdd.weatherforecastapp.databinding.FragmentSearchBinding;
import com.ltdd.weatherforecastapp.model.Coord;
import com.ltdd.weatherforecastapp.model.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private AutoCompleteTextView autoComplete;
    private TextView city, country, temperature, status;
    private Button addCity;
    private ListView lvCityFavourite;
//    private ISendData mISendData;
    CityAdapter cityAdapter;
    List<SearchModel> listCity = new ArrayList<>();
    String lat = "", lon = "", c = "";
//    ArrayList<City> dataArr = new ArrayList<>();
//    ArrayAdapter<City> newsAdapter;
    DBHelper db;
    View root;
    public int id;

//    public interface ISendData{
//        void sendData (String lat, String lon);
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        mISendData = (ISendData) getActivity();
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container,false);
        anhXa();


        cityAdapter = new CityAdapter(this.getActivity(), listCity, new CityAdapter.iClickItem() {
            @Override
            public void onClickItemCity(SearchModel searchModel) {

            }
        });
        lvCityFavourite.setAdapter(cityAdapter);
        registerForContextMenu(lvCityFavourite);

        lvCityFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("xem đi", i + "");
               String name = listCity.get(i).getKw().replaceAll("[^a-zA-Z0-9]+", "");
               String nameC = listCity.get(i).getNameCity();
               Coord coord = db.getCoordByCityName(name);
               Log.e("lat nè", coord.getLat() + "");
               Log.e("lon nè", coord.getLon() + "");
               Common.latitude = coord.getLat() + "";
               Common.longitude = coord.getLon() + "";
               Common.nameCity = nameC;

               Intent intent = new Intent(SearchFragment.this.getActivity(), MainActivity.class);
               startActivity(intent);
           }
        });

        db = new DBHelper(this.getActivity());
        showListCity();

        autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                c = autoComplete.getText().toString().replaceAll(" ", "");
                Log.e("kkk", c);

                ApiService.apiservice.getWeatherByName(c,
                        "6c496cba030fbaa54828d14c88c1de64", "minutely,alert", "vi",
                        "metric").enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.code() == 200) {
                            WeatherResponse weatherResponse = response.body();
                            String cityName = weatherResponse.getName();
                            String countryName = weatherResponse.getSys().getCountry();
                            String temp = Math.round(weatherResponse.getMain().getTemp()) + "°C";
                            String statusGet = weatherResponse.getWeather().get(0).getDescription();
                            String latG = String.valueOf(weatherResponse.getCoord().getLat());
                            String lonG = String.valueOf(weatherResponse.getCoord().getLon());

                            city.setText(cityName);
                            country.setText(countryName);
                            temperature.setText(temp);
                            status.setText(statusGet);
                            lat = latG;
                            lon = lonG;
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.saveCitydata(c, lon, lat)) {
                    showListCity();
                    Toast.makeText(SearchFragment.this.getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchFragment.this.getActivity(), "Thành phố này bận đã thêm rồi", Toast.LENGTH_SHORT).show();
                }
            }

        });


        return root;
    }

    private void anhXa() {
        autoComplete = root.findViewById(R.id.actv);
        city = root.findViewById(R.id.tv_cityname);
        country = root.findViewById(R.id.tv_countryname);
        temperature = root.findViewById(R.id.tv_temp);
        status = root.findViewById(R.id.tv_trangthai);
        addCity = root.findViewById(R.id.btn_add);

        lvCityFavourite = root.findViewById(R.id.lv_city_favourite);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, 0, 0, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (item.getItemId() == 0){
            Log.e("mâmma", menuInfo.position + " nè nè");
            deleteCity(menuInfo.position);
        }
        return super.onContextItemSelected(item);
    }

    public void showListCity() {
        listCity.clear();
        Cursor res = db.getListCity();
        while (res.moveToNext()) {
            String lvName = res.getString(0);
            String lvLon = res.getString(1);
            String lvLat = res.getString(2);


            ApiService.apiservice.getWeatherByName(lvName,
                    "6c496cba030fbaa54828d14c88c1de64", "minutely,alert", "vi",
                    "metric").enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, retrofit2.Response<WeatherResponse> response) {
                    if(response.code() == 200) {
                        WeatherResponse weatherResponse = response.body();
                        String temp = Math.round(weatherResponse.getMain().getTemp()) + "°C";
                        String icon = "_" + weatherResponse.getWeather().get(0).getIcon();
                        String city = weatherResponse.getName();

                        Log.e("icon", icon);
                        Log.e("name", lvName);
                        Log.e("lon", lvLon);
                        Log.e("lat", lvLat);
                        listCity.add(new SearchModel(city, " (" + lvName + ")", temp, icon));
                        cityAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                }
            });
        }

        Log.e("end", " game");

    }

    public void deleteCity(int position) {
//        Toast.makeText(SearchFragment.this.getActivity(), "xóa", Toast.LENGTH_SHORT).show();
        String name = listCity.get(position).getKw().replaceAll("[^a-zA-Z0-9]+", "");
        Log.e("nameD", name);

        if (db.deleteCitydata(name)) {
            listCity.remove(position);
            cityAdapter.notifyDataSetChanged();
            Toast.makeText(SearchFragment.this.getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SearchFragment.this.getActivity(), "Không thể xóa", Toast.LENGTH_SHORT).show();
        }
//        Log.e("quạo ghê", ".");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}