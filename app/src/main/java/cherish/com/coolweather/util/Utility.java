package cherish.com.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

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
}
