package com.example.linsawako.scau.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by linsawako on 2016/12/10.
 */

public interface ApiService {


    @FormUrlEncoded
    @POST("default2.aspx")
    Call<ResponseBody> login(
            @Header("Cookie") String cookie,
            @Field("__VIEWSTATE") String viewstate,
            @Field("txtUserName") String user,
            @Field("TextBox2") String password,
            @Field("txtSecretCode") String code,
            @Field("RadioButtonList1") String studORTheacher,
            @Field("Button1") String button1,
            @Field("lbLanguage") String lbLanguage
    );

    @GET("CheckCode.aspx")
    Call<ResponseBody> getCodeImage();


    /*
    * User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
cjpost.setHeader("Content-Type","application/x-www-form-urlencoded");
cjpost.setHeader("Accept", "text/html, application/xhtml+xml");
            cjpost.setHeader("Connection", "Keep-Alive")*/


    @FormUrlEncoded
    @POST("xscjcx.aspx")
    Call<ResponseBody> getScore(
            @Header("Cookie") String cookie,
            @Header("Host") String host,
            @Header("Referer") String referer,
            @Query("xh") String credit,
            @Query(value = "xm", encoded = true) String name,
            @Query("gnmkdm") String commandNum,
            @Field("__VIEWSTATE") String viewState,
            @Field("__VIEWSTATEGENERATOR") String generator,
            @Field("ddlXN") String years,
            @Field("ddlXQ") String ddxq,
            @Field(value = "btn_xq", encoded = true) String btn_xq
    );

    @FormUrlEncoded
    @POST("xskbcx.aspx")
    Call<ResponseBody> getCourse(
            @Header("Cookie") String cookie,
            @Header("Host") String host,
            @Header("Referer") String referer,
            @Query("xh") String credit,
            @Query(value = "xm", encoded = true) String name,
            @Query("gnmkdm") String commandNum,
            @Field("__EVENTTARGET") String eventTarget,
            @Field("__EVENTARGUMENT") String EventArgument,
            @Field("__VIEWSTATE") String viewState,
            @Field("__VIEWSTATEGENERATOR") String viewStateGenerator,
            @Field("xnd") String xnd,
            @Field("xqd") String xqd
    );


    @GET("xskbcx.aspx")
    Call<ResponseBody> toSearchScore(
            @Header("Cookie") String cookie,
            @Header("Host") String host,
            @Header("Referer") String referer,
            @Query("xh") String credit,
            @Query(value = "xm", encoded = true) String name,
            @Query("gnmkdm") String commandNum
    );



}
