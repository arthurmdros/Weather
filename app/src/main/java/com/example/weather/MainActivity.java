package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.btnGetCity = findViewById(R.id.btn_getCityId);
        this.mViewHolder.btnWeatherByID = findViewById(R.id.btn_getWeatherByCityId);
        this.mViewHolder.btnWeatherByName = findViewById(R.id.btn_getWeatherByCityName);
        this.mViewHolder.etDataInput = findViewById(R.id.et_dataInput);
        this.mViewHolder.lvWeatherReports = findViewById(R.id.lv_weatherReports);
    }

    private class ViewHolder{
        Button btnGetCity;
        Button btnWeatherByID;
        Button btnWeatherByName;
        EditText etDataInput;
        ListView lvWeatherReports;
    }
}