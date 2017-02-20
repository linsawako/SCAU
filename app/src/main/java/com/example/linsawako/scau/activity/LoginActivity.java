package com.example.linsawako.scau.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.tech.TagTechnology;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linsawako.scau.R;
import com.example.linsawako.scau.api.Api;
import com.example.linsawako.scau.api.ApiService;
import com.example.linsawako.scau.bean.UserInfo;
import com.example.linsawako.scau.util.Utility;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ImageView codeImage;
    private EditText nameText;
    private EditText passwordText;
    private EditText codeText;
    private Button commitBut;
    private TextView resultText;
    private static final String TAG = "LoginActivity";

    private String sessionId = "";

    private ApiService apiService = Api.getApi();

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            sessionId = (String)msg.obj;
            Log.d("LogActivity", sessionId);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initView();
        initCode();

        codeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCode();
            }
        });

        commitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });
    }

    public void postData(){

        final UserInfo userInfo = new UserInfo();
        userInfo.setCode(codeText.getText().toString());
        userInfo.setName(nameText.getText().toString());
        userInfo.setPassword(passwordText.getText().toString());


        apiService.login(
                sessionId,
                "dDwyODE2NTM0OTg7Oz6XQwtkC4IPj2mY5bsI42qRkaJNzw==",
                userInfo.getName(),
                userInfo.getPassword(),
                userInfo.getCode(),
                "学生",
                "",
                ""
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String info = response.body().string();

                    resultText.setText(info);

                    String name = Utility.paseLoginHtml(info);

                    Log.d(TAG, "intent");
                    Intent intent = new Intent(LoginActivity.this, GetScoreActivity.class);
                    intent.putExtra("credit", userInfo.getName());
                    intent.putExtra("name", name);
                    intent.putExtra("sessionId", sessionId);
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void initView() {
        codeImage = (ImageView) findViewById(R.id.code_image);
        nameText = (EditText) findViewById(R.id.user_name);
        passwordText = (EditText) findViewById(R.id.user_password);
        codeText = (EditText) findViewById(R.id.code);
        commitBut = (Button) findViewById(R.id.commit);
        resultText = (TextView) findViewById(R.id.result);
    }

    private void initCode() {

        apiService.getCodeImage().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Headers headers = response.headers();

                String sessionID = headers.get("Set-Cookie");

                Message msg = new Message();
                msg.obj = sessionID;
                handler.handleMessage(msg);

                byte[] bytes = new byte[0];
                try {
                    bytes = response.body().bytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //把byte字节组装成图片
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //网络图片请求成功，更新到主线程的ImageView
                        resultText.setText(sessionId);
                        codeImage.setImageBitmap(bmp);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
