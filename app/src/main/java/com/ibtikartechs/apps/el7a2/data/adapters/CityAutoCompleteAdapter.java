package com.ibtikartechs.apps.el7a2.data.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.models.GovernroteModel;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class CityAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<GovernroteModel> resultList = new ArrayList<>();
    ViewHolder viewHolder;
    private List<GovernroteModel> governrotes = new ArrayList<>();
    OnAutoGovernItemClickListner onAutoLocationItemClickListner;

    public void setGovernrotes(GovernroteModel governroteModel) {
        this.governrotes.add(governroteModel);
    }

    public List<GovernroteModel> getGovernrotes() {
        return governrotes;
    }

    public CityAutoCompleteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public GovernroteModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GovernroteModel item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_autocomp_tex_city_row, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvCity.setText(item.getCityName());
        viewHolder.loutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAutoLocationItemClickListner.onAutoLocationItemClicked(item.getId(), item.getCityName());
            }
        });

        return convertView;
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
                    try
                    {
                        URL url = new URL (buildUrl(charSequence.toString()));
                        conn = (HttpURLConnection) url.openConnection();
                        input = conn.getInputStream();
                        InputStreamReader reader = new InputStreamReader (input, "UTF-8");
                        BufferedReader buffer = new BufferedReader (reader, 8192);
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = buffer.readLine()) != null)
                        {
                            builder.append (line);
                        }

                        ArrayList<GovernroteModel> suggestions = new ArrayList<>();
                        JSONObject jsnMainObject = new JSONObject(builder.toString());
                        if (jsnMainObject.getString("status").equals("OK"))
                        {
                            JSONArray jsnGovArray = jsnMainObject.getJSONArray("List");
                            if (jsnGovArray != null) {

                                for (int i = 0; i < jsnGovArray.length(); i++) {
                                    JSONObject jsnItemObject = jsnGovArray.getJSONObject(i);
                                    suggestions.add(new GovernroteModel(jsnItemObject.getString("name2"),jsnItemObject.getString("id")));
                                }


                            }

                        }
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                        resultList = suggestions;
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        if (input != null)
                        {
                            try
                            {
                                input.close();
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        if (conn != null) conn.disconnect();
                    }


/*

                    OkHttpClient client = new OkHttpClient();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(buildUrl(charSequence.toString()))
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {


                            try {
                                JSONObject jsnMainObject = new JSONObject(response.body().string());
                                if (jsnMainObject.getString("status").equals("OK"))
                                {
                                    JSONArray jsnGovArray = jsnMainObject.getJSONArray("List");
                                    if (jsnGovArray != null) {

                                        for (int i = 0; i < jsnGovArray.length(); i++) {
                                            JSONObject jsnItemObject = jsnGovArray.getJSONObject(i);
                                            setGovernrotes(new GovernroteModel(jsnItemObject.getString("name2"),jsnItemObject.getString("id")));
                                        }


                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

*/

                    // Assign the data to the FilterResults


                }
                return filterResults;

            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    resultList = (List<GovernroteModel>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }



    private String buildUrl(String searchQuery) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(StaticValues.URL_AUOTHORITY)
                .appendPath("mob")
                .appendPath("citieslike")
                .appendPath(searchQuery);
        String url = builder.build().toString();
        return url;
    }

    public class ViewHolder{
        @BindView(R.id.lout_container)
        ConstraintLayout loutContainer;
        @BindView(R.id.tv_city)
        CustomFontTextView tvCity;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnAutoGovernItemClickListner {

        public void onAutoLocationItemClicked(String placeId, String name);
    }

    public void setOnAutoLocationItemClickListner(OnAutoGovernItemClickListner onAutoGovernItemClickListner)
    {
        this.onAutoLocationItemClickListner = onAutoGovernItemClickListner;
    }

}
