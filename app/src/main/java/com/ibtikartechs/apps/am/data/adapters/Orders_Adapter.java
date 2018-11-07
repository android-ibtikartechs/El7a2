package com.ibtikartechs.apps.am.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.utilities.Order;

import java.util.List;

public class Orders_Adapter extends RecyclerView.Adapter<Orders_Adapter.MyViewHolder> {
    Context context;
    List<Order> list;

    public Orders_Adapter(Context context, List<Order> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.content.setText(list.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.order_content_txt);
        }
    }
}
