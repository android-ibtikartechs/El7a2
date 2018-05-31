package com.ibtikartechs.apps.el7a2.data.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.models.GovernroteModel;
import com.ibtikartechs.apps.el7a2.data.models.SearchProductModel;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<SearchProductModel> resultList = new ArrayList<>();
    ViewHolder viewHolder;
    OnAutoItemClickListner onAutoItemClickListner;

    public SearchAutoCompleteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public SearchProductModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final SearchProductModel item = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.view_autocomp_tex_city_row, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.tvProduct.setText(item.getProductName());
        viewHolder.loutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAutoItemClickListner.onAutoProductItemClicked(item.getProductId(), item.getProductName());
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence != null) {


                    HttpURLConnection conn = null;
                    InputStream input = null;
                    try {
                        URL url = null;
                            url = new URL(buildSearchUrl());
                        conn = (HttpURLConnection) url.openConnection();

                        conn.setReadTimeout(10000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        ContentValues params = new ContentValues();
                        params.put("keyword", charSequence.toString());
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getQuery(params));
                        writer.flush();
                        writer.close();
                        os.close();
                        input = conn.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                        BufferedReader buffer = new BufferedReader(reader, 8192);
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = buffer.readLine()) != null) {
                            builder.append(line);
                        }

                        ArrayList<SearchProductModel> suggestions = new ArrayList<>();
                        JSONObject jsnMainObject = new JSONObject(builder.toString());
                        if (jsnMainObject.getString("status").equals("OK")) {
                            JSONArray jsnproductsArray = jsnMainObject.getJSONArray("Result");
                            if (jsnproductsArray != null) {

                                for (int i = 0; i < jsnproductsArray.length(); i++) {
                                    JSONObject jsnItemObject = jsnproductsArray.getJSONObject(i);
                                        suggestions.add(new SearchProductModel(jsnItemObject.getString("name"), jsnItemObject.getString("id")));
                                }


                            }

                        }
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                        resultList = suggestions;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        if (input != null) {
                            try {
                                input.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (conn != null) conn.disconnect();
                    }


                }
                return filterResults;

            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {

                    resultList = (List<SearchProductModel>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }


    private String getQuery(ContentValues params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : params.valueSet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }

        return result.toString();
    }

    private String buildSearchUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("search");
        String url = builder.build().toString();
        return url;
    }

    public interface OnAutoItemClickListner {
        public void onAutoProductItemClicked(String productId, String productName);
    }

    public void setOnAutoLocationItemClickListner(OnAutoItemClickListner onAutoGovernItemClickListner)
    {
        this.onAutoItemClickListner = onAutoGovernItemClickListner;
    }

    public class ViewHolder{
        @BindView(R.id.lout_container)
        ConstraintLayout loutContainer;
        @BindView(R.id.tv_city)
        CustomFontTextView tvProduct;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
