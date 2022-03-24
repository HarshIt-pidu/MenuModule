package com.example.menumodule.models;

import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiPlaceHolder {

    @POST("menu-getlist")
    Call<ResponseBody> getMenuList(@Body JsonObject jsonObject);

    @POST("menu-add")
    Call<ResponseBody> addMenuList(@Body JsonObject jsonObject);
//
//    @POST("salary-getattendance")
//    Call<ResponseBody> getAttendance(@Body JsonObject jsonObject);
//
//    @POST("salary-update")
//    Call<ResponseBody> updateUser(
//            @Body JsonObject jsonObject);
//
//    @POST("salary-delete")
//    Call<ResponseBody> deleteUser(@Body JsonObject jsonObject);
//
//    @POST("salary-getstaff")
//    Call<ResponseBody> getStaff(@Body JsonObject jsonObject);

}
