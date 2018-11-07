package com.ibtikartechs.apps.am.ui_utilities;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmedyehya on 5/1/18.
 */

public class FontCache {
    private static Map<String, Typeface> sCachedFonts = new HashMap<String, Typeface>();

    public static Typeface getTypeface(String assetPath, Context context ) {
        if (!sCachedFonts.containsKey(assetPath)) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), assetPath);
            sCachedFonts.put(assetPath, tf);
        }

        return sCachedFonts.get(assetPath);
    }
}
