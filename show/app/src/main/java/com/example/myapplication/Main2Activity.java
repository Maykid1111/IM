package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    private ExpandableListView mExpandableListView;
    private String userid;
    private String friend;
    private String pass;
    private int friendnum;
    private LinearLayout linearLayout;
    private String idToAdd;
    public LinearLayout linearLayout_move;
    httpPostRest HttpPostRest;

    final String frid[]=new String[10];
    private String data[]=new String[6];

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mExpandableListView = findViewById(R.id.expandablelistview);

        //获取好友列表,处理字符串转换成数组

        Bundle bundle=getIntent().getExtras();
        userid=bundle.getString("id");
        pass=bundle.getString("pass");
        final Handler handler1;

        linearLayout_move=findViewById(R.id.layout_1);
        linearLayout_move.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("xw", "OnTouchListener1");
                return false;
            }

        });


        handler1=new Handler(){
            @Override
            public void handleMessage(Message message){
                        int index;
                        friendnum=0;
                        for(int i=0;i<friend.length();i++){
                            index=friend.indexOf("&",i);
                            if(index==-1){
                                frid[friendnum]=friend.substring(i,friend.length());
                            }
                            else {
                                frid[friendnum]=friend.substring(i,index);
                            }
                            friendnum++;
                            if(index==-1){
                                break;
                            }
                            i=index;
                        }
                        if (friend==null){
                            friendnum=0;
                        }

                        List<String> groupList = new ArrayList<>();
                        groupList.add("分组一");
                        final List<List<String>> childList = new ArrayList<>();
                        List<String> childList1 = new ArrayList<>();


                        for (int i=0;i<friendnum;i++){
                            childList1.add(frid[i]);
                        }
                        childList.add(childList1);
                        DemoAdapter demoAdapter = new DemoAdapter(groupList, childList);
                        mExpandableListView.setAdapter(demoAdapter);
                        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {//一级点击监听
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                //如果你处理了并且消费了点击返回true,这是一个基本的防止onTouch事件向下或者向上传递的返回机制
                                return false;
                            }
                        });
                        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {//二级点击监听
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                Intent intent =new Intent(Main2Activity.this,Main4Activity.class);
                                intent.putExtra("id",userid);
                                intent.putExtra("pass",pass);
                                intent.putExtra("friendid",frid[childPosition]);
                                startActivity(intent);
                                finish();
                                return true;
                            }
                        });

            }
        };
        handler1.sendEmptyMessageDelayed(1,3000);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message message){
                super.handleMessage(message);

                data=(String[]) message.obj;
                userid=data[0];
                friend=data[5];
                Message ms1 = new Message();
                ms1.what = 2;
                        //ms1.obj=data_;
                handler1.sendMessage(ms1);



            }
        };
        HttpPostRest.postData("http://192.168.1.45:8888/login","name=123456&pass=12345678",handler);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
            y1 = event.getY();
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float moveDistanceX = event.getX() - x1;
            if(moveDistanceX < 0){// 如果是向右滑动
                linearLayout_move.setX(moveDistanceX);
            }
            else if(moveDistanceX > 0){
                linearLayout_move.setX(moveDistanceX);
            }
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            y2 = event.getY();
            float moveDistanceX = x1-event.getX() ;
            if(moveDistanceX > 200){
                Intent intent =new Intent(
                        Main2Activity.this,Main3Activity.class);
                intent.putExtra("id",userid);
                intent.putExtra("pass",pass);
                startActivity(intent);
                finish();
            }else{
                linearLayout_move.setX(0);
            }
        }
        return super.onTouchEvent(event);
    }
    public class DemoAdapter extends BaseExpandableListAdapter {
        List<String> mGroupList;//一级List
        List<List<String>> mChildList;//二级List 注意!这里是List里面套了一个List<String>,实际项目你可以写一个pojo类来管理2层数据


        public DemoAdapter(List<String> groupList, List<List<String>> childList){
            mGroupList = groupList;
            mChildList = childList;
        }
        @Override
        public int getGroupCount() {//返回第一级List长度
            return mGroupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {//返回指定groupPosition的第二级List长度
            return mChildList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {//返回一级List里的内容
            return mGroupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {//返回二级List的内容
            return mChildList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {//返回一级View的id 保证id唯一
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {//返回二级View的id 保证id唯一
            return groupPosition + childPosition;
        }

        /**
         * 指示在对基础数据进行更改时子ID和组ID是否稳定
         * @return
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         *  返回一级父View
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent,false);
            ((TextView)convertView).setText((String)getGroup(groupPosition));
            return convertView;
        }

        /**
         *  返回二级子View
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent,false);
            ((TextView)convertView).setText((String)getChild(groupPosition,childPosition));
            return convertView;
        }
        /**
         *  指定位置的子项是否可选
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
    /**
     *创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }

    /*
     *菜单的点击事件
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.add_friend, null);
        final EditText editText=linearLayout.findViewById(R.id.userEdit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                idToAdd=editText.getText().toString();
            }
        });
        switch (item.getItemId()){
            case R.id.id_add_item:
                Dialog alertDialog = new AlertDialog.Builder(this).
                        setTitle("添加好友").
                        setView(linearLayout).
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Handler handler11=new Handler(){
                                    public void handleMessage(Message message){
                                        int s=(int) message.obj;
                                        if(s==200) {
                                            Toast.makeText(Main2Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                            Intent intent =new Intent(
                                                    Main2Activity.this,Main2Activity.class);
                                            intent.putExtra("id",userid);
                                            intent.putExtra("pass",pass);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(Main2Activity.this,"请查看id输入是否有误",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                HttpPostRest.postData("http://192.168.1.45:8888/add","id="+userid+"&friend="+idToAdd,handler11);
                                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                                dialog.dismiss();

                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                                dialog.dismiss();
                            }
                        }).
                        create();
                alertDialog.show();
                break;
            default:
                break;
        }

        return true;
    }



}



