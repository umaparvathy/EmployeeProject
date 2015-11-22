package com.uv.employeeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteHelper dbHelper;
    private static final int REQUEST_PICK_FILE = 1;
    //private TextView filePath;
    ImageView image;
    private Button browse;
    private File selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_employee);

        image = (ImageView)findViewById(R.id.photo);
        browse = (Button)findViewById(R.id.browse);
        browse.setOnClickListener(this);

        /*dbHelper = new SQLiteHelper(this);


        dbHelper.insertProduct("HP Printer", "The printer");
        dbHelper.insertProduct("Logitech keyboard", "The keyboard");
        dbHelper.insertProduct("ViewSonic Monitor", "The monitor");
        dbHelper.insertProduct("Another Monitor", "The monitor");*/

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.browse:
                Intent intent = new Intent(this, FilePicker.class);
                startActivityForResult(intent, REQUEST_PICK_FILE);
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
