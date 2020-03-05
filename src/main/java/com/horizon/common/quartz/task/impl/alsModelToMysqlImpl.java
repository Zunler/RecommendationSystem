package com.horizon.common.quartz.task.impl;

import com.horizon.common.quartz.task.BatchMgr;
import com.horizon.common.util.ReqEngine;
import org.mortbay.log.Log;
import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: cm
 * @description:
 * @author: Sunbuhui7
 * @create: 2020-03-05 10:39
 **/

public class alsModelToMysqlImpl implements BatchMgr {
    //livy batches端口
    private String livyUrl = "http://172.16.29.107:8998/batches";

    @Override
    public void invoke(JobExecutionContext context) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Log.info("~~ 启动模型推荐保存到数据库功能 " + df.format(new Date()));
        String postData = "{\"file\":\"hdfs://172.16.29.107:9000/als.jar\",\"className\":\"neu.AlsPredictor\",\"name\":\"AlsPredictor\"}";
        String reqResult = ReqEngine.sendPostReq(livyUrl, postData);
        System.out.println(reqResult);
    }
}
