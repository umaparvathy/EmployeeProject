package com.uv.employeeproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class ViewAllEmployeesActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;

    ListView allEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_employees);
        dbHelper = new SQLiteHelper(this);
        ArrayList<Employee> employeesList = dbHelper.getAllEmployees();
        Employee[] employees = new Employee[employeesList.size()];
        employees = employeesList.toArray(employees);
        setAdapterForEmployeeList(employees);

    }

    private void setAdapterForEmployeeList(final Employee[] employees) {
        ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(getApplicationContext(), R.layout.employee_item, employees){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.employee_item, parent, false);
                }
                TextView employeeName = null;
                TextView employeeAge = null;

                employeeName = (TextView)convertView.findViewById(R.id.employeeName);
                employeeAge = (TextView)convertView.findViewById(R.id.employeeAge);

                employeeName.setText(employees[position].getName());
                employeeAge.setText(String.valueOf(employees[position].getAge()));
                return convertView;
            }
        };

        allEmployees = (ListView) findViewById(R.id.allEmployees);
        allEmployees.setAdapter(adapter);
    }

}
