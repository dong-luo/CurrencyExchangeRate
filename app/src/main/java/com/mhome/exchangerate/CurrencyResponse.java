package com.mhome.exchangerate;

import java.util.List;

/**
 * Title :
 * Description :
 * Author : Dong Luo    Data : 2017/1/16 17:27
 * Updater :                  Data : 2017/1/16 17:27
 * Version : 1.0.0
 * Copyright : Copyright(c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */

public class CurrencyResponse {
    public String reason;
    public Result result;
    public class Result{
        public List<Currency> list;
        public class Currency{
            public String name;
            public String code;
        }
    }
    public int error_code;
}
