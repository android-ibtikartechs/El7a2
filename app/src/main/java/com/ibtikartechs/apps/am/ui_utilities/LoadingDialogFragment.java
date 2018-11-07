package com.ibtikartechs.apps.am.ui_utilities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibtikartechs.apps.am.R;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class LoadingDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_loading,container,false);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }


}
