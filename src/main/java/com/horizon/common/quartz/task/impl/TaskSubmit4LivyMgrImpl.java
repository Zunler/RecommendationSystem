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
import org.quartz.JobExecutionContext;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 向yarn 上提交Spark 任务
 */
public class TaskSubmit4LivyMgrImpl implements BatchMgr {

	private static Logger log = Logger.getLogger(TaskSubmit4LivyMgrImpl.class);
	@Override
	//通过Livy 提交spark 任务到yarn 集群
	public void invoke(JobExecutionContext context) {
		log.info("~~ invoke task");
		String url = "http://172.16.29.107:8998/sessions";
		String postData = "{\"kind\": \"spark\"}";
		String reqResult = ReqEngine.sendPostReq(url, postData);
		System.out.println(reqResult);
	}


}
