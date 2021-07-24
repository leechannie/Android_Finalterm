package com.example.leech_60191680_final;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;  //  datePicker - 날짜를 선택하는 달력
    TextView viewDay;  //  viewDay - 선택한 날짜를 보여주는 textView
    TextView viewTodo;  //  viewDay - 선택한 날짜를 보여주는 textView
    String ID;

    String fileName;   //  fileName - 돌고 도는 선택된 날짜의 파일 이름

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Calendar 이찬희");

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        viewDay = (TextView) findViewById(R.id.viewDay);
        viewTodo = (TextView) findViewById(R.id.viewTodo);
        Button button = (Button) findViewById(R.id.button2);
        Button button2 = (Button) findViewById(R.id.button3);

        // intent로 이메일 정보 얻어오기
        Intent mainintent = getIntent();
        ID = mainintent.getStringExtra("이메일");

        // 오늘 날짜를 받게해주는 Calender
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        // 오늘을 누르면 오늘 날짜로 이동
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDay.setText(cYear + "년 " + (cMonth+1) + "월 " + cDay + "일");
                checkedDay(cYear, cMonth, cDay);
                datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일 ";
                        viewDay.setText(date);
                        checkedDay(year, monthOfYear, dayOfMonth);
                    }
                });
            }
        });

        // 설정을 누르면 Listview로 이동
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Listview.class);
                intent3.putExtra("이메일", ID);
                startActivity(intent3);
            }
        });

    }
    // 메뉴 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                // 날짜를 누르면 해당 날짜에 일정을 등록하러 가는 메뉴
                datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Intent intent = new Intent(getApplicationContext(), Todo.class);
                        intent.putExtra("날짜", year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        intent.putExtra("이메일", ID);
                        startActivity(intent);
                    }
                });
                return true;

            case R.id.menu2:
                // 날짜를 누르면 해당 날짜에 일정을 보여주는 메뉴
                datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        viewDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일 ");
                        checkedDay(year, monthOfYear, dayOfMonth);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 일기 파일 읽기
    String checkedDay(int year, int monthOfYear, int dayOfMonth) {
        // 파일 이름을 만들어준다.
        String name = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ".txt";

        // 일정 있으면 가져오기
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(name);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();

            // 읽어서 토스트 메시지로 보여줌
            Toast.makeText(getApplicationContext(), "일정 존재", Toast.LENGTH_SHORT).show();
            viewTodo.setText(diaryStr);
        } catch (Exception e) {
            // 없음을 알림
            Toast.makeText(getApplicationContext(), "일정 미존재", Toast.LENGTH_SHORT).show();
            viewTodo.setText("");
        }
        return diaryStr;
    }

}