package com.uv.employeeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;

public class NewEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteHelper dbHelper;
    private static final int REQUEST_PICK_FILE = 1;
    private TextView employeeName, employeeAge;
    ImageView image;
    private Button browse, submit;
    private File selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_employee);

        employeeName = (TextView)findViewById(R.id.name);
        employeeAge = (TextView)findViewById(R.id.age);
        image = (ImageView)findViewById(R.id.photo);
        browse = (Button)findViewById(R.id.browse);
        submit = (Button) findViewById(R.id.submit);
        browse.setOnClickListener(this);
        submit.setOnClickListener(this);
        dbHelper = new SQLiteHelper(this);


        /*dbHelper.insertProduct("HP Printer", "The printer");
        dbHelper.insertProduct("Logitech keyboard", "The keyboard");
        dbHelper.insertProduct("ViewSonic Monitor", "The monitor");
        dbHelper.insertProduct("Another Monitor", "The monitor");*/

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch(v.getId()) {

            case R.id.browse:
                intent = new Intent(this, FilePicker.class);
                startActivityForResult(intent, REQUEST_PICK_FILE);
                break;
            case R.id.submit:
                saveEmployeeRecord();
                Toast.makeText(getApplicationContext(), "Employee Record Saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void saveEmployeeRecord() {
        String name = employeeName.getText().toString();
        int age = Integer.valueOf(employeeAge.getText().toString());
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        byte[] array = getBiteArray(bitmap);
        Employee employee = new Employee(name, age, array);
        dbHelper.insertEmployeeRecord(employee);
    }

    private byte[] getBiteArray(Bitmap bitmap) {

        byte[] bytes = null;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bytes = bos.toByteArray();

        return bytes;

        /*int bytes = bitmap.getByteCount();

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

        byte[] array = buffer.array(); //Get the underlying array containing the data.
        return array;*/
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
