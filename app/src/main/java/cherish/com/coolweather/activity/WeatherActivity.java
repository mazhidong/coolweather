package cherish.com.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cherish.com.coolweather.R;
import cherish.com.coolweather.service.AutoUpdateService;
import cherish.com.coolweather.util.HttpCallbackListener;
import cherish.com.coolweather.util.HttpUtil;
import cherish.com.coolweather.util.Utility;

public class WeatherActivity extends Activity implements View.OnClickListener{
    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView ptimeText;
    private TextView currentDateText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView weatherText;
    private Button switchCity;
    private Button refreshWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        ptimeText = (TextView) findViewById(R.id.ptime);
        currentDateText = (TextView) findViewById(R.id.current_date);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        weatherText = (TextView) findViewById(R.id.weather);
        String countryCode = getIntent().getStringExtra("country_code");
        if (!TextUtils.isEmpty(countryCode)) {
            ptimeText.setText("同步中..");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countryCode);
        } else {
            showWeather();
        }
    }

    private void queryWeatherCode(String countryCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countryCode + ".xml";
        queryFromServer(address,"country");
    }

    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address,"weatherCode");
    }

    private void queryFromServer(String address, final String type) {
        Log.e("addresslast",address);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("country".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode=array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ptimeText.setText("同步失败..");
                    }
                });
            }
        });
    }

    private void showWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(sharedPreferences.getString("city_name",""));
        ptimeText.setText("今天"+sharedPreferences.getString("ptime","")+"发布");
        currentDateText.setText(sharedPreferences.getString("current_date",""));
        temp1Text.setText(sharedPreferences.getString("temp1",""));
        temp2Text.setText(sharedPreferences.getString("temp2",""));
        weatherText.setText(sharedPreferences.getString("weather",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                    break;
            case R.id.refresh_weather:
                ptimeText.setText("同步中..");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String weathercode = sharedPreferences.getString("weather_code","");
                if (!TextUtils.isEmpty(weathercode)) {
                    queryWeatherInfo(weathercode);
                }
                break;
            default:
                    break;
        }
    }
}
