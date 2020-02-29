package com.xp1024;

import com.xp1024.constants.XP1024Const;
import com.xp1024.pipeline.XP1024PagePipeline;
import com.xp1024.processor.XP1024ChapDetailProcessor;
import com.xp1024.processor.XP1024DailyPageProcessor;
import com.xp1024.processor.XP1024PageProcessor;
import com.xp1024.tools.DateUtil;
import com.xp1024.tools.M3U8Download;
import com.xp1024.tools.M3U8DownloadUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024
 * @date 2020/2/28 14:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {XP1024CrawlerApplication.class})// 指定启动类
public class ProcessorStart {

    @Autowired
    private XP1024PageProcessor xp1024PageProcessor;

    @Autowired
    private XP1024DailyPageProcessor xp1024DailyPageProcessor;

    @Autowired
    private XP1024PagePipeline xp1024PagePipeline;

    @Autowired
    private XP1024ChapDetailProcessor xp1024ChapDetailProcessor;

    /**
     * 测试爬取某个主题下所有的链接
     * 默认爬取了
     */
    @Test
    public void crawlAll() {
        Spider spider = Spider.create(xp1024PageProcessor)
                .addUrl(XP1024Const.DOMAIN + "/thread.php?fid=111");
        spider.addPipeline(xp1024PagePipeline);
        spider.thread(1);
        spider.run();
    }

    @Test
    public void timeCompare() throws ParseException {
        //简单测试时间比较
        Date date = DateUtil.toDate("2020-2-28 16:00:00");
        Date date2 = DateUtil.toDate("2020-2-27 16:00:00");
        System.out.println(date2.compareTo(date));
    }

    /**
     * 每日爬取最新
     */
    @Test
    public void dailyCrawl() {
        Spider spider = Spider.create(xp1024DailyPageProcessor)
                .addUrl(XP1024Const.DOMAIN + "/thread.php?fid=111");
        spider.addPipeline(xp1024PagePipeline);
        spider.thread(1);
        spider.run();
    }

    /**
     * 内容页面爬取
     */
    @Test
    public void chapDetail() {
        Spider spider = Spider.create(xp1024ChapDetailProcessor)
                .addUrl("https://k6.csnjcbnxdnb.com/pw/html_data/111/2002/4635987.html");
//        spider.addPipeline(xp1024PagePipeline);
        spider.thread(1);
        spider.run();
    }

    @Test
    public void testDownload() {
        //单线程测试
        String content = M3U8DownloadUtil.readM3u8IndexFile("d:\\temp\\hp1QKyzm.m3u8");
        List<String> urls = M3U8DownloadUtil.analysisIndex(content);
        for (String url : urls) {
            System.out.println(url);
            String path = M3U8DownloadUtil.commonDownload(url, "d:\\temp\\hp1qkyzm");
            System.out.println(path);
        }
    }

    /**
     * 测试多线程下载
     */
    @Test
    public void testThreadDownload() {
        String content = M3U8DownloadUtil.readM3u8IndexFile("d:\\temp\\hp1QKyzm.m3u8");
        List<String> urls = M3U8DownloadUtil.analysisIndex(content);

        final int threadQuantity = 4;//设置线程数量
        ExecutorService executorService = Executors.newFixedThreadPool(threadQuantity);
        int everyThreadQuantity = (urls.size() + threadQuantity - 1) / threadQuantity;//获得每个线程需要的任务量
        for (int i = 0; i < urls.size(); i += everyThreadQuantity) {
            int startIndex = i;
            int endIndex = i + everyThreadQuantity - 1;
            if(endIndex >= urls.size()) {
                endIndex = urls.size() - 1;
            }
            M3U8Download m3U8Download = new M3U8Download(urls, startIndex, endIndex, "d:\\temp\\hp1qkyzm");
            executorService.execute(m3U8Download);
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            //一直循环等到所有任务被执行完毕时继续往下执行
        }
    }

    /**
     * 测试多线程下载
     */
    @Test
    public void testTreadDownload2(){
        String content = M3U8DownloadUtil.readM3u8IndexFile("d:\\temp\\hp1QKyzm.m3u8");
        //转换成同步的集合
//        List<String> synchronizedList = Collections.synchronizedList(M3U8DownloadUtil.analysisIndex(content));
        List<String> synchronizedList = M3U8DownloadUtil.analysisIndex(content);
        final int threadQuantity = 4;//设置线程数量
        ExecutorService executorService = Executors.newFixedThreadPool(threadQuantity);
        AtomicInteger count= new AtomicInteger();
        for (String url : synchronizedList) {
            executorService.execute(()->{
//                String path = M3U8DownloadUtil.commonDownload(url, "d:\\temp\\hp1qkyzm");
                System.out.println("线程名:"+Thread.currentThread().getName()+",url:"+url);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count.getAndIncrement();
            });
        }
        executorService.shutdown();
//        executorService.awaitTermination(5, TimeUnit.SECONDS);
        while (!executorService.isTerminated()) {

            //一直循环等到所有任务被执行完毕时继续往下执行
        }
        System.out.println(count);

    }

    @Test
    public void testDouble(){
        System.out.println(3/2);
    }

    @Test
    public void mergeTest(){
        System.out.println("合并测试");
        M3U8DownloadUtil.mergeM3u8ToVideo(new File("d:\\temp\\hp1qkyzm"),new File("d:\\temp\\hp1qkyzm\\test.mp4"));
    }

}
