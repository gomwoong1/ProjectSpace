package com.example.mobile_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); // 타이틀바 관리 객체 생성
        actionBar.hide(); //타이틀바 숨기기

        //xml의 주차장 각 버튼과 매핑
        ImageButton SmallPark = (ImageButton)findViewById(R.id.smcar_park);
        ImageButton park1 = (ImageButton)findViewById(R.id.car1_park);
        ImageButton park2 = (ImageButton)findViewById(R.id.car2_park);
        ImageButton park3 = (ImageButton)findViewById(R.id.car3_park);
        ImageButton park4 = (ImageButton)findViewById(R.id.car4_park);
        ImageButton park5 = (ImageButton)findViewById(R.id.car5_park);
        ImageButton park6 = (ImageButton)findViewById(R.id.car6_park);
        ImageButton park7 = (ImageButton)findViewById(R.id.car7_park);

        //각 버튼마다 알맞는 이미지 출력을 위해 이벤트 메소드를 호출하면 매개변수에 해당 뷰와 제어변수를 넘김
        SmallPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 1);
            }
        });

        park1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 3);
            }
        });

        park2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 3);
            }
        });

        park3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 2);
            }
        });

        park4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 2);
            }
        });

        park5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 2);
            }
        });

        park6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 2);
            }
        });

        park7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event(view, 2);
            }
        });

    }

    public void event(View view, int select) {
        
        //다이얼로그 출력하여 상황에 맞는 프로세스가 실행되는 것을 유도
        AlertDialog.Builder dia = new AlertDialog.Builder(MainActivity.this);
        dia.setTitle("알림");
        dia.setMessage("상태를 선택해주세요.");
        
        //주차 버튼을 눌렀을 경우, 제어변수에 따라 이미지버튼에 알맞는 이미지를
        //setImageResource 메소드를 활용하여 이미지 변경
        dia.setNegativeButton("주차", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(select == 1) {
                    ImageButton change = (ImageButton) view;
                    change.setImageResource(R.drawable.use_smallcar_btn);
                }
                else if(select == 2) {
                    ImageButton change = (ImageButton) view;
                    change.setImageResource(R.drawable.use_car_btn);
                }
                else if(select == 3) {
                    ImageButton change = (ImageButton) view;
                    change.setImageResource(R.drawable.use_car1_btn);
                }
                ImageButton state = (ImageButton) view;
                state.getDrawable();
            }
        });

        //취소 버튼을 눌렀다면 Toast를 이용해 짧은 메시지 출력
        dia.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "취소합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //주차 종료 버튼을 누르면 제어변수를 활용해 원래의 이미지로 변경
        dia.setNeutralButton("주차종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(select == 1) {
                    ImageButton change = (ImageButton) view;
                    change.setImageResource(R.drawable.park_smallcar_btn);
                }
                else if(select == 2) {
                    ImageButton change = (ImageButton) view;
                    change.setImageResource(R.drawable.park_car_btn);
                }
                else if(select == 3) {
                    ImageButton change = (ImageButton) view;
                    change.setImageResource(R.drawable.park_car1_btn);
                }
            }
        });
        
        //다이얼로그 생성 후 출력하기
        AlertDialog alertDia = dia.create();
        alertDia.show();
    }
}