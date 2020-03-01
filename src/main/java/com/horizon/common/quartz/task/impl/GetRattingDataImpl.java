/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: GetRattingDataImpl
 * Author:   pengzijun
 * Date:     2020/2/27 8:52 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.horizon.common.quartz.task.impl;

import com.horizon.common.util.HDFSUtils;
import com.horizon.music.artist.service.IArtistService;
import com.horizon.music.artist.vo.Music;
import com.horizon.music.artist.vo.RattingData;
import org.apache.hadoop.conf.Configuration;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author pengzijun
 * @create 2020/2/27
 * @since 1.0.0
 */
public class GetRattingDataImpl {
    @Autowired
    private IArtistService artistService;
    private String  hdfspath="hdfs://172.16.29.107:9000/zun/music/out/usrArtist/";
    private String rattingDataPath="alsData/usrArtist.txt";
    public  void invoke(JobExecutionContext context) throws IOException {
        int rowCount=0;
        Configuration conf = new Configuration();

        HDFSUtils hdfs=new HDFSUtils(conf);
        SqlRowSet srs = artistService.getRattingData();
        String str="";
        while (srs.next()) {

            str+=srs.getString("USER_ID") + " " + srs.getString("ARTIST_ID")+ " " + srs.getString("COUNT");
            str+="\n";
        }
        System.out.println(str);
        //路径写到src即可

        String path ="src/main/resources/alsData/usrArtist.txt";
        //写入文件
        try {
            File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt
            //文件追加，ture
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(str); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传到hdfs
        hdfs.copyFromLocal(path,"/user/root/zun/music/out/usrArtist/");
        System.out.println("写入成功");
    }

}