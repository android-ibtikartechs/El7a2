package com.ibtikartechs.apps.am.ui.fragments.searchdialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.StaticValues;
import com.ibtikartechs.apps.am.data.adapters.SearchAutoCompleteAdapter;
import com.ibtikartechs.apps.am.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.am.ui_utilities.CustomAutoCompleteTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDialogFragment extends DialogFragment implements SearchAutoCompleteAdapter.OnAutoItemClickListner {
    @BindView(R.id.customAutoCompleteTextView)
    CustomAutoCompleteTextView atSearch;

    @BindView(R.id.layout_root)
    LinearLayout layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SearchAutoCompleteAdapter searchAutoCompleteAdapter = new SearchAutoCompleteAdapter(getActivity());
        searchAutoCompleteAdapter.setOnAutoLocationItemClickListner(this);
        atSearch.setAdapter(searchAutoCompleteAdapter);
        atSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAutoProductItemClicked(String productId, String productName) {
        getActivity().finish();
        atSearch.dismissDropDown();
        hideKeyboard();
        startActivity(MainDealDetailsActivity.getStartIntent(getActivity(),productId, StaticValues.PROD_FLAG, productName));
    }

    public void hideKeyboard() {
        View view = getActivity().findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
