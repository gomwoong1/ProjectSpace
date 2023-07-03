package com.example.mobile_project;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class custom_dialog {

    //커스텀 다이얼로그의 뷰들을 필드 멤버로 선언
    private Context context;
    private EditText carNumber;
    private EditText homeNumber;
    private EditText carKind;
    private EditText phoneNumber;
    private CarManagementActivity carMg;
    private ListViewAdapter adapter;

    public custom_dialog(Context context) {
        this.context = context;
    }

    public void callDialog(CarManagementActivity carMg) {
        //다이얼로그 객체를 생성
        this.carMg = carMg;
        final Dialog dig = new Dialog(context);

        //다이얼로그의 타이틀바를 숨김
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //다이얼로그의 레이아웃을 설정함
        dig.setContentView(R.layout.custom_dialog);

        //다이얼로그 출력
        dig.show();

        //다이얼로그의 각 위젯들을 정의함
        carNumber = (EditText) dig.findViewById(R.id.input_carNumber);
        homeNumber = (EditText) dig.findViewById(R.id.input_homeNumber);
        carKind = (EditText) dig.findViewById(R.id.input_carKind);
        phoneNumber = (EditText) dig.findViewById(R.id.input_phNumber);
        Button submit = (Button) dig.findViewById(R.id.submit);
        Button cancel = (Button) dig.findViewById(R.id.cancel);

        //확인 버튼을 눌렀을 때 이벤트 발생을 정의
        //carManagerActivity의 adapter 객체에 있는 addItem 메서드를 호출하여 데이터를 리스트뷰에 저장
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carMg.adapter.addItem(carNumber.getText().toString(), homeNumber.getText().toString(), carKind.getText().toString(), phoneNumber.getText().toString());
                dig.dismiss(); //다이얼로그 닫기
            }
        });
        
        //취소 버튼을 눌렀을 때 이벤트 발생을 정의
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소합니다.", Toast.LENGTH_SHORT).show();
                dig.dismiss(); //다이얼로그 닫기
            }
        });
    }
}
