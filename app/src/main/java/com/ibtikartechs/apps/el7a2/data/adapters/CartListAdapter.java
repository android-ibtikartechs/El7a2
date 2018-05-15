package com.ibtikartechs.apps.el7a2.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedyehya on 5/13/18.
 */

public class CartListAdapter extends ArrayAdapter<CartListModel> {
    Context context;
    ViewHolder viewHolder;

    private CustomeListener customeListener;


    public CartListAdapter(Context context, ArrayList<CartListModel> items)
    {
        super(context,0,items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CartListModel cartListModel = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_cart_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            ArrayAdapter<String> amountSpinnerAdapter;
            String[] amountsArray = {"1","2","3","4","5","6","7","8","9","10"};

            amountSpinnerAdapter = new ArrayAdapter<String>(context, R.layout.view_spinner_amount_row, R.id.language, amountsArray);
            amountSpinnerAdapter.setDropDownViewResource(R.layout.view_spinner_amount_dropdown_row);

            setupSpinner(viewHolder.spinner,amountSpinnerAdapter,cartListModel.getDpId());


            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.spinner.setSelection(Integer.valueOf(cartListModel.getAmount())-1);


            if (!(cartListModel.getImg_url()==null || cartListModel.getImg_url().equals("")))
            {
                Glide.with(context)
                        .load(cartListModel.getImg_url()).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.imvDescription);
            }
            else {
                Glide.with(context)
                        .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.imvDescription); }

            viewHolder.tvTitle.setText(cartListModel.getTitle());
            viewHolder.tvPrice.setText(cartListModel.getPrice());
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customeListener.onRemoveButtonClickListner(cartListModel.getDpId(),view,position);
                }
            });



            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selection = (String) adapterView.getItemAtPosition(i);

                    if (!TextUtils.isEmpty(selection)) {
                        cartListModel.setCount(i);
                        customeListener.onAmountEditListener(cartListModel.getDpId(), selection, position);
                        cartListModel.setAmount(selection);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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
        @BindView(R.id.spinner2)
        Spinner spinner;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CustomeListener {
        public void onRemoveButtonClickListner(String dbId, View buttonView, int position);
        public void onAmountEditListener(String dpId, String amount, int position);
    }
    public void setCustomButtonListner(CustomeListener listener) {
        this.customeListener = listener;
    }



    private void setupSpinner(Spinner spinner, ArrayAdapter<String> amountSpinnerAdapter, final String dpId ) {
        spinner.setAdapter(amountSpinnerAdapter);

    }

}
