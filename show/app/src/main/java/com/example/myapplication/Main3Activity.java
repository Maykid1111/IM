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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference;

public class Main3Activity extends AppCompatActivity {
    private Button button1;
    public httpPostRest HttpPostRest;
    public String id;
    public String pass;
    public String name;
    public String sex="b";
    public String email;
    public LinearLayout linearLayout_move;

    private String totalString;
    private String totalStringTemp;
    private LinearLayout linearLayout;
    public String name_temp;
    private String data[]=new String[6];



    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;


    static class MyHandler extends Handler {
        WeakReference<Main3Activity>mActivity;
        MyHandler(Main3Activity activity){
            mActivity=new WeakReference<Main3Activity>(activity);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final TextView tex_id=findViewById(R.id.textView_id);
        final TextView tex_id1=findViewById(R.id.textView_name);
        final TextView tex_id2=findViewById(R.id.textView_sex);
        final TextView tex_id3=findViewById(R.id.textView_email);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        pass=bundle.getString("pass");
        name="loading";
        email="loading";

        //滑动
        final MyHandler handler1=new MyHandler(Main3Activity.this){

        };

        linearLayout_move=findViewById(R.id.layout);

        linearLayout_move.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(totalString!=totalStringTemp){
                    String string="id="+tex_id.getText()+"&name="+tex_id1.getText()+"&sex="+sex+"&email="+tex_id3.getText();
                    httpPostRest.postData("http://192.168.1.45:8888/update",string,handler1);
                }
                Log.e("xw", "OnTouchListener");
                return false;
            }

        });


        button1=(Button)findViewById(R.id.out_button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(totalString!=totalStringTemp){
                    if(totalString!=totalStringTemp){
                        String string="id="+tex_id.getText()+"&name="+tex_id1.getText()+"&sex="+sex+"&email="+tex_id3.getText();
                        httpPostRest.postData("http://192.168.1.45:8888/update",string,handler1);
                    }
                    Intent intent =new Intent(Main3Activity.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }
                finish();
            }
        });
        Drawable drab1=getResources().getDrawable(R.mipmap.id);
        drab1.setBounds(10,0,70,70);//左上边距，长宽
        tex_id.setCompoundDrawables(drab1,null,null,null);//只放左边
        tex_id.setText(id);

        Drawable drab2=getResources().getDrawable(R.mipmap.name);
        drab2.setBounds(10,0,70,70);//左上边距，长宽
        tex_id1.setCompoundDrawables(drab2,null,null,null);//只放左边
        tex_id1.setText(name);


        linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.change_message, null);
        final EditText editText=linearLayout.findViewById(R.id.changeEdit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name_temp=editText.getText().toString();
            }
        });
        tex_id1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Dialog alertDialog = new AlertDialog.Builder(Main3Activity.this).
                        setTitle("更改姓名").
                        setView(linearLayout).
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(name_temp!=null){
                                    name=name_temp;
                                    tex_id1.setText(name);
                                    totalStringTemp=name+sex+email;
                                }
                                else{
                                    Toast.makeText(Main3Activity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                                }
                                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                                editText.setText("");
                                name_temp=null;
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
                                show();
                alertDialog.show();

            }
        });


        Drawable drab3=getResources().getDrawable(R.mipmap.xingbie1);
        drab3.setBounds(10,0,70,70);//左上边距，长宽
        tex_id2.setCompoundDrawables(drab3,null,null,null);//只放左边

        tex_id2.setText(sex);
        tex_id2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Dialog alertDialog = new AlertDialog.Builder(Main3Activity.this).
                        setTitle("确定要修改性别吗？").
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(sex.contentEquals("g")){
                                    tex_id2.setText("boy");
                                    sex="b";
                                }
                                else if(sex.contentEquals("b")){
                                    tex_id2.setText("girl");
                                    sex="g";
                                }
                                totalStringTemp=name+sex+email;
                                dialog.dismiss();
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
        Drawable drab4=getResources().getDrawable(R.mipmap.email1);
        drab4.setBounds(10,0,70,70);//左上边距，长宽
        tex_id3.setCompoundDrawables(drab4,null,null,null);//只放左边
        tex_id3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Dialog alertDialog = new AlertDialog.Builder(Main3Activity.this).
                        setTitle("更改邮箱").
                        setView(linearLayout).
                        //setIcon(R.drawable.ic_launcher).
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //修改信息
                                if(name_temp==null){
                                    Toast.makeText(Main3Activity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                                }
                                else if(name_temp.contentEquals("@qq.com")||name_temp.contentEquals("@136.com")||name_temp.contentEquals("@163.com")){
                                    if(name_temp!=null){
                                        email=name_temp;
                                        tex_id3.setText(email);
                                        totalStringTemp=name+sex+email;
                                    }
                                }
                                else{
                                    Toast.makeText(Main3Activity.this,"格式错误",Toast.LENGTH_SHORT).show();
                                }

                                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                                name_temp=null;
                                editText.setText("");
                                dialog.dismiss();
                            }
                        }).
                                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                                    }
                                }).
                                create();
                alertDialog.show();

            }
        });
        tex_id3.setText(email);

        final MyHandler handler=new MyHandler(Main3Activity.this){
            @Override
            public void handleMessage(Message message){
                data=(String[]) message.obj;
                id=data[0];
                name=data[2];
                sex=data[3];
                email=data[4];
                tex_id.setText(id);
                tex_id1.setText(name);
                if(sex.contentEquals("b"))
                    tex_id2.setText("boy");
                if(sex.contentEquals("g"))
                    tex_id2.setText("girl");
                tex_id3.setText(email);
                totalString=name+sex+email;
                totalStringTemp=totalString;

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
                        Main3Activity.this,Main2Activity.class);
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
    public void test1(View view){
        Toast.makeText(this,"11",Toast.LENGTH_SHORT).show();
    }

}


