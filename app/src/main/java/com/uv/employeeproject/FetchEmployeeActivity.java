package com.uv.employeeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class FetchEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView actv;

    SQLiteHelper dbHelper;
    private static final int REQUEST_PICK_FILE = 1;
    //private TextView filePath;
    ImageView image;
    private Button search;
    private File selectedFile;

    private TextView employeeName, employeeAge;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_employee);
        dbHelper = new SQLiteHelper(this);
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteEmployeeName);
        String[] products = dbHelper.getAllEmployeeNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,products);
        actv.setAdapter(adapter);

        image = (ImageView)findViewById(R.id.photo);
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(this);
        employeeName = (TextView)findViewById(R.id.employeeName);
        employeeAge = (TextView)findViewById(R.id.employeeAge);

        /*dbHelper = new SQLiteHelper(this);


        dbHelper.insertProduct("HP Printer", "The printer");
        dbHelper.insertProduct("Logitech keyboard", "The keyboard");
        dbHelper.insertProduct("ViewSonic Monitor", "The monitor");
        dbHelper.insertProduct("Another Monitor", "The monitor");*/

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch(v.getId()) {
            case R.id.search:
                String searchName = actv.getText().toString();
                Employee employee = dbHelper.getAnEmployee(searchName);
                if(employee != null) {
                    employeeName.setText(employee.getName());
                    employeeAge.setText(String.valueOf(employee.getAge()));
                }
                Toast.makeText(getApplicationContext(), "Employee Record", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            switch(requestCode) {

                case REQUEST_PICK_FILE:

                    if(data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));
                        Bitmap bm = BitmapFactory.decodeFile(selectedFile.getPath());
                        image.setImageBitmap(bm);
                    }
                    break;
            }
        }
    }
}
