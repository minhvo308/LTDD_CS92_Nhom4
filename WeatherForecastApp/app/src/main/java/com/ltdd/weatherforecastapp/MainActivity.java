package com.ltdd.weatherforecastapp;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ltdd.weatherforecastapp.databinding.ActivityMainBinding;
import com.ltdd.weatherforecastapp.ui.home.HomeFragment;
import com.ltdd.weatherforecastapp.ui.search.SearchFragment;
import com.ltdd.weatherforecastapp.ui.search.SearchModel;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ẩn thanh action bar
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        //Ẩn thanh trạng thái
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.container, new HomeFragment());
////        fragmentTransaction.add(R.id.navigation_search, new SearchFragment());
//        fragmentTransaction.commit();
    }

//    public void sendData(String lat, String lon) {
////        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        HomeFragment homeFragment = new HomeFragment();
//        homeFragment.receiveDataFromSearchFragment(lat, lon);
////
////        Bundle bundle = new Bundle();
////        bundle.putSerializable("object serchmodel", searchModel);
////        searchFragment.setArguments(bundle);
//    }
}