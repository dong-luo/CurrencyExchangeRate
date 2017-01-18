package com.mhome.exchangerate;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Title :
 * Description :
 * Author : Dong Luo    Data : 2017/1/18 11:05
 * Updater :                  Data : 2017/1/18 11:05
 * Version : 1.0.0
 * Copyright : Copyright(c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */

public interface ApiInterface {
    @GET("currency")
    Call<ConverterResponse> getCurrency(@Query("from") String from, @Query("to") String to, @Query("key") String appkey);

    @GET("list")
    Call<CurrencyResponse> getList(@Query("key") String key);

    @GET("query")
    Call<RatesResponse> getRates(@Query("key") String key);
}
