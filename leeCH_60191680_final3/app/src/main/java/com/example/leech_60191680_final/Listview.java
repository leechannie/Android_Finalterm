package com.example.leech_60191680_final;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Listview extends AppCompatActivity {
    Button button;

    private ArrayList<HashMap<String,String>> Data = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> data1 = new HashMap<>();
    private HashMap<String,String> data2 = new HashMap<>();
    private HashMap<String,String> data3 = new HashMap<>();
    private HashMap<String,String> data4 = new HashMap<>();
    private HashMap<String,String> data5 = new HashMap<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        setTitle("Calendar 이찬희");

        // 이메일 정보 받기
        Intent listintent = getIntent();
        String ID = listintent.getStringExtra("이메일");

        listView = (ListView)findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button4);

        // 리스트 뷰 데이터 초기화
        data1.put("title","로그인 정보");
        data1.put("sub", ID);
        Data.add(data1);

        data2.put("title","버전 정보");
        data2.put("sub","최신 1.0.2 사용 중");
        Data.add(data2);

        data3.put("title","데이터 저장 위치");
        data3.put("sub", " 내부저장소 /data/data/leeCH_60191680_final");
        Data.add(data3);

        data4.put("title","서비스 정보");
        data4.put("sub","1.0.2");
        Data.add(data4);

        data5.put("title","로그아웃");
        data5.put("sub", "");
        Data.add(data5);


        // 리스트 뷰 어탭터
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,Data,android.R.layout.simple_list_item_2,new String[]{"title","sub"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);

        // 리스트 뷰 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),  "1.0.2 버전" , Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),  "내부저장소 사용" , Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Intent intent7 = new Intent(getApplicationContext(), service.class); // 서비스 정보 페이지로 이동
                        intent7.putExtra("이메일", ID); //이전으로 가더라도 로그인 정보가 유실되지 않도록
                        startActivity(intent7);
                        break;
                    case 4:
                        showMessage(); //로그아웃 대화상자 띄우기
                }
            }
        });

        // 이전을 누르면 달력으로 나가기
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                intent4.putExtra("이메일", ID); //이전으로 가더라도 로그인 정보가 유실되지 않도록
                startActivity(intent4);
            }
        });

    }

    // 로그아웃 대화상자 생성
    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그아웃");
        builder.setMessage("로그아웃 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),  "로그아웃 되었습니다." , Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(getApplicationContext(), Login.class); //로그아웃 한다고 하면 초기화면으로 빠져나가기
                startActivity(intent6);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // 로그아웃을 하지 않으면 대화상자만 없애기
            }
        });

        AlertDialog alertDialog = builder.create(); // 대화상자 생성
        alertDialog.show(); //대화상자 보여주기
    }
}