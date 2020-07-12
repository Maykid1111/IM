package com.example.myapplication;

import android.accounts.NetworkErrorException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class httpPostRest {

    public static void postData(final String path,final String string1,final Handler handler){
        final String data_[]=new String[6];
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn;
                    String data="";

                    URL mURL = new URL(path);
                    conn = (HttpURLConnection) mURL.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(1000);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setUseCaches(false);

                    OutputStream out = conn.getOutputStream();
                    String str=string1;
                    out.write(str.getBytes("utf-8"));
                    out.flush();
                    out.close();
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        Log.i("aaaa","correct");
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

                        }
                        Log.i("aaaa","data="+data);
                        //Log.i("aaaa",id);
                        JSONObject jsonObject=new JSONObject(data);
                        String index[]=new String[6];

                        if(path=="http://192.168.1.45:8888/login"){
                            data_[0]=jsonObject.getString("id");
                            data_[1]=jsonObject.getString("password");
                            data_[2]=jsonObject.getString("name");
                            data_[3]=jsonObject.getString("sex");
                            data_[4]=jsonObject.getString("email");
                            data_[5]=jsonObject.getString("friend");
                            Message ms1 = new Message();
                            ms1.what = 1;
                            ms1.obj=data_;
                            handler.sendMessage(ms1);
                        }
                        else if(path=="http://192.168.1.45:8888/friend"){
                            data_[0]=jsonObject.getString("id");
                            data_[2]=jsonObject.getString("name");
                            data_[3]=jsonObject.getString("sex");
                            data_[4]=jsonObject.getString("email");
                            data_[1]=jsonObject.getString("password");
                            data_[5]=jsonObject.getString("friend");
                            Message ms1 = new Message();
                            ms1.what = 2;
                            ms1.obj=data_;
                            handler.sendMessage(ms1);
                        }
                        else if(path=="http://192.168.1.45:8888/delete"){
                            Message ms1 = new Message();
                            ms1.what = 3;
                            handler.sendMessage(ms1);
                        }
                        else if(path=="http://192.168.1.45:8888/update"){
                            Message ms1 = new Message();
                            ms1.what = 4;
                            handler.sendMessage(ms1);
                        }
                        else if(path=="http://192.168.1.45:8888/add"){
                            Message ms1 = new Message();
                            ms1.what = 5;
                            ms1.obj=responseCode;
                            handler.sendMessage(ms1);
                        }
                    } else {
                        Message ms1 = new Message();
                        ms1.what = 5;
                        ms1.obj=responseCode;
                        handler.sendMessage(ms1);
                        throw new NetworkErrorException("response status is "+responseCode);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
