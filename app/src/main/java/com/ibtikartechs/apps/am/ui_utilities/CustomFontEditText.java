package com.ibtikartechs.apps.am.ui_utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

import com.ibtikartechs.apps.am.R;

public class CustomFontEditText extends AppCompatEditText {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontEditText);

        String fontName = attributeArray.getString(R.styleable.CustomFontEditText_custom_font_edi_text);
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, fontName, textStyle);
        setTypeface(customFont);

        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName, int textStyle) {
        if (fontName.contentEquals(context.getString(R.string.font_name_helvatican))) {
            return FontCache.getTypeface("fonts/helveticaneueltarabicroman1.ttf", context);
        }
        else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            return null;
        }
    }

}
