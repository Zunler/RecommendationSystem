package com.horizon.common.quartz.task.impl;

import com.horizon.common.quartz.task.BatchMgr;
import com.horizon.common.util.ReqEngine;
import com.jcraft.jsch.JSchException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 已完成Sync同步功能，完成Quartz功能中
 */
public class MysqlToHDFSImpl implements BatchMgr {
    private static Logger log = Logger.getLogger(MysqlToHDFSImpl.class);
    //定义生成log 的根目录
    private String logDir;
    //执行的类
    private String exeClass;
    //执行的jar 包
    private String exeJar;
    //执行任务的相关参数
    private String params;
    //source Path
    private String srcPath;
    //dest Path
    private String dstPath;
    //livy batches端口
    private String livyUrl = "http://172.16.29.107:8998/batches";

    /**
     * 通过Livy实现保存数据到HDFS功能
     * @param context
     */
    @Override
    public void invoke(JobExecutionContext context) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Log.info("~~ 启动保存数据库到HDFS功能 "+df.format(new Date()));
        String postData = "{\"file\":\"hdfs://172.16.29.107:9000/als.jar\",\"className\":\"neu.AlsEtlFromMysql\",\"name\":\"AlsEtlFromMysql\"}";
        String reqResult = ReqEngine.sendPostReq(livyUrl, postData);
        System.out.println(reqResult);
    }

    public void setLogDir(String logDir) {
    }

    public void setExeClass(String exeClass) {
    }

    public void setExeJar(String exeJar) {
    }

    public void setParams(String params) {
    }

    public void setSrcPath(String srcPath) {
    }

    public void setDstPath(String dstPath) {
    }

    /**
     * 测试代码
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Log.info("~~ 启动保存数据库到HDFS功能");
        String livyUrl = "http://172.16.29.107:8998/batches";
        String postData = "{\"file\":\"hdfs://172.16.29.107:9000/als.jar\",\"className\":\"neu.AlsEtl\",\"name\":\"AlsEtl\"}";
        String reqResult = ReqEngine.sendPostReq(livyUrl, postData);
        System.out.println(reqResult);

    }
}
