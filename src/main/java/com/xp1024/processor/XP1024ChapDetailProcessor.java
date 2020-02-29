package com.xp1024.processor;

import com.xp1024.constants.XP1024Const;
import com.xp1024.tools.M3U8DownloadUtil;
import com.xp1024.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 每个内容抓取
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.processor
 * @date 2020/2/28 16:32
 */
@Component
public class XP1024ChapDetailProcessor implements PageProcessor {
    private final static Logger log = LoggerFactory.getLogger(XP1024ChapDetailProcessor.class);
    // 定义连接失败时，重试机制
    private Site site = Site.me()
            .setDomain(XP1024Const.DOMAIN)
            .setUserAgent(XP1024Const.USERAGENT)
            .setRetryTimes(1)
            .setTimeOut(120000)
            .setSleepTime(5000);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        //后去标签内部属性
        String src = html.xpath("//iframe[@src]/@src").get();

        String param = UrlUtil.getParam(src);

        String url = XP1024Const.M3U8+"/"+param+".m3u8";

        M3U8DownloadUtil.commonDownload(url,"d:\\temp");

    }


}
