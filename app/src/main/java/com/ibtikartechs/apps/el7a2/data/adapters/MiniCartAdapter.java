package com.ibtikartechs.apps.el7a2.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;

import java.util.List;

public class MiniCartAdapter extends RecyclerView.Adapter<MiniCartAdapter.MyViewHolder> {
    Context context;
    List<CartListModel> cartList;

    public MiniCartAdapter(Context context, List<CartListModel> carts) {
        this.context = context;
        this.cartList = carts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mini_cart_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.total.setText(Integer.valueOf(cartList.get(position).getPrice()) * Integer.valueOf(cartList.get(position).getAmount()) + " EGP");
        holder.price.setText("(" + cartList.get(position).getPrice() + "X" + cartList.get(position).getAmount() + ")");
        holder.qty.setText("Qty : " + cartList.get(position).getAmount() + "");
        holder.title.setText(cartList.get(position).getTitle());
        Glide.with(context).load(cartList.get(position).getImg_url()).asBitmap().into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        ImageView photo;
        TextView title, price, total, qty;

        public MyViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.cart_container);
            photo = itemView.findViewById(R.id.photo_img);
            title = itemView.findViewById(R.id.title_txt);
            price = itemView.findViewById(R.id.price_txt);
            qty = itemView.findViewById(R.id.qty_txt);
            total = itemView.findViewById(R.id.total_txt);
        }
    }
}