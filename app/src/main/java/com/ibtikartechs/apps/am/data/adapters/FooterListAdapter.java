package com.ibtikartechs.apps.am.data.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.models.FooterListItemModel;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedyehya on 5/7/18.
 */

public class FooterListAdapter extends CustomRecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<FooterListItemModel> arrayList;
    Handler handler;
    Runnable runnable;
    private Context context;
    private ContainerClickListener customListener;

    public FooterListAdapter(Context context,
                       ArrayList<FooterListItemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.view_item_suggested_category_list, parent, false);
        viewHolder = new FooterViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FooterListItemModel footerListItemModel = arrayList.get(position);
        final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

        if(!(footerListItemModel.getImgUrl().equals("")|| footerListItemModel.getImgUrl()==null))
        {
            Glide.with(context)
                    .load(footerListItemModel.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(footerViewHolder.imItem);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(footerViewHolder.imItem);
        }

        footerViewHolder.tvPrice.setText(footerListItemModel.getPrice());
        footerViewHolder.tvTitle.setText(footerListItemModel.getDescription());

        if (footerListItemModel.isDisplayTimer())
            countDownStart(footerListItemModel.getEndDate(),footerViewHolder);
        else
            footerViewHolder.loutTime.setVisibility(View.GONE);
        footerViewHolder.btnContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.onItemClickListener(footerListItemModel.getId(), footerListItemModel.getDescription());
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    public void add(FooterListItemModel r) {
        arrayList.add(r);
        notifyItemInserted(arrayList.size()-1 );
    }

    public void addAll(ArrayList<FooterListItemModel> opResults) {
        for (FooterListItemModel result : opResults) {
            add(result);
        }
    }


    protected class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_lout_container)
        ConstraintLayout btnContainer;
        @BindView(R.id.im_footer_item)
        ImageView imItem;
        @BindView(R.id.tv_footer_item_price)
        TextView tvPrice;
        @BindView(R.id.tv_footer_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_main_deal_days)
        CustomFontTextView tvDays;
        @BindView(R.id.tv_main_deal_hours)
        CustomFontTextView tvHours;
        @BindView(R.id.tv_main_deal_minutes)
        CustomFontTextView tvMinutes;
        @BindView(R.id.tv_main_deal_seconds)
        CustomFontTextView tvSeconds;
        @BindView(R.id.timer_container)
        ConstraintLayout loutTime;


        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public void countDownStart(final String futureTime, final FooterViewHolder footerViewHolder) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// Please here set your event date//YYYY-MM-DD

                    Date futureDate = dateFormat.parse(futureTime);
                    Date currentDate = new Date();
                    //Date currentDate = startDateFormat.parse(startTime);

                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;

                        footerViewHolder.tvDays.setText("" + String.format("%02d", days));
                        footerViewHolder.tvHours.setText("" + String.format("%02d", hours));
                        footerViewHolder.tvMinutes.setText(""
                                + String.format("%02d", minutes));
                        footerViewHolder.tvSeconds.setText(""
                                + String.format("%02d", seconds));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


    }

    public interface ContainerClickListener {
        public void onItemClickListener(String id, String title);
    }
    public void setCustomButtonListner(ContainerClickListener listener) {
        this.customListener = listener;
    }
}
