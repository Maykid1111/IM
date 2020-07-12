package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    private String id="";
    private String friendId="";
    private String name;
    private String sex;
    private String email;
    private String pass;
    private String data[]=new String[6];
    public LinearLayout linearLayout_move;

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    httpPostRest HttpPostRest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        final TextView tex_id=(TextView) findViewById(R.id.textView_id);
        final TextView tex_id1=(TextView) findViewById(R.id.textView_name);
        final TextView tex_id2=(TextView) findViewById(R.id.textView_sex);
        final TextView tex_id3=(TextView) findViewById(R.id.textView_email);

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        pass=bundle.getString("pass");
        friendId=bundle.getString("friendid");
        linearLayout_move=findViewById(R.id.layout_4);
        final Handler handler=new Handler(){
            public void handleMessage(Message message){
                super.handleMessage(message);
                data=(String[]) message.obj;
                name=data[2];
                sex=data[3];
                email=data[4];
                if(sex.contentEquals("b"))
                    tex_id2.setText("boy");
                if(sex.contentEquals("g"))
                    tex_id2.setText("girl");
                tex_id1.setText(name);
                tex_id3.setText(email);

            }
        };
        final Handler handler1=new Handler(){
            public void handleMessage(Message message){
                super.handleMessage(message);

            }
        };



        HttpPostRest.postData("http://192.168.1.45:8888/friend","id="+friendId,handler);


        Drawable drab1=getResources().getDrawable(R.mipmap.id);
        drab1.setBounds(10,0,70,70);//左上边距，长宽
        tex_id.setCompoundDrawables(drab1,null,null,null);//只放左边
        tex_id.setText(friendId);

        Drawable drab2=getResources().getDrawable(R.mipmap.name);
        drab2.setBounds(10,0,70,70);//左上边距，长宽
        tex_id1.setCompoundDrawables(drab2,null,null,null);//只放左边

        Drawable drab3=getResources().getDrawable(R.mipmap.xingbie1);
        drab3.setBounds(10,0,70,70);//左上边距，长宽
        tex_id2.setCompoundDrawables(drab3,null,null,null);//只放左边

        Drawable drab4=getResources().getDrawable(R.mipmap.email1);
        drab4.setBounds(10,0,70,70);//左上边距，长宽
        tex_id3.setCompoundDrawables(drab4,null,null,null);//只放左边

        Button btn=findViewById(R.id.delete_button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Dialog alertDialog = new AlertDialog.Builder(Main4Activity.this).
                        setTitle("删除好友").
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //添加好友
                                HttpPostRest.postData("http://192.168.1.45:8888/delete","id="+id+"&friend="+friendId,handler1);
                                Intent intent =new Intent(Main4Activity.this,Main2Activity.class);
                                Log.i("input","http://192.168.0.102:8888/delete"+"     id="+id+"&friend="+friendId);
                                intent.putExtra("id",id);
                                intent.putExtra("pass",pass);
                                startActivity(intent);
                                finish();
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).
                                create();
                alertDialog.show();
            }
        });

        Button btn_send=findViewById(R.id.send_button);
        btn_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               //发送消息的按钮
                //
                //
                //
                //
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
            y1 = event.getY();
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float moveDistanceX = event.getX() - x1;
            if(moveDistanceX < 0){
                linearLayout_move.setX(moveDistanceX);
            }
            else if(moveDistanceX > 0){
                linearLayout_move.setX(moveDistanceX);
            }
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            y2 = event.getY();
            Log.e("qqqq", x2+""+"+"+y2+"");
            float moveDistanceX = x1-event.getX() ;
            if(moveDistanceX > 200){
                Intent intent =new Intent(
                        Main4Activity.this,Main2Activity.class);
                intent.putExtra("id",id);
                intent.putExtra("pass",pass);
                startActivity(intent);
                finish();
            }else{
                linearLayout_move.setX(0);
            }
        }
        return super.onTouchEvent(event);
    }
}

