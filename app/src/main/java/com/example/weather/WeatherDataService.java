package com.example.weather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String cityID);
    }

    public void getCityId (String cityName, final VolleyResponseListener volleyResponseListener){
        String url = QUERY_FOR_CITY_ID +cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("Something wrong!");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByIdResponse{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForescastById(String cityID, ForecastByIdResponse forecastByIdResponse){
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();

        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");

                    for(int i = 0; i < consolidated_weather_list.length(); i++) {
                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject days = (JSONObject) consolidated_weather_list.get(i);
                        one_day_weather.setId(days.getInt("id"));
                        one_day_weather.setWeather_state_name(days.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(days.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(days.getString("wind_direction_compass"));
                        one_day_weather.setCreated(days.getString("created"));
                        one_day_weather.setApplicable_date(days.getString("applicable_date"));
                        one_day_weather.setMin_temp(days.getLong("min_temp"));
                        one_day_weather.setMax_temp(days.getLong("max_temp"));
                        one_day_weather.setThe_temp(days.getLong("the_temp"));
                        one_day_weather.setWind_speed(days.getLong("wind_speed"));
                        one_day_weather.setWind_direction(days.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(days.getInt("air_pressure"));
                        one_day_weather.setHumidity(days.getInt("humidity"));
                        one_day_weather.setVisibility(days.getLong("visibility"));
                        one_day_weather.setPredictability(days.getInt("predictability"));
                        weatherReportModels.add(one_day_weather);
                    }

                    forecastByIdResponse.onResponse(weatherReportModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forecastByIdResponse.onError("Something wrong!");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByNameResponse{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForescastByName(String cityName, ForecastByNameResponse forecastByNameResponse){
        getCityId(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {}
            @Override
            public void onResponse(String cityID) {
                getCityForescastById(cityID, new ForecastByIdResponse() {
                    @Override
                    public void onError(String message) {}
                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        forecastByNameResponse.onResponse(weatherReportModels);
                    }
                });
            }
        });

    }
}
