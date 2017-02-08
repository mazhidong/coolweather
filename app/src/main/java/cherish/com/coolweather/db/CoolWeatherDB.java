package cherish.com.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cherish.com.coolweather.model.City;
import cherish.com.coolweather.model.Country;
import cherish.com.coolweather.model.Province;

/**
 * Created by cherish on 2017/2/8.
 */

public class CoolWeatherDB {
    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper coolWeatherOpenHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = coolWeatherOpenHelper.getWritableDatabase();
    }

    public synchronized CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    public void saveProvince(Province province) {
        ContentValues values = new ContentValues();
        values.put("province_name",province.getProvinceName());
        values.put("province_code", province.getProvinceCode());
        db.insert("Province", null, values);
    }

    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("provincce_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city) {
        ContentValues values = new ContentValues();
        values.put("city_name",city.getCityName());
        values.put("city_code", city.getCityCode());
        values.put("province_id",city.getProvinceID());
        db.insert("City", null, values);
    }

    public List<City> loadCitys() {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceID(cursor.getInt(cursor.getColumnIndex("provincce_id")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCountry(Country country) {
        ContentValues values = new ContentValues();
        values.put("country_name",country.getCountryName());
        values.put("country_code", country.getCountryCode());
        values.put("city_id",country.getCityId());
        db.insert("Country", null, values);
    }

    public List<Country> loadCountrys() {
        List<Country> list = new ArrayList<Country>();
        Cursor cursor = db.query("Country",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(country);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
