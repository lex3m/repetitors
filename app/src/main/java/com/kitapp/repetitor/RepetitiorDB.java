package com.kitapp.repetitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.kitapp.repetitor.entities.City;
import com.kitapp.repetitor.entities.Repetitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by denis on 9/19/17.
 */

public class RepetitiorDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context context;

    private static final String DATABASE_NAME = "RepetitorDB";

    private static final String TABLE_REGIONS = "regions";
    private static final String TABLE_CITIES = "cities";
    private static final String TABLE_DISCIPLINES = "disciplines";
    private static final String TABLE_REPETITORS = "repetitors";

    private static final String KEY_CITY_ID = "city_id";
    private static final String KEY_CITY_NAME = "city_name";
    private static final String KEY_CITY_TYPE = "city_type";

    private static final String KEY_REGION_ID = "region_id";
    private static final String KEY_REGION_NAME = "region_name";

    private static final String KEY_REPETITOR_ID = "repetitor_id";
    private static final String KEY_FIO = "fio";
    private static final String KEY_AGE = "age";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DISCIPLINE = "discipline";
    private static final String KEY_PRICE = "price";
    private static final String KEY_UNITS = "units";
    private static final String KEY_PLACE = "place";
    private static final String KEY_STAGE = "stage";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PHONE2 = "phone2";
    private static final String KEY_PRICE_6_9 = "price_6_9";
    private static final String KEY_PRICE_10_11 = "price_10_11";
    private static final String KEY_PRICE_SKYPE = "price_skype";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_FAVORITE = "favorite";

    RepetitiorDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_REGIONS + "("
                + KEY_REGION_ID + " INTEGER,"
                + KEY_REGION_NAME + " TEXT)";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_CITIES + "("
                + KEY_CITY_ID + " INTEGER,"
                + KEY_CITY_NAME + " TEXT,"
                + KEY_CITY_TYPE + " TEXT,"
                + KEY_REGION_ID + " INTEGER)";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_DISCIPLINES + "("
                + KEY_DISCIPLINE + " TEXT)";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_REPETITORS + "("
                + KEY_REPETITOR_ID + " INTEGER,"
                + KEY_FIO + " TEXT,"
                + KEY_AGE + " INTEGER,"
                + KEY_CITY_ID + " INTEGER,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_DISCIPLINE + " TEXT,"
                + KEY_PRICE + " INTEGER,"
                + KEY_UNITS + " TEXT,"
                + KEY_PLACE + " TEXT,"
                + KEY_STAGE + " INTEGER,"
                + KEY_PHONE + " TEXT,"
                + KEY_PHONE2 + " TEXT,"
                + KEY_PRICE_6_9 + " INTEGER,"
                + KEY_PRICE_10_11 + " INTEGER,"
                + KEY_PRICE_SKYPE + " INTEGER,"
                + KEY_COMMENTS + " TEXT,"
                + KEY_FAVORITE + " INTEGER)";
        sqLiteDatabase.execSQL(sql);
        //initData();
    }

    public void init() {
        if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("initialized", false)) {
            initData();
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("initialized", true).apply();
        }
    }

    private void initData() {
        SQLiteDatabase db = getWritableDatabase();
        JSONObject data = loadDataFromJson(R.raw.regions_json);
        if (data != null) {
            db.beginTransaction();
            try {
                JSONArray array = data.getJSONArray("regions");
                for (int i = 0; i < array.length(); i++) {
                    ContentValues values = new ContentValues();
                    values.put(KEY_REGION_ID, array.getJSONObject(i).getInt(KEY_REGION_ID));
                    values.put(KEY_REGION_NAME, array.getJSONObject(i).getString(KEY_REGION_NAME));
                    db.insertWithOnConflict(TABLE_REGIONS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        data = loadDataFromJson(R.raw.cities_json);
        if (data != null) {
            db.beginTransaction();
            try {
                JSONArray array = data.getJSONArray("cities");
                for (int i = 0; i < array.length(); i++) {
                    ContentValues values = new ContentValues();
                    values.put(KEY_CITY_ID, array.getJSONObject(i).getInt(KEY_CITY_ID));
                    values.put(KEY_CITY_NAME, array.getJSONObject(i).getString(KEY_CITY_NAME));
                    values.put(KEY_CITY_TYPE, array.getJSONObject(i).getString(KEY_CITY_TYPE));
                    values.put(KEY_REGION_ID, array.getJSONObject(i).getInt(KEY_REGION_ID));
                    db.insertWithOnConflict(TABLE_CITIES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        data = loadDataFromJson(R.raw.disciplines_json);
        if (data != null) {
            db.beginTransaction();
            try {
                JSONArray array = data.getJSONArray("disciplines");
                for (int i = 0; i < array.length(); i++) {
                    ContentValues values = new ContentValues();
                    values.put(KEY_DISCIPLINE, array.getString(i));
                    db.insertWithOnConflict(TABLE_DISCIPLINES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        data = loadDataFromJson(R.raw.repetitors_json);
        if (data != null) {
            db.beginTransaction();
            try {
                JSONArray array = data.getJSONArray("repetitors");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    ContentValues values = new ContentValues();
                    values.put(KEY_REPETITOR_ID, object.getInt(KEY_REPETITOR_ID));
                    values.put(KEY_FIO, object.getString(KEY_FIO));
                    values.put(KEY_AGE, object.getInt(KEY_AGE));
                    values.put(KEY_CITY_ID, object.getInt(KEY_CITY_ID));
                    values.put(KEY_ADDRESS, object.getString(KEY_ADDRESS));
                    values.put(KEY_DISCIPLINE, object.getString(KEY_DISCIPLINE));
                    values.put(KEY_PRICE, object.getInt(KEY_PRICE));
                    values.put(KEY_UNITS, object.getString(KEY_UNITS));
                    values.put(KEY_PLACE, object.getString(KEY_PLACE));
                    values.put(KEY_STAGE, object.getInt(KEY_STAGE));
                    values.put(KEY_PHONE, object.getString(KEY_PHONE));
                    values.put(KEY_PHONE2, object.getString(KEY_PHONE2));
                    values.put(KEY_PRICE_6_9, object.getInt(KEY_PRICE_6_9));
                    values.put(KEY_PRICE_10_11, object.getInt(KEY_PRICE_10_11));
                    values.put(KEY_PRICE_SKYPE, object.getInt(KEY_PRICE_SKYPE));
                    values.put(KEY_COMMENTS, object.getString(KEY_COMMENTS));
                    values.put(KEY_FAVORITE, 0);
                    db.insertWithOnConflict(TABLE_REPETITORS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        db.close();
    }

    ArrayList<Repetitor> getFilteredRepetitorsList(String discipline, int city_id, int startPrice, int endPrice) {
        ArrayList<Repetitor> resultList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String searchCriteria = "";
        if (discipline != null) {
            searchCriteria += KEY_DISCIPLINE + " LIKE '%" + discipline + "%' ";
        }
        if (city_id != -1) {
            if (!searchCriteria.isEmpty()) searchCriteria += " AND ";
            searchCriteria += KEY_CITY_ID + "=" + String.valueOf(city_id);
        }
        if (startPrice >= 0) {
            if (!searchCriteria.isEmpty()) searchCriteria += " AND ";
            searchCriteria += KEY_PRICE + ">=" + String.valueOf(startPrice);
        }
        if (endPrice >= 0) {
            if (!searchCriteria.isEmpty()) searchCriteria += " AND ";
            searchCriteria += KEY_PRICE + "<=" + String.valueOf(endPrice);
        }
        Cursor c;
        if (!searchCriteria.isEmpty()) {
            c = db.query(TABLE_REPETITORS,
                    new String[]{KEY_REPETITOR_ID, KEY_FIO, KEY_AGE, KEY_CITY_ID, KEY_ADDRESS,
                            KEY_DISCIPLINE, KEY_PRICE, KEY_UNITS, KEY_PLACE, KEY_STAGE, KEY_PHONE,
                            KEY_PHONE2, KEY_PRICE_6_9, KEY_PRICE_10_11, KEY_PRICE_SKYPE, KEY_COMMENTS,
                            KEY_FAVORITE},
                    searchCriteria, null, null, null, null);
        } else {
            c = db.rawQuery("SELECT * FROM " + TABLE_REPETITORS, null);
        }
        if (c.moveToFirst()) {
            do {
                Repetitor r = new Repetitor(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4),
                        c.getString(5), c.getInt(6), c.getString(7), c.getString(8), c.getInt(9), c.getString(10),
                        c.getString(11), c.getString(15));
                resultList.add(r);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return resultList;
    }

    ArrayList<Repetitor> getFavoritesList() {
        ArrayList<Repetitor> resultList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_REPETITORS,
                new String[]{KEY_REPETITOR_ID, KEY_FIO, KEY_AGE, KEY_CITY_ID, KEY_ADDRESS,
                        KEY_DISCIPLINE, KEY_PRICE, KEY_UNITS, KEY_PLACE, KEY_STAGE, KEY_PHONE,
                        KEY_PHONE2, KEY_PRICE_6_9, KEY_PRICE_10_11, KEY_PRICE_SKYPE, KEY_COMMENTS,
                        KEY_FAVORITE},
                KEY_FAVORITE + "=1", null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Repetitor r = new Repetitor(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4),
                        c.getString(5), c.getInt(6), c.getString(7), c.getString(8), c.getInt(9), c.getString(10),
                        c.getString(11), c.getString(15));
                resultList.add(r);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return resultList;
    }

    public void setFavoriteById(int id, boolean favorite) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE, favorite ? 1 : 0);
        db.update(TABLE_REPETITORS, values, KEY_REPETITOR_ID + "=?", new String[] {String.valueOf(id)});
        db.close();
    }

    Repetitor getRepetitorById(int id) {
        Repetitor r = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_REPETITORS,
                new String[]{KEY_REPETITOR_ID, KEY_FIO, KEY_AGE, KEY_CITY_ID, KEY_ADDRESS,
                        KEY_DISCIPLINE, KEY_PRICE, KEY_UNITS, KEY_PLACE, KEY_STAGE, KEY_PHONE,
                        KEY_PHONE2, KEY_PRICE_6_9, KEY_PRICE_10_11, KEY_PRICE_SKYPE, KEY_COMMENTS,
                        KEY_FAVORITE},
                KEY_REPETITOR_ID + "=" + String.valueOf(id), null, null, null, null);
        if (c.moveToFirst()) {
            r = new Repetitor(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4),
                    c.getString(5), c.getInt(6), c.getString(7), c.getString(8), c.getInt(9), c.getString(10),
                    c.getString(11), c.getString(15));
        }
        c.close();
        db.close();
        return r;
    }

    ArrayList<City> getCitiesList() {
        ArrayList<City> resultList = new ArrayList<>();
        resultList.add(new City(-1, context.getString(R.string.all_cities), "", -1));
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + TABLE_CITIES, null);
        if (c.moveToFirst()) {
            do {
                City city = new City(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3));
                resultList.add(city);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return resultList;
    }

    ArrayList<String> getDisciplinesList() {
        ArrayList<String> resultList = new ArrayList<>();
        resultList.add(context.getString(R.string.all_disciplines));
        SQLiteDatabase db = getReadableDatabase();
        //Cursor c = db.query(true, TABLE_REPETITORS, new String[] {KEY_DISCIPLINE}, null, null, KEY_DISCIPLINE, null, null, null);
        //Cursor c = db.rawQuery("SELECT * DISTINCT " + KEY_DISCIPLINE + " FROM " + TABLE_REPETITORS, null);
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DISCIPLINES, null);
        if (c.moveToFirst()) {
            do {
                resultList.add(c.getString(0));
                //resultList.add(c.getString(5));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return resultList;
    }

    private JSONObject loadDataFromJson(int resourceId) {
        InputStream is;
        JSONObject object = null;
        try {
            is = context.getResources().openRawResource(resourceId);
            //String initJson = IOUtils.toString(is, "UTF-8");
            String initJson = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                initJson += line;
                line = reader.readLine();
            }
            object = new JSONObject(initJson);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("initialized", false).apply();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REPETITORS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REGIONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINES);
        onCreate(sqLiteDatabase);
    }
}
