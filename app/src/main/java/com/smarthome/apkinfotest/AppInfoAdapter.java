package com.smarthome.apkinfotest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    private List<AppInfo> appInfoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appImage;
        TextView appName, appPackageName, appPackageVersion, appLastUpdateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appImage = itemView.findViewById(R.id.app_image);
            appName = itemView.findViewById(R.id.app_name);
            appPackageName = itemView.findViewById(R.id.app_package_name);
            appPackageVersion = itemView.findViewById(R.id.app_package_version);
            appLastUpdateTime = itemView.findViewById(R.id.app_last_update_time);
        }
    }

    public AppInfoAdapter(List<AppInfo> appInfoList) {
        this.appInfoList = appInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo appInfo = appInfoList.get(position);
        holder.appImage.setImageDrawable(appInfo.getAppIcon());
        holder.appName.setText(appInfo.getAppName());
        holder.appPackageName.setText("包名: " + appInfo.getPackageName());
        holder.appPackageVersion.setText("版本: " + appInfo.getVersionName() + "-" + appInfo.getVersionCode());
        holder.appLastUpdateTime.setText("日期: " + appInfo.getLastUpdateTime());
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }
}
