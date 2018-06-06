package com.ibtikartechs.apps.el7a2.data.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ibtikartechs.apps.el7a2.ui_utilities.CustomRecyclerView;

public class CustomBaseAdapter extends CustomRecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
