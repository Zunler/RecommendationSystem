/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HDFSUtil
 * Author:   pengzijun
 * Date:     2020/3/1 11:17 上午
 * Description: hdfs工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.horizon.common.util;

import java.io.IOException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈hdfs工具类〉
 *
 * @author pengzijun
 * @create 2020/3/1
 * @since 1.0.0
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.yarn.api.records.URL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class HDFSUtils {
    //TODO 上传
    private FileSystem fs = null;


    public HDFSUtils(Configuration conf) {

        try {
            //设置url，并设置用户为root
            fs = FileSystem.get(URI.create("hdfs://172.16.29.107:9000"),conf ,"root");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteDir(String path) throws IOException {
        Path p = new Path(path);
        if (fs.exists(p)) {
            fs.delete(p, true);

        }
    }

    public boolean makeDir(String path) throws IOException {
        Path p = new Path(path);
        if (!fs.exists(p)) {
            return fs.mkdirs(p);
        }
        return false;
    }

    public void copyFromLocal(String localPath, String serverPath) throws IOException {
        Path serverP = new Path(serverPath);
        Path localP = new Path(localPath);
        if (fs.exists(serverP)) {
            fs.copyFromLocalFile(localP, serverP);

        }

    }
    public Path[] listStatus(String path) throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path(path));
        Path[] paths = FileUtil.stat2Paths(fileStatuses);
        return  paths;

    }
    public void list(String path) throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path(path));
        for (FileStatus fileStatus :
                fileStatuses) {
            System.out.println(fileStatus.toString());

        }

    }

    public int count(String Path) throws IOException {
        int totalFileCount = 0;
        Path path = new Path(Path);
        if (fs.exists(path)) {
            FileStatus[] stats = fs.listStatus(path);
            for (int i = 0; i < stats.length; ++i) {
                if (!stats[i].isDir()) {
                    // regular file
                    // System.out.println(stats[i].getPath().toString());
                    totalFileCount++;
                } else {
                    // dir
                    // System.out.println(stats[i].getPath().toString());
                    totalFileCount += count(stats[i].getPath().toString());
                }
            }
        }
        return totalFileCount;
    }

    public void changeOwner(String path) throws IOException {
        Path p=new Path(path);
        fs.setOwner(p,"pengzijun","neu");
    }
    public void createFile(String path) throws IOException {
        Path p = new Path(path);
        FSDataOutputStream fsDataOutputStream = fs.create(p);
        fsDataOutputStream.close();


    }

    public void writeFile(String path, String content) throws IOException {
        FSDataOutputStream create = fs.create(new Path(path));
// 写入
        create.write(content.getBytes());
// 刷新
        create.flush();
// 关闭
        create.close();

    }

    public void showContent(String path) throws IOException {
//		FSDataInputStream inputStream=fs.open(new Path(path));
//		System.out.println("myfile:"+inputStream.readUTF());
//		inputStream.close();
        FSDataInputStream inputStream = fs.open(new Path(path));
        IOUtils.copyBytes(inputStream, System.out, 1024);
        inputStream.close();

    }

    public boolean isExists(String path) throws IOException {
        return fs.exists(new Path(path));
    }

    public void rename(String src, String dest) throws IOException {
        Path p = new Path(src);
        Path destPath = new Path(dest);
        if (fs.exists(p)) {
            this.deleteDir(dest);
            fs.rename(p, destPath);
        }
    }

    public void getAcl(String path) throws IOException {
        Path p = new Path(path);
        AclStatus aclStatus = fs.getAclStatus(p);
        System.out.println(aclStatus.toString());

    }

    public void setAcl(String path) throws IOException {
        Path p = new Path(path);
        List<AclEntry> aclEntries = new ArrayList<AclEntry>();

        for (int i = 0; i < 3; i++) {
            AclEntryScope aclEntryScope = AclEntryScope.ACCESS;
            AclEntry.Builder builder = new AclEntry.Builder();

            if (i == 0) {
                builder.setName("pengzijun");
                builder.setPermission(FsAction.ALL);
                builder.setScope(aclEntryScope);
                builder.setType(AclEntryType.USER);
            } else if (i == 1) {
                builder.setPermission(FsAction.READ);
                builder.setScope(aclEntryScope);
                builder.setType(AclEntryType.OTHER);


            } else {
                builder.setPermission(FsAction.READ);
                builder.setScope(aclEntryScope);
                builder.setType(AclEntryType.GROUP);
            }

            AclEntry aclEntry = builder.build();
            aclEntries.add(aclEntry);
        }
        fs.modifyAclEntries(p, aclEntries);

    }

}
