package com.example.myapplication;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    public HttpURLConnection conn;
    public String data;
    public String id;
    public String name;
    public String sex;
    public String email;
    public String pass;
    public String friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        class Mhandler extends Handler {
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:

                        break;
                }
            }
        }


        Thread thread= new Thread(new Runnable() {
            public StringBuffer string;
            @Override
            public void run() {
                try {
                    URL mURL = new URL("http://192.168.1.45:8888/login");
                    conn = (HttpURLConnection) mURL.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(1000);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setUseCaches(false);

                    OutputStream out = conn.getOutputStream();
                    //String str = "name="+usernameEditText.getText().toString()+"&pass="+passwordEditText.getText().toString();
                    String str="name=123456&pass=12345678";
                    //String str="id=123456";
                    out.write(str.getBytes("utf-8"));
                    out.flush();
                    out.close();
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        Log.i("aaaa","correct");
                        //Intent intent = new Intent(LoginActivity.this, chatActivity.class);
                        //intent.putExtra("Id", usernameEditText.getText().toString());
                        //startActivity(intent);
                        //setResult(Activity.RESULT_OK);r: java.net.SocketTimeoutException: timeout
                        //finish();
                        InputStream inputStream = conn.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) { //不为空进行操作
                            if(data==null){
                                data=line+"\n";
                            }
                            else{
                                data =data+line+"\n";
                            }
                            Log.i("aaaa","line="+line);
                            Log.i("aaaa","data="+data);
                        }
                        //Log.i("aaaa",id);
                        JSONObject jsonObject=new JSONObject(data);
                        id=jsonObject.getString("id");
                        pass=jsonObject.getString("password");
                        name=jsonObject.getString("name");
                        sex=jsonObject.getString("sex");
                        email=jsonObject.getString("email");
                        friend=jsonObject.getString("friend");
                        Log.i("aaaa","id="+id);
                        Log.i("aaaa","pass="+pass);

                        //mHandler.sendEmptyMessage(0x01); //向主线程返送
                    } else {
                        throw new NetworkErrorException("response status is "+responseCode);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = 1;
                    //uiHandler.sendMessage(message);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        });
        thread.start();

        setContentView(R.layout.activity_main);
        btn1=(Button) findViewById(R.id.btn_test1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent(
                        MainActivity.this,Main2Activity.class);
                intent.putExtra("id",id);
                //intent.putExtra("friend",friend);
                intent.putExtra("id",pass);
                intent.putExtra("sex",sex);
                startActivity(intent);
                finish();
            }
            });
        btn2=(Button) findViewById(R.id.btn_test2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent(MainActivity.this,Main3Activity.class);
                //id="123456";
                //pass="12345678";
                //name="wu";
                //sex="b";
                //email="123@qq.com";
                intent.putExtra("id",id);
                Log.i("input","id2="+id);
                intent.putExtra("pass",pass);
                //intent.putExtra("sex",sex);
                finish();
                startActivity(intent);
                //finish();
            }
        });
        btn3=(Button) findViewById(R.id.btn_test3);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent(MainActivity.this,Main4Activity.class);
                startActivity(intent);

            }
        });

    }
    public void test1(View view){
        Toast.makeText(this,"11",Toast.LENGTH_SHORT).show();
    }
    public void test2(View view){
        Toast.makeText(this,"22",Toast.LENGTH_SHORT).show();
    }

}

