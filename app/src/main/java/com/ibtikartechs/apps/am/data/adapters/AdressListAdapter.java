package com.ibtikartechs.apps.am.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.models.AddressModel;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdressListAdapter  extends ArrayAdapter<AddressModel> {
    Context context;
    CustomeListener customeListener;
    ViewHolder viewHolder;
    int selectedPosition;



    public AdressListAdapter (Context context, ArrayList<AddressModel> items) {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final AddressModel addressModel = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_address_item,parent,false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();


        String url = "http://maps.google.com/maps/api/staticmap?center=" + addressModel.getLatitude() + "," + addressModel.getLongtude() + "&zoom=15&size=400x200&sensor=false";


            Glide.with(context)
                    .load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(viewHolder.mapImageView);


        if (addressModel.isSelected())
            viewHolder.imCheckIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tick_selected));

        else
            viewHolder.imCheckIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tick_unselected));


        viewHolder.lout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition != position || position ==0)
                {
                    getItem(selectedPosition).setSelected(false);
                    selectedPosition = position;
                    addressModel.setSelected(true);
                    customeListener.onSelectedItem(addressModel, position);
                    //viewHolder.lout_container.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_blue_frame));

                }
            }
        });

        viewHolder.tvName.setText(addressModel.getName());
        String citGov = addressModel.getGovernmentName() + " - " + addressModel.getCityName();
        viewHolder.tvGov_City.setText(citGov);
        viewHolder.tvAddress.setText(addressModel.getAddress());
        if (addressModel.isDelivaryAvailabl())
        {
            viewHolder.tvAvailability.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.tvAvailability.setText("متاح");
        }
        else
        {
            viewHolder.tvAvailability.setTextColor(context.getResources().getColor(R.color.ColorRedHint));
            viewHolder.tvAvailability.setText("غير متاح");
        }

        return convertView;
    }




    @Nullable
    @Override
    public AddressModel getItem(int position) {
        return super.getItem(position);
    }

    public class ViewHolder {
        @BindView(R.id.lout_frame_select)
        LinearLayout lout_container;
        @BindView(R.id.tv_name)
        CustomFontTextView tvName;
        @BindView(R.id.tv_govern_city)
        CustomFontTextView tvGov_City;
        @BindView(R.id.tv_address)
        CustomFontTextView tvAddress;
        @BindView(R.id.tv_availability)
        CustomFontTextView tvAvailability;
        @BindView(R.id.map_image)
        ImageView mapImageView;
        @BindView(R.id.ic_check_indicator)
        ImageView imCheckIndicator;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CustomeListener {
        public void onSelectedItem(AddressModel addressModel, int position);
    }

    public void setCustomButtonListner(CustomeListener listener) {
        this.customeListener = listener;
    }
}
