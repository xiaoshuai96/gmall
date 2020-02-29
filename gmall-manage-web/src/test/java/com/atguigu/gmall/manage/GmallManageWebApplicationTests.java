package com.atguigu.gmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {
        ClientGlobal.init(this.getClass().getResource("/tracker.conf").getPath());
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        //通过tracker获得一个Storage连接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String[] jpgs = storageClient.upload_file("D:/111.jpg", "jpg", null);
        String url = "http://192.168.183.128";
        for (String s : jpgs) {
            url = url + "/" + s;
            System.out.println(url);
        }

    }

    @Test
    public void demo() {
        String s = "1231.1546.jpg";
        int i = s.lastIndexOf(".");
        String s1 = s.substring(i + 1);
        System.out.println(s1);
    }


}
