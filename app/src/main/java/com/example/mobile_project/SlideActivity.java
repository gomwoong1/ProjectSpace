package com.example.mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SlideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar(); // 타이틀바 관리 객체 생성
        actionBar.hide(); //타이틀바 숨기기

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slide);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { //인텐트를 사용하는 run 메소드 재정의
                Intent intent = new Intent(getApplicationContext(),ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); //3초 뒤 Intent 메소드 실행하게끔 시간 설정
    }
}