package com.mhome.exchangerate;

import java.util.List;

/**
 * Title :
 * Description :
 * Author : Dong Luo    Data : 2017/1/16 14:32
 * Updater :                  Data : 2017/1/16 14:32
 * Version : 1.0.0
 * Copyright : Copyright(c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */

public class RatesResponse {
    public String reason;
    public Result result;
    public class Result{
        public String update;
        public List<List<String>> list;
    }


}
