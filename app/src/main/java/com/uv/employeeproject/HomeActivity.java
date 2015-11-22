package com.uv.employeeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button newEmployee, fetchEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        newEmployee = (Button)findViewById(R.id.newEmployee);
        fetchEmployee = (Button)findViewById(R.id.fetchEmployee);
        newEmployee.setOnClickListener(this);
        fetchEmployee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {

            case R.id.newEmployee:
                intent = new Intent(this, NewEmployeeActivity.class);
                startActivity(intent);
                break;
            case R.id.fetchEmployee:
                intent = new Intent(this, FetchEmployeeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
