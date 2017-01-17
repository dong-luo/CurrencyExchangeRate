package com.mhome.exchangerate;

import java.util.List;

/**
 * Title :
 * Description :
 * Author : Dong Luo    Data : 2017/1/17 11:16
 * Updater :                  Data : 2017/1/17 11:16
 * Version : 1.0.0
 * Copyright : Copyright(c) 浙江蘑菇加电子商务有限公司 2015 ~ 2016 版权所有
 */

public class ConverterResponse {
    public int error_code;
    public String reason;
    public List<Detail> result;
    public class Detail{
        public double exchange;
    }
}
