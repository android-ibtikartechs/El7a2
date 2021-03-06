package com.ibtikartechs.apps.am.data.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.ibtikartechs.apps.am.data.models.CartListModel;
import com.ibtikartechs.apps.am.data.models.UserModel;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by ahmedyehya on 4/30/18.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    Context context;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "app_database";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE "+ El7a2Contract.CartEntry.TABLE_CART_ITEMS + "(" +
                El7a2Contract.CartEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                El7a2Contract.CartEntry.COLUMN_ITEM_ID + " TEXT NOT NULL, "+
                El7a2Contract.CartEntry.COLUMN_ITEM_TITLE + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_ITEM_PRICE + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT + " TEXT DEFAULT 1, "+
                El7a2Contract.CartEntry.COLUMN_ITEM_IMG_URL + " TEXT);";

        String SQL_CREAT_USERS_TABLE = "CREATE TABLE " + El7a2Contract.CartEntry.TABLE_USER + "(" +
                El7a2Contract.CartEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                El7a2Contract.CartEntry.COLUMN_USER_ID + " TEXT NOT NULL, "+
                El7a2Contract.CartEntry.COLUMN_USER_NAME + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_USER_EMAIL + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_USER_MOBILE_NUM + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_USER_ADDRESS + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_USER_GOV + " TEXT, "+
                El7a2Contract.CartEntry.COLUMN_USER_CITY + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREAT_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
// Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + El7a2Contract.CartEntry.TABLE_CART_ITEMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + El7a2Contract.CartEntry.TABLE_USER);
        // Create tables again
        onCreate(sqLiteDatabase);
    }
    public void addUser (String id, String name, String email, String mobNum, String address, String gov, String city)
    {
        ContentValues values = new ContentValues();
        values.put(El7a2Contract.CartEntry.COLUMN_USER_ID,id);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_NAME,name);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_EMAIL, email);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_MOBILE_NUM, mobNum);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_ADDRESS, address);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_GOV, gov);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_CITY, city);

        Uri newUri;
        newUri = context.getContentResolver().insert(El7a2Contract.CartEntry.CONTENT_URI_USER_PATH,values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Log.d("SqliteHandler", "addUser: insert is failed");
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Log.d("SqliteHandler", "addUser: insert is done");
        }

    }



    public Uri addItemsToCart (String itemId, String title, String price, String imgUrl, String quantity){
        ContentValues values = new ContentValues();
        values.put(El7a2Contract.CartEntry.COLUMN_ITEM_ID,itemId);
        values.put(El7a2Contract.CartEntry.COLUMN_ITEM_TITLE,title);
        values.put(El7a2Contract.CartEntry.COLUMN_ITEM_PRICE,price);
        values.put(El7a2Contract.CartEntry.COLUMN_ITEM_IMG_URL,imgUrl);
        values.put(El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT, quantity);

        Uri newUri;
        newUri = context.getContentResolver().insert(El7a2Contract.CartEntry.CONTENT_URI,values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Log.d("SqliteHandler", "addItemsToCart: insert is failed");
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Log.d("SqliteHandler", newUri.toString());
        }
        return newUri;
    }

    public boolean isUserLogedIn ()
    {


            String[] projection = {El7a2Contract.CartEntry._ID,};
            Cursor cursor = context.getContentResolver().query(El7a2Contract.CartEntry.CONTENT_URI_USER_PATH, projection,null,null,null);

        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        else {
            cursor.close();
            return true;
        }

    }

    public UserModel gryUser()
    {
        String dbId = null, userId = null, userName = null, userEmail = null, userMobileNum = null, userAddress = null, userGov =null, userCity =null;
        String[] projection = {
                El7a2Contract.CartEntry._ID,
                El7a2Contract.CartEntry.COLUMN_USER_ID,
                El7a2Contract.CartEntry.COLUMN_USER_NAME,
                El7a2Contract.CartEntry.COLUMN_USER_EMAIL,
                El7a2Contract.CartEntry.COLUMN_USER_MOBILE_NUM,
                El7a2Contract.CartEntry.COLUMN_USER_ADDRESS,
                El7a2Contract.CartEntry.COLUMN_USER_GOV,
                El7a2Contract.CartEntry.COLUMN_USER_CITY,

        };
        Cursor cursor = context.getContentResolver().query(El7a2Contract.CartEntry.CONTENT_URI_USER_PATH, projection,null,null,null);
        if (cursor.moveToFirst()){
            dbId = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry._ID));
            userId = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_ID));
            userName = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_NAME));
            userEmail = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_EMAIL));
            userMobileNum = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_MOBILE_NUM));
            userAddress = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_ADDRESS));
            userGov = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_GOV));
            userCity = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_USER_CITY));
        }
        cursor.close();
       return new UserModel(dbId, userId, userName, userEmail, userMobileNum, userAddress, userGov, userCity);
    }

    public Callable<Integer> getCartItemsNumber ()
    {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Cursor cursor = null;
               try {
                   String[] projection = {
                           El7a2Contract.CartEntry._ID,};
                    cursor = context.getContentResolver().query(El7a2Contract.CartEntry.CONTENT_URI, projection,null,null,null);
                   return cursor.getCount();
               }
               finally {
                   if (cursor != null)
                       cursor.close();
               }
                
            }
        };
    }

    public ArrayList<CartListModel> getCartList () {
        ArrayList<CartListModel> cartList = new ArrayList<>();
        String[] projection = {
                El7a2Contract.CartEntry._ID,
                El7a2Contract.CartEntry.COLUMN_ITEM_ID,
                El7a2Contract.CartEntry.COLUMN_ITEM_TITLE,
                El7a2Contract.CartEntry.COLUMN_ITEM_PRICE,
                El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT,
                El7a2Contract.CartEntry.COLUMN_ITEM_IMG_URL,
        };

        Cursor cursor = context.getContentResolver().query(El7a2Contract.CartEntry.CONTENT_URI, projection,null,null,null);

        if (cursor.moveToFirst()){
            do{
                String dbId = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry._ID));
                String itemId = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_ID));
                String title = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_TITLE));
                String price = cursor .getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_PRICE));
                String amount = cursor .getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT));
                String imgUrl = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_IMG_URL));
                cartList.add(new CartListModel(dbId,itemId,title,price,imgUrl,amount));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();
        return cartList;
    }

    public boolean isItemExistInCart (Uri uri)
    {
        String[] projection = {
                El7a2Contract.CartEntry._ID,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection,null,null,null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        else {
            cursor.close();
            return true;
        }
    }

    public CartListModel getItemById (Uri uri)
    {
        CartListModel cartListModel = null;
        String[] projection = {
                El7a2Contract.CartEntry._ID,
                El7a2Contract.CartEntry.COLUMN_ITEM_TITLE,
                El7a2Contract.CartEntry.COLUMN_ITEM_ID,
                El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT,
                El7a2Contract.CartEntry.COLUMN_ITEM_IMG_URL,
                El7a2Contract.CartEntry.COLUMN_ITEM_PRICE,
        };

        Cursor cursor = context.getContentResolver().query(uri, projection,null,null,null);

        if (cursor.moveToFirst()){
            String dbId = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry._ID));
            String itemId = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_ID));
            String title = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_TITLE));
            String price = cursor .getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_PRICE));
            String amount = cursor .getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT));
            String imgUrl = cursor.getString(cursor.getColumnIndex(El7a2Contract.CartEntry.COLUMN_ITEM_IMG_URL));
            cartListModel = new CartListModel(dbId, itemId, title, price, imgUrl, amount);
        }
   return cartListModel;
    }

    public Callable<Boolean> isItemExistInCartObserv(final Uri uri) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                // select * from users where _id is userId
                String[] projection = {
                        El7a2Contract.CartEntry._ID,
                };
                Cursor cursor = context.getContentResolver().query(uri, projection,null,null,null);
                if (cursor.getCount()<=0){
                    cursor.close();
                    return false;
                }
                else {
                    cursor.close();
                    return true;
                }
            }
        };
    }

    public void deleteFromDataBase(Uri uri)
    {
        int checkEffect = context.getContentResolver().delete(uri,null,null);
        if (checkEffect > 0) {
            // If the new content URI is null, then there was an error with insertion.

            /* Toast.makeText(context, "delete is done",
                    Toast.LENGTH_SHORT).show(); */
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
         /*   Toast.makeText(context,"delete is failed",
                    Toast.LENGTH_SHORT).show(); */
        }
    }

    public Callable<Boolean> deleteFromDataBaseObserved(final Uri uri)
    {
        return new Callable<Boolean>(){
            @Override
            public Boolean call() {
                int checkEffect = context.getContentResolver().delete(uri,null,null);
                if (checkEffect>0)
                    return true;
                else
                    return false;
            }
        };
    }

    public void editAmountofItem(Uri uri , String value)
    {
        ContentValues values = new ContentValues();
        values.put(El7a2Contract.CartEntry.COLUMN_ITEM_AMOUNT, value);
        context.getContentResolver().update(uri,values,null,null);
    }

    public void editUserData (Uri uri, String id, String name, String email, String mobNum, String address, String gov, String city)
    {
        ContentValues values = new ContentValues();
        values.put(El7a2Contract.CartEntry.COLUMN_USER_ID,id);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_NAME,name);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_EMAIL, email);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_MOBILE_NUM, mobNum);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_ADDRESS, address);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_GOV, gov);
        values.put(El7a2Contract.CartEntry.COLUMN_USER_CITY, city);
        context.getContentResolver().update(uri, values, null, null);
    }
}
