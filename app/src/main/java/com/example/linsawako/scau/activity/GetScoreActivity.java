package com.example.linsawako.scau.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linsawako.scau.R;
import com.example.linsawako.scau.api.Api;
import com.example.linsawako.scau.api.ApiService;
import com.example.linsawako.scau.bean.Course;
import com.example.linsawako.scau.bean.Score;
import com.example.linsawako.scau.myview.TimeTableView;
import com.example.linsawako.scau.util.Utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.linsawako.scau.R.id.info;
import static com.example.linsawako.scau.R.id.result;
import static com.example.linsawako.scau.util.Utility.parseScoreHtml;
import static com.example.linsawako.scau.util.Utility.parseToSearchScore;

public class GetScoreActivity extends AppCompatActivity {

    private static final String TAG = "GetScoreActivity";

    private Button commit;
    private WebView scoreText;
    private TimeTableView timeTableView;
    private ApiService apiService = Api.getApi();
    private String userCredit;
    private String sessionId = "null";
    private String name;
    private String btn = null;
    private String baseUrl = "http://202.116.160.166/";
    private String generator = "9727EB43";
    private String viewState = null;
    private String viewState2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        userCredit = intent.getStringExtra("credit");
        sessionId = intent.getStringExtra("sessionId");

        try {
            name = URLEncoder.encode(name, "gb2312");
            btn = URLEncoder.encode("历年成绩","gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        commit = (Button) findViewById(R.id.score_btu);
        scoreText = (WebView) findViewById(R.id.webView);
       // timeTableView = (TimeTableView) findViewById(R.id.timetable_view);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewState();
            }
        });
    }

    public void getViewState(){
        apiService.toSearchScore(
                sessionId,
                "202.116.160.166",//这个不能加http!!!!!
                //baseUrl + "xs_main.aspx?xh=" + userCredit,
                baseUrl + "xs_main.aspx?xh=" + userCredit,
                userCredit,
                name,
                "N121603"
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //scoreText.setText(response.body().string() + " ");
                    String info = response.body().string();


                    List<String> list = Utility.parseToSearchScore(info);
                    viewState = list.get(0);
                    viewState2 = list.get(1);

                   getCourse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getCourse(){

        String referer = null;
        String text = null;

        //获取课表
        apiService.getCourse(
                sessionId,
                "202.116.160.166",//这个不能加http!!!!!
                baseUrl + "xskbcx.aspx?xh=" + userCredit + "&xm=" + name + "&gnmkdm=N121603",
                userCredit,
                name,
                "N121603",
                "xqd",
                "",
                viewState,
                viewState2,
                "2016-2017",
                "2"
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //scoreText.setText(response.body().string());
                   // List<Course> courseList = Utility.parseTimeTableHtml(response.body().string());
                    //timeTableView.setCourses(courseList);
                    String html = Utility.parseTimeTableHtml1(response.body().string());
                    Log.d(TAG, "onResponse: " + html);
                    scoreText.getSettings().setJavaScriptEnabled(true);
                    scoreText.setWebViewClient(new WebViewClient());
                    scoreText.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

}

    //可以用来获取成绩
    /*public void getScore(){
        String referer = null;
        String text = null;

        apiService.getScore(
                sessionId,
                "202.116.160.166",//这个不能加http!!!!!
                baseUrl + "xs_main.aspx?xh=" + userCredit,
                userCredit,
                name,
                "N121605",
                viewState,
                generator,
                "2015-2016",
                "1",
                btn
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (!response.isSuccess()){
                        Log.d(TAG, "onResponse: unsuccess");

                        scoreText.setText("unsuccess");
                    }else{
                        byte[] b = response.body().bytes();     //获取数据的bytes
                        String info = new String(b, "GB2312");

                        List<Score> scoreList = Utility.parseScoreHtml(info);

                        for (int i = 0; i < scoreList.size(); i++){
                            scoreText.append(scoreList.get(i) + " ");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }*/

}
