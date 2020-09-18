package com.smarthome.apkinfotest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(getAppInfoList());

        recyclerView.setAdapter(appInfoAdapter);
    }

    private List<AppInfo> getAppInfoList() {
        List<AppInfo> appInfoList = new ArrayList<>();

        List<PackageInfo> packageInfoLists = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfoLists.size(); i++) {
            PackageInfo packageInfo = packageInfoLists.get(i);

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                @SuppressLint("SimpleDateFormat") AppInfo appInfo = new AppInfo(
                        packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(),
                        packageInfo.packageName,
                        packageInfo.versionName,
                        packageInfo.versionCode,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                format(new Date(packageInfo.lastUpdateTime)),
                        packageInfo.applicationInfo.loadIcon(getPackageManager())
                );
                appInfoList.add(appInfo);
            }
        }

        return appInfoList;
    }

    private void initSearchView(String keyword) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(getAppInfoListByKeyword(keyword));

        recyclerView.setAdapter(appInfoAdapter);
    }

    private List<AppInfo> getAppInfoListByKeyword(String keyword) {
        List<AppInfo> appInfoList = new ArrayList<>();

        List<PackageInfo> packageInfoLists = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfoLists.size(); i++) {
            PackageInfo packageInfo = packageInfoLists.get(i);

            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && (appName.toLowerCase().contains(keyword.toLowerCase()))) {
                @SuppressLint("SimpleDateFormat") AppInfo appInfo = new AppInfo(
                        appName,
                        packageInfo.packageName,
                        packageInfo.versionName,
                        packageInfo.versionCode,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                format(new Date(packageInfo.lastUpdateTime)),
                        packageInfo.applicationInfo.loadIcon(getPackageManager())
                );
                appInfoList.add(appInfo);
            }
        }

        return appInfoList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setIconified(true);
        searchView.setMaxWidth(660);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint("Input APP Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("APKInfo", "Query Text [" + query + "] Submitted!");
                initSearchView(query);
                Log.i("APKInfo","Query Text [" + query + "] Succeeded!");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("APKInfo", "Query Text [" + newText + "] Changed!");
                initSearchView(newText);
                Log.i("APKInfo", "Query Text [" + newText + "] Succeeded!");
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_refresh) {
            Log.i("APKInfo", "Refresh Button Pressed!");
            initView();
            Log.i("APKInfo", "APK Info List Initialized!");
            Toast.makeText(MainActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}