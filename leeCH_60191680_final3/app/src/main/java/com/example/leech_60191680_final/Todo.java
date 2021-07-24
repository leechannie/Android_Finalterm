package com.example.leech_60191680_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Todo extends AppCompatActivity {

    Button button;
    EditText editText;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setTitle("Calendar 이찬희");

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        textView2 = (TextView) findViewById(R.id.textView2);

        // 명시적 intent를 통해 날짜 정보 주고받기
        Intent todointent = getIntent();
        String Name = todointent.getStringExtra("날짜");
        String ID = todointent.getStringExtra("이메일");
        String fileName = todointent.getStringExtra("날짜") + ".txt";

        // 전에 파일이 있으면 읽어오게 한다.
        String str = readDiary(fileName);
        editText.setText(str);

        // 일기의 날짜 보여주기
        textView2.setText(Name);

        // 일정을 날짜의 이름으로 파일 저장
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName,
                            Context.MODE_PRIVATE);
                    String str = editText.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(),
                            fileName + " 이  저장됨", Toast.LENGTH_SHORT).show();

                    // 파일이 저장되면 다시 달력으로
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("이메일", ID);
                    startActivity(intent);
                } catch (IOException e) {
                }
            }
        });
    }

    //파일 읽기
    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            // 있으면 파일 불러오고, 수정으로 버튼 수정
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            button.setText("수정");
        } catch (IOException e) {
            // 오류 처리
            editText.setHint("일정 작성");
            button.setText("저장");
        }
        return diaryStr;
    }
}