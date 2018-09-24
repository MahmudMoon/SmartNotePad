package com.example.moon.smartnotepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    SqlHepler sqlHepler;
    ListView listView;
    ArrayList<ObjectForNotes> mArrayList;
    Custom_Adapter custom_adapter;
    ArrayList<Integer> needToBeDeleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_views();
        init_variables();
        init_functions();
        init_listeners();
    }

    private void init_views() {
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        listView = (ListView)findViewById(R.id.list);
    }

    private void init_variables() {
        sqlHepler = new SqlHepler(getApplicationContext());
        mArrayList = new ArrayList<>();
        needToBeDeleted = new ArrayList<>();
    }

    private void init_functions() {
         LOADDATA();
    }

    private void LOADDATA() {
        Cursor cursor = sqlHepler.read();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i = 0;i<cursor.getCount();i++){
                int number = cursor.getInt(0);
                String date = cursor.getString(1);
                String title = cursor.getString(2);
                String detail = cursor.getString(3);
                mArrayList.add(new ObjectForNotes(number,date,title,detail));

              //  Toast.makeText(getApplicationContext(),number + " " + date + title + detail,Toast.LENGTH_SHORT ).show();
                cursor.moveToNext();
            }
        }

        custom_adapter = new Custom_Adapter(getApplicationContext(),mArrayList);
        listView.setAdapter(custom_adapter);
        custom_adapter.notifyDataSetChanged();
    }

    private void init_listeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNotes.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CheckBox checkBox = (CheckBox)view.findViewById(R.id.check);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            needToBeDeleted.add(position);
                        }else {
                            Integer a = position;
                            needToBeDeleted.remove(a);
                        }
                    }
                });

//                Intent intent = new Intent(MainActivity.this,AddNotes.class);
//                intent.putExtra("id",mArrayList.get(position).getId());
//                intent.putExtra("date",mArrayList.get(position).getDate());
//                intent.putExtra("title",mArrayList.get(position).getTitle());
//                intent.putExtra("detail",mArrayList.get(position).getDetails());
//                startActivity(intent);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popUpWindowView(mArrayList.get(position).getId(),mArrayList.get(position).getTitle(), mArrayList.get(position).getDetails());
            }
        });

    }


    public void popUpWindowView(final int id, final String title, final String details){

        TextView text_title,text_detail;
        Button update_now,cancle;


        LayoutInflater popUpView = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUp = popUpView.inflate(R.layout.pop_up_window,null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        text_title = (TextView)popUp.findViewById(R.id.text_t);
        text_detail = (TextView)popUp.findViewById(R.id.text_d);
        update_now = (Button) popUp.findViewById(R.id.btn_update_note);
        cancle = (Button)popUp.findViewById(R.id.cancle);


        final PopupWindow popupWindow = new PopupWindow(popUp,width,height,true);
        popupWindow.showAtLocation(popUp, Gravity.CENTER,0,0);


        text_title.setText(title);
        text_detail.setText(details);
        update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNotes.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("detail",details);
                startActivity(intent);
            }
        });


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.update){
            Toast.makeText(getApplicationContext(),"Menu Item Selected",Toast.LENGTH_SHORT).show();
            for(int i=0;i<needToBeDeleted.size();i++){
                ObjectForNotes objectForNotes = mArrayList.get(needToBeDeleted.get(i));

                int deleted =  sqlHepler.delete(objectForNotes.getId());
               Toast.makeText(getApplicationContext(),String.valueOf(deleted),Toast.LENGTH_SHORT).show();
            }
           Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
