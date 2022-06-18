package com.example.mobile_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    //클래스 배열을 필드 멥버로 선언
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    //BaseAdapter 추상 클래스를 상속받음으로써 메소드 재정의 필수
    //단, 사용하는 메소드만 재정의하고 필요없는 메소드는 선언만 실시
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //리스트를 화면에 뿌려주는 메소드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //커스텀 리스트뷰의 xml을 리스트뷰를 화면에 출력
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView carNumber = (TextView) convertView.findViewById(R.id.list_carNumber);
        TextView homeNumber = (TextView) convertView.findViewById(R.id.list_homeNumber);
        TextView carKind = (TextView) convertView.findViewById(R.id.list_carKind);
        TextView phoneNumber = (TextView) convertView.findViewById(R.id.list_phone);

        // Data Set (listViewItemList) 에서 position에 위치한 데이터참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        carNumber.setText(listViewItem.getCarNumber());
        homeNumber.setText(listViewItem.getHomeNumber());
        carKind.setText(listViewItem.getCarKind());
        phoneNumber.setText(listViewItem.getPhoneNumber());

        //생성된 뷰 리턴하여 뿌려줌
        return convertView;
    }

    // item 데이터 추가
    public void addItem(String carNumber, String homeNumber, String carKind, String phoneNumber) {
        ListViewItem item = new ListViewItem();
        item.setCarKind(carKind);
        item.setHomeNumber(homeNumber);
        item.setCarNumber(carNumber);
        item.setPhoneNumber(phoneNumber);
        listViewItemList.add(item);
    }

    // 매개변수로부터 넘겨받은 변수를 토대로 item 삭제
    public void delItem(int position) {
        listViewItemList.remove(position);
    }

    //리스트 모델 구현
    public class ListViewItem {
        //커스텀 리스트뷰에 출력할 자료들 선언 및 Getter, Setter 선언
        private String carNumber;
        private String homeNumber;
        private String carKind;
        private String phoneNumber;

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getHomeNumber() {
            return homeNumber;
        }

        public void setHomeNumber(String homeNumber) {
            this.homeNumber = homeNumber;
        }

        public String getCarKind() {
            return carKind;
        }

        public void setCarKind(String carKind) {
            this.carKind = carKind;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}