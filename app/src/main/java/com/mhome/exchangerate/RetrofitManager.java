package com.mhome.exchangerate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Title :
 * Description :
 * Author : Dong Luo    Data : 2017/1/18 15:04
 * Updater :                  Data : 2017/1/18 15:04
 * Version : 1.0.0
 * Copyright : Copyright(c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */

public class RetrofitManager {
    static final ApiInterface apiService = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface.class);
}
