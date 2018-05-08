package com.ibtikartechs.apps.el7a2.data.db_helper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ahmedyehya on 5/6/18.
 */

public class El7a2Contract {
    public static final String CONTENT_AUTHORITY = "com.ibtikar.app.el7a2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CART_ITEMS = "cart";

    public static final class CartEntry implements BaseColumns {
        public final static String TABLE_CART_ITEMS = "cart_items";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ITEM_ID = "item_id";
        public final static String COLUMN_ITEM_IMG_URL = "item_img_url";
        public final static String COLUMN_ITEM_TITLE = "item_title";
        public final static String COLUMN_ITEM_PRICE = "item_price";
        public final static String COLUMN_ITEM_AMOUNT = "item_amount";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_CART_ITEMS);
    }

}
