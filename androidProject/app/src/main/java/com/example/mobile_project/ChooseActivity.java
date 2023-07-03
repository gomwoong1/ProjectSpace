package com.example.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        ActionBar actionBar = getSupportActionBar(); // 타이틀바 관리 객체 생성
        actionBar.hide(); //타이틀바 숨기기

        //parkBtn 이미지 버튼이 눌렸을 때 실행될 이벤트 정의
        ImageButton parkBtn = (ImageButton) findViewById(R.id.parkBtn);
        parkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //intent 사용하여 MainActivity로 액티비티 전환
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //alramBtn 이미지 버튼이 눌렸을 때 실행될 이벤트 정의
        ImageButton alramBtn = (ImageButton) findViewById(R.id.alramBtn);
        alramBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //intent 사용하여 AlramActivity로 액티비티 전환
                Intent intent = new Intent(getApplicationContext(), AlramActivity.class);
                startActivity(intent);
            }
        });

        //carMgBtn 이미지 버튼이 눌렸을 때 실행될 이벤트 정의
        ImageButton carMgBtn = (ImageButton) findViewById(R.id.carMgBtn);
        carMgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //intent 사용하여 CarManagementActivity로 액티비티 전환
                Intent intent = new Intent(getApplicationContext(), CarManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}