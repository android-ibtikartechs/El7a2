package com.ibtikartechs.apps.am.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.models.NavItemModel;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class NavItemsAdapter extends ArrayAdapter<NavItemModel> {
    Context context;
    ArrayList<NavItemModel> navArrayList;
    private NavItemClickListener customListener;

    public NavItemsAdapter(Context context, ArrayList<NavItemModel> navArrayList) {
        super(context,0, navArrayList);
        this.context = context;
        this.navArrayList = navArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View lisItemView = convertView;

        if (lisItemView == null) {
            lisItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_nav_list_item, parent, false);
        }
        final NavItemModel currentItem = getItem(position);
        ViewHolder viewHolder = (ViewHolder)lisItemView.getTag();
        if (viewHolder==null){
            viewHolder = new ViewHolder();
            viewHolder.txTitle = lisItemView.findViewById(R.id.tv_nav_item_name);
            viewHolder.itemImage = lisItemView.findViewById(R.id.im_nav_icon);
            viewHolder.layoutContainer = lisItemView.findViewById(R.id.lout_nva_item_container);

            lisItemView.setTag(viewHolder);
        }

        viewHolder.txTitle.setText(currentItem.getName());
        viewHolder.layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customListener.onItemClickListner(view,position);
            }
        });

        Glide.with(context).load(currentItem.getIconResourceId()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.itemImage);


        return lisItemView;
    }

    class ViewHolder {
        CustomFontTextView txTitle;
        ImageView itemImage;
        ConstraintLayout layoutContainer;
    }

    public interface NavItemClickListener {
        public void onItemClickListner(View buttonView, int position);
    }
    public void setCustomButtonListner(NavItemClickListener listener) {
        this.customListener = listener;
    }
}
