package com.horizon.common.quartz.task.impl;


import com.horizon.common.quartz.task.BatchMgr;
import com.horizon.common.util.ReqEngine;
import com.horizon.common.util.SysContextUtil;
import com.horizon.music.artist.dao.ArtistDAO;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.quartz.JobExecutionContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 保存最优模型到HDFS
 */
public class ModelToHDFSImpl implements BatchMgr {
    //livy batches端口
    private String livyUrl = "http://172.16.29.107:8998/batches";

    private static Logger log = Logger.getLogger(ModelToHDFSImpl.class);

    //通过Livy 提交spark 任务到yarn 集群
    @Override
    public void invoke(JobExecutionContext context) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Log.info("~~ 启动保存最优模型到HDFS功能 "+df.format(new Date()));
        String postData = "{\"file\":\"hdfs://172.16.29.107:9000/als.jar\",\"className\":\"neu.AlsModel\",\"name\":\"AlsModel\"}";
        String reqResult = ReqEngine.sendPostReq(livyUrl, postData);
        System.out.println(reqResult);
    }

}

