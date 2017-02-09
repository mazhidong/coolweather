package cherish.com.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

import cherish.com.coolweather.db.CoolWeatherDB;
import cherish.com.coolweather.model.City;
import cherish.com.coolweather.model.Country;
import cherish.com.coolweather.model.Province;

/**
 * Created by cherish on 2017/2/9.
 */

public class Utility {
    public strictfp static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p:allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCityResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCitys = response.split(",");
            if (allCitys.length > 0 && allCitys != null) {
                for (String p:allCitys) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceID(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCountryResponse(CoolWeatherDB coolWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountrys = response.split(",");
            if (allCountrys != null && allCountrys.length > 0) {
                for (String p : allCountrys) {
                    String[] array = p.split("\\|");
                    Country country = new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    country.setCityId(cityId);
                    coolWeatherDB.saveCountry(country);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("weatherinfo");
                String cityName = object.getString("city");
                String weatherCode = object.getString("cityid");
                String temp1 = object.getString("temp1");
                String temp2 = object.getString("temp2");
                String weather = object.getString("weather");
                String ptime = object.getString("ptime");
                String[] imgs1 = object.getString("img1").split("\\.");
                String[] imgs2 = object.getString("img2").split("\\.");
                String img1 = imgs1[0];
                String img2 = imgs2[0];;
                saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weather,ptime,img1,img2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weather,
                                       String ptime,String img1,String img2) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if (Build.VERSION.SDK_INT >= 23) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            editor.putString("current_date", sdf.format(new Date()));
        } else {
            editor.putString("current_date", "");
        }

        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather", weather);
        editor.putString("ptime", ptime);
        editor.putString("img1", img1);
        editor.putString("img2", img2);
        editor.commit();
    }


}
