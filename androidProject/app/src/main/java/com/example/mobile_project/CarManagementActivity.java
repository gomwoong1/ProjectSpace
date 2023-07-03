package com.example.mobile_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CarManagementActivity extends AppCompatActivity {

    protected ListViewAdapter adapter; //커스텀 어댑터 필드 멤버로 선언

    //커스텀 액션바 출력하는 메서드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 커스텀 액션바의 메뉴에 이벤트 지정
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        
        //차량 추가하기 메뉴가 선택되면 커스텀 다이얼로그를 현재 액티비티에 선언 및 호출
        if( id == R.id.create_car){
            custom_dialog custom_dialog = new custom_dialog(CarManagementActivity.this);
            custom_dialog.callDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_management);

        ActionBar actionBar = getSupportActionBar(); // 타이틀바 관리 객체 생성
        actionBar.setTitle("차량 정보");

        //해당 액티비티의 레이아웃에 정의된 리스트뷰를 등록
        ListView listView = findViewById(R.id.listView);

        //생성한 커스텀 어댑터 클래스의 객체를 생성하고 리스트뷰에 적용
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
        
        //기본 데이터 추가
        adapter.addItem("33저 0792","201호", "더 넥스트 스파크", "010-8429-7735");
        adapter.addItem("12가 3456","202호", "포터 EV", "010-1111-2222");
        adapter.addItem("34나 5678","202호", "더 뉴 말리부", "010-3333-4444");
        adapter.addItem("56다 7890","302호", "그랜져IG", "010-5555-6666");

        //리스트의 아이템을 클릭했을 때의 이벤트 정의
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //삭제 이벤트를 실행하기 전, 다이얼로그를 출력하여 삭제할 것인지 아닌지를 재확인하는 과정
                AlertDialog.Builder dia = new AlertDialog.Builder(CarManagementActivity.this);
                dia.setTitle("확인");
                dia.setMessage("삭제하시겠습니까?");

                //삭제 버튼을 누르면 전달된 포지션 i 값을 입력하여 별도로 만들어둔 delItem 메서드를 호출해 클릭된 리스트 삭제
                //Toast로 삭제 되었음을 알리고 adapter에 현재 상태가 반영되도록 명령문 수행
                dia.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        adapter.delItem(i);
                        Toast.makeText(CarManagementActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged(); //리스트 상태 반영
                    }
                });

                //취소 버튼을 누르면 Toast로 취소 되었음을 알리고 아무런 동작도 실행하지 않음.
                dia.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CarManagementActivity.this, "취소합니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                //다이얼로그 생성하고 화면에 출력
                AlertDialog alertDia = dia.create();
                alertDia.show();
            }
        });
    }
}
