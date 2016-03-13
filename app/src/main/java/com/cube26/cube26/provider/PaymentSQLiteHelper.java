package com.cube26.cube26.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class PaymentSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FavouritePayment";
    static final String TABLE_NAME = "favourites";
    static final int DATABASE_VERSION = 1;

    //fields for database
    public static final String ROW_ID = "id";
    public static final String ID ="paymentId";
    public static final String TITLE = "title";
    public static final String IMAGE_PATH = "imagePath";
    public static final String DESCRIPTION = "description";
    public static final String CURRENCY = "currency";
    public static final String DOCUMENT = "document";
    public static final String TRANSACTION_FEE = "transactionFee";
    public static final String SETUP_FEE = "setupFee";

    static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME +" ( "+ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + ID+ " TEXT NOT NULL, " + "" +
            TITLE+" TEXT NOT NULL, " + IMAGE_PATH+" TEXT NOT NULL, " + DESCRIPTION+" TEXT NOT NULL, " +CURRENCY+" TEXT NOT NULL, " +DOCUMENT+" TEXT NOT NULL, " +TRANSACTION_FEE+" TEXT NOT NULL, " + SETUP_FEE+" TEXT NOT NULL); ";

    public PaymentSQLiteHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creating table",""+CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }
}
