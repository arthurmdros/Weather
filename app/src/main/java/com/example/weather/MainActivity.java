package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

        this.mViewHolder.btnGetCity.setOnClickListener(this);
        this.mViewHolder.btnWeatherByID.setOnClickListener(this);
        this.mViewHolder.btnWeatherByName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        WeatherDataService wDs = new WeatherDataService(MainActivity.this);
        if(v.getId() == R.id.btn_getCityId){
            wDs.getCityId(this.mViewHolder.etDataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this,"Something wrong!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(String cityID) {
                    Toast.makeText(MainActivity.this,"Return id: "+cityID, Toast.LENGTH_SHORT).show();
                }
            });
        }else if(v.getId() == R.id.btn_getWeatherByCityId){
            wDs.getCityForescastById(this.mViewHolder.etDataInput.getText().toString(), new WeatherDataService.ForecastByIdResponse() {
                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this,"Something wrong!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(List<WeatherReportModel> weatherReportModels) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                    mViewHolder.lvWeatherReports.setAdapter(arrayAdapter);
                }
            });
        }else if(v.getId() == R.id.btn_getWeatherByCityName){
            Toast.makeText(this,"You typed " + this.mViewHolder.etDataInput.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private class ViewHolder{
        Button btnGetCity;
        Button btnWeatherByID;
        Button btnWeatherByName;
        EditText etDataInput;
        ListView lvWeatherReports;
    }
}