package com.example.leech_60191680_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class service extends AppCompatActivity {
        Button button;

        private ArrayList<HashMap<String,String>> Data = new ArrayList<HashMap<String, String>>();
        private HashMap<String,String> data1 = new HashMap<>();
        private HashMap<String,String> data2 = new HashMap<>();
        private HashMap<String,String> data3 = new HashMap<>();
        private HashMap<String,String> data4 = new HashMap<>();
        private HashMap<String,String> data5 = new HashMap<>();
        private ListView listView2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_service);
            setTitle("Calendar 이찬희");

            listView2 = (ListView)findViewById(R.id.listView2);
            button = (Button) findViewById(R.id.button5);

            // 이메일 정보 받기 (이전으로 가더라도 로그인 정보가 유실되지 않도록)
            Intent listintent = getIntent();
            String ID = listintent.getStringExtra("이메일");

            // 리스트 뷰 데이터 초기화
            final String[] arr = { "서비스 약관", "고객센터", "제작자 정보" };

            // 리스트 뷰 어탭터
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
            listView2.setAdapter(adapter);

            // 리스트 뷰 이벤트 처리
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0:
                           Intent internet = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/terms?hl=ko")); //이용약관 페이지로 이동
                           startActivity(internet);
                           break;
                        case 1:
                            Intent call = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/60-1916-80")); //서비스센터로 전화걸도록 이동
                            startActivity(call);
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(),  "2021 모바일 프로그래밍 기말고사 프로젝트, 융합소프트웨어 학부 60191680 이찬희" , Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            // 이전을 누르면 달력으로 나가기
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent4 = new Intent(getApplicationContext(), Listview.class);
                    intent4.putExtra("이메일", ID);
                    startActivity(intent4);
                }
            });

        }

    }
