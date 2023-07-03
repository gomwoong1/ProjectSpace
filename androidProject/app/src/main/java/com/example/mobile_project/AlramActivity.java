package com.example.mobile_project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AlramActivity extends AppCompatActivity {

    //리스트 뷰의 자료를 저장하는 String 배열과 adapter를 필드 멤버로 선언
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private EditText inputTf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram);

        ActionBar actionBar = getSupportActionBar(); // 타이틀바 관리 객체 생성
        actionBar.hide(); //타이틀바 숨기기

        //xml 파일의 뷰들과 매핑
        ListView listView = findViewById(R.id.outTimeList);
        inputTf = findViewById(R.id.inputTf);
        Button inputBtn = findViewById(R.id.input);

        //입력 버튼 클릭시 이벤트 발생 정의
        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String 배열에 입력받은 값 저장하고 어댑터 호출하여 리스트 상태 반영
                list.add(inputTf.getText().toString());
                adapter.notifyDataSetChanged();
                inputTf.setText(""); //텍스트필드 비우기
            }
        });

        list = new ArrayList<String>(); //String형 배열 선언
        list.add("201호 3시 33분 출차할 예정!!"); //기본값 입력
        list.add("12가 3456 1시에 출차합니다");

        //리스트 어레이를 출력할 어댑터와 리스트 표시 모드, 불러올 리스트 정의
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter); //어댑터 적용

        //리스트의 아이템을 클릭했을 때의 이벤트 정의
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //넘겨받은 인덱싱 넘버를 이용해 리스트의 아이템 제거 및 간단한 메시지 출력
                list.remove(i);
                Toast.makeText(AlramActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged(); //리스트 상태 반영
            }
        });
    }
}