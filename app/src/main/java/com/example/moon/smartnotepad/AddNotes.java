package com.example.moon.smartnotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNotes extends AppCompatActivity {


    EditText title_;
    EditText details;
    Button btn_save;
    Intent intent;
    int isUpdate = 0;
    SqlHepler sqlHepler;
    int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        intent = getIntent();
        inti_views();
        init_variables();
        init_functions();
        init_listeners();

    }

    private void inti_views() {
        title_ = (EditText)findViewById(R.id.editText);
        details = (EditText)findViewById(R.id.editText2);
        btn_save = (Button)findViewById(R.id.btn_save_note);
    }

    private void init_variables() {
        sqlHepler = new SqlHepler(getApplicationContext());

    }

    private void init_functions() {
        value = intent.getIntExtra("id",-1);
        if(value!=-1){
            title_.setText(intent.getStringExtra("title"));
            details.setText(intent.getStringExtra("detail"));
            btn_save.setText("Update");
            isUpdate = 1;
        }
    }

    private void init_listeners() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title_.getText().toString().trim().equals("") || details.getText().toString().trim().equals("")){
                    if(title_.getText().toString().trim().equals("")){
                        title_.setError("Can't be Empty");
                    }
                    if( details.getText().toString().trim().equals("")){
                        details.setError("Can't be Empty");
                    }
                }else {

                    String title = title_.getText().toString().trim().toUpperCase();
                    String detail = details.getText().toString().trim();

                    if(btn_save.getText().toString().equals("Update")){

                        int updated = sqlHepler.Update(value, title, detail);
                        Toast.makeText(getApplicationContext(),String.valueOf(updated),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddNotes.this,MainActivity.class);
                        startActivity(intent);

                    }else {


                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int year = calendar.get(Calendar.YEAR);
                        String Current_Date = day + "/" + month + "/" + year;

                        //Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();


                        ObjectForNotes objectForNotes = new ObjectForNotes(Current_Date, title, detail);

                        long inserted = sqlHepler.insert(objectForNotes);
                        if (inserted > 0) {
                            Toast.makeText(getApplicationContext(), "Successs insert new note", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "Failed to insert note ", Toast.LENGTH_SHORT).show();
                    }

                    title_.setText("");
                    details.setText("");
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddNotes.this,MainActivity.class);
        startActivity(intent);
    }
}
