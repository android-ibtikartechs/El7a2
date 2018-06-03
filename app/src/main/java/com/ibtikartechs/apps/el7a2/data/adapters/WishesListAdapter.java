package com.ibtikartechs.apps.el7a2.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishesListAdapter extends ArrayAdapter<CartListModel> {
    Context context;
    ViewHolder viewHolder;
    CustomeListener customeListener;

    public WishesListAdapter(Context context, ArrayList<CartListModel> items)
    {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CartListModel wishesListModel = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_wishes_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        if (!(wishesListModel.getImg_url()==null || wishesListModel.getImg_url().equals("")))
        {
            Glide.with(context)
                    .load(wishesListModel.getImg_url()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(viewHolder.imvDescription);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.imvDescription); }

        viewHolder.tvTitle.setText(wishesListModel.getTitle());
        viewHolder.tvPrice.setText(wishesListModel.getPrice());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customeListener.onRemoveButtonClickListner(wishesListModel.getDpId(),view,position);
            }
        });
        viewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customeListener.onAddToCartListener(wishesListModel.getId(), wishesListModel.getTitle(), wishesListModel.getPrice(), wishesListModel.getImg_url(),"1");
            }
        });
        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.imageView2)
        ImageView imvDescription;
        @BindView(R.id.customFontTextView3)
        CustomFontTextView tvTitle;
        @BindView(R.id.customFontTextView6)
        CustomFontTextView tvPrice;
        @BindView(R.id.im_btn_delete)
        ImageView btnDelete;
       @BindView(R.id.imageView5)
       ImageView btnAddToCart;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


    public interface CustomeListener {
        public void onRemoveButtonClickListner(String dbId, View buttonView, int position);
        public void onAddToCartListener(String id, String title, String price, String imgUrl, String quantity);
    }
    public void setCustomButtonListner(CustomeListener listener) {
        this.customeListener = listener;
    }
}
