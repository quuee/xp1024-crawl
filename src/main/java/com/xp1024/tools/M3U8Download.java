package com.xp1024.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 多线程下载类
 * 这个是平均分配任务执行下载
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.tools
 * @date 2020/2/28 20:26
 */
public class M3U8Download implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(M3U8Download.class);
    //设置当前下载数量,所有线程共享
    private static int currentNum = 0;
    private List<String> urlList;
    private int startIndex;
    private int endIndex;
    private String folderPath;

    public M3U8Download(List<String> urlList, int startIndex, int endIndex, String folderPath) {
        this.urlList = urlList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.folderPath = folderPath;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            String subUrlPath = urlList.get(i);
            M3U8DownloadUtil.commonDownload(subUrlPath, folderPath);
//            synchronized (M3U8Download.class){
//                currentNum++;
//            }
            //应该不需要同步锁,因为数量是确定的
            currentNum++;
            log.info("线程:{},正在下载第:{}个,总数:{},总计完成:{}%", Thread.currentThread().getName(),
                    currentNum,
                    urlList.size(),
                    new BigDecimal((currentNum) / urlList.size()).setScale(2, RoundingMode.HALF_UP).intValue() * 100);
        }
    }
}
