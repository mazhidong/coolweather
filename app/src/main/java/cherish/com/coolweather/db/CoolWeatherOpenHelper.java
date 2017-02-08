package cherish.com.coolweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cherish on 2017/2/8.
 */

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_PROVINCE="CREATE TABLE Province(\n" +
            "id integer PRIMARY KEY AUTOINCREMENT,\n" +
            "province_name text,\n" +
            "province_code text)";
    private static final String CREATE_CITY="CREATE TABLE City(\n" +
            "id integer PRIMARY KEY AUTOINCREMENT,\n" +
            "city_name text,\n" +
            "city_code text,\n" +
            "province_id integer)";

    private static final String CREATE_COUNTRY="CREATE TABLE Country(\n" +
            "id integer PRIMARY KEY AUTOINCREMENT,\n" +
            "country_name text,\n" +
            "country_code text,\n" +
            "city_id integer)";


    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
