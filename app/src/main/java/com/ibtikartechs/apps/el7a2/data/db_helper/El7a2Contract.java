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
    public static final String PATH_USER = "user";

    public static final class CartEntry implements BaseColumns {
        public final static String TABLE_CART_ITEMS = "cart_items";
        public final static String TABLE_USER = "user_table";
        // Cart Item Table
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ITEM_ID = "item_id";
        public final static String COLUMN_ITEM_IMG_URL = "item_img_url";
        public final static String COLUMN_ITEM_TITLE = "item_title";
        public final static String COLUMN_ITEM_PRICE = "item_price";
        public final static String COLUMN_ITEM_AMOUNT = "item_amount";

        // User Table
        public final static String COLUMN_USER_ID="user_id";
        public final static String COLUMN_USER_NAME="user_name";
        public final static String COLUMN_USER_EMAIL="user_emaol";
        public final static String COLUMN_USER_MOBILE_NUM="user_mob_num";
        public final static String COLUMN_USER_GOV="user_gov";
        public final static String COLUMN_USER_CITY="user_city";
        public final static String COLUMN_USER_ADDRESS="user_address";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_CART_ITEMS);
        public static final Uri CONTENT_URI_USER_PATH = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_USER);
    }

}
