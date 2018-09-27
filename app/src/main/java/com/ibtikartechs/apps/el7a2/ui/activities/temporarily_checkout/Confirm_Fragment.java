package com.ibtikartechs.apps.el7a2.ui.activities.temporarily_checkout;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hosamazzam.volleysimple.VolleySimple;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.StaticValues;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.data.adapters.MiniCartAdapter;
import com.ibtikartechs.apps.el7a2.data.models.AddressModel;
import com.ibtikartechs.apps.el7a2.data.models.CartListModel;
import com.ibtikartechs.apps.el7a2.utilities.OrderResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Confirm_Fragment extends Fragment  {
    TextView contiune, address_info_txt, card_edit, address_edit, total_txt, subtotal_txt, charge_txt;
    RecyclerView cartList;
    AddressModel address;
    int deliver_option = -1;
    String note = "";
    VolleySimple volleySimple;
    DataManager dataManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_confirm, container, false);
        dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        volleySimple = VolleySimple.getInstance(getContext());
        initView(rootview);
        listener();
        initTotal();
        return rootview;
    }

    public void initView(View view) {
        address_info_txt = view.findViewById(R.id.address_info_txt);
        //card_edit = view.findViewById(R.id.cart_edit_txt);
        //address_edit = view.findViewById(R.id.address_edit_txt);
        contiune = view.findViewById(R.id.continue_btn);
        total_txt = view.findViewById(R.id.total_txt);
        subtotal_txt = view.findViewById(R.id.subtotal_txt);
        charge_txt = view.findViewById(R.id.charge_txt);

        cartList = view.findViewById(R.id.cart_list);
        cartList.setNestedScrollingEnabled(false);
        cartList.setLayoutManager(new LinearLayoutManager(getContext()));
        cartList.setAdapter(new MiniCartAdapter(getContext(), dataManager.getCartItemsList()));

        if (!note.equals("")) note += "\n";
        //address_info_txt.setText(address.getAddress() + "\n" + "الشحن : " + getOptionString(deliver_option) + note);
    }

    public void listener() {
        contiune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addorder();
            }
        });

        /*card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });*/

       /* address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
            }
        });*/
    }


    public void setdata(AddressModel address, int option, String note, boolean isCash) {
        this.address = address;
        this.deliver_option = option;
        this.note = note;
        address_info_txt.setText(address.getName()+ "\n"+address.getAddress() + "\n" +"ملاحظات : " +note);
        if (address.getFees() == null || address.getFees().isEmpty())
            charge_txt.setText("10");
        else
            charge_txt.setText(address.getFees());
        double totPrice;
        if (address.getFees() == null || address.getFees().isEmpty())
            totPrice = updateTotalPrice() + Integer.valueOf("10");
        else
            totPrice = updateTotalPrice() + Integer.valueOf(address.getFees());
        total_txt.setText(totPrice + " EGP");
    }

    public void initTotal() {
        subtotal_txt.setText(updateTotalPrice() + " EGP");
        //charge_txt.setText(getOptionDouble(deliver_option) == 0 ? "مجانى" : getOptionDouble(deliver_option) + " EGP");
        //charge_txt.setText(address.getFees());

    }

    public String getOptionString(int option) {
        if (option == 1) return "مجانى";
        if (option == 2) return "2-3 يوم";
        if (option == 3) return "اليوم التالى";
        return "";
    }

    public double getOptionDouble(int option) {
        if (option == 1) return 0;
        if (option == 2) return 49;
        if (option == 3) return 149;
        return 0;
    }

    public void addorder() {
        ProgressDialog progressDialog = ProgressDialog.show(getContext(), "انتظر من فضلك", "", false, false);
        Map<String, String> map = new HashMap<>();
        map.put("bcharge", String.valueOf(deliver_option));
        map.put("bcomment", note);
        map.put("buser", String.valueOf(dataManager.getUser().getUserId()));
        map.put("baddress", String.valueOf(address.getAddressId()));
        int size = dataManager.getCartItemsList().size();
        List<CartListModel> items = dataManager.getCartItemsList();
        for (int i = 0; i < size; i++) {
            //if (items.get(i).isDeal())
                map.put("mybookdeals[" + items.get(i).getId() + "]", items.get(i).getAmount() + "");
            /*else
                map.put("mybookproducts[" + items.get(i).getId() + "]", items.get(i).getQty() + "");
                */
        }
        volleySimple.asyncStringPost("http://"+StaticValues.URL_AUOTHORITY +"/mob/" +"addbook", map, new VolleySimple.NetworkListener<String>() {
            @Override
            public void onResponse(String s) {
                OrderResponse response = new Gson().fromJson(s, OrderResponse.class);
                if (response.getStatus().equals("OK")) {
                    Toast.makeText(getContext(), "تم ارسال طلبك", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    //El7a2APP.clearCartList(getContext());

                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        }, progressDialog);
    }

    public Integer updateTotalPrice()
    {
        ArrayList<CartListModel> cartItemsArrayList = dataManager.getCartItemsList();
        Integer totalPrice = 0;
        int arrayLength = cartItemsArrayList.size();
        for (int i = 0; i<arrayLength;i++)
        {
            final CartListModel item = cartItemsArrayList.get(i);
            totalPrice+=Integer.parseInt(item.getPrice())*Integer.parseInt(item.getAmount());

        }
        String totPrice = String.valueOf(totalPrice);
        //String resultPrice = totalPrice.toString()+"$";
        return totalPrice;
    }


}
