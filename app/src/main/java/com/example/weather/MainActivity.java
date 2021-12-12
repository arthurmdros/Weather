package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        if(v.getId() == R.id.btn_getCityId){
            Toast.makeText(this,"You clicked me 1.", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.btn_getWeatherByCityId){
            Toast.makeText(this,"You clicked me 2.", Toast.LENGTH_SHORT).show();
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