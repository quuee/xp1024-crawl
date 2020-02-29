package com.xp1024.processor;

import com.xp1024.constants.XP1024Const;
import com.xp1024.domain.ChapDetailDO;
import com.xp1024.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 一次性爬取某个主题下所有的列表
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.processor
 * @date 2020/2/28 11:22
 */
@Component
public class XP1024PageProcessor implements PageProcessor {

    private final static Logger log = LoggerFactory.getLogger(XP1024PageProcessor.class);
    // 定义连接失败时，重试机制
    private Site site = Site.me()
            .setDomain(XP1024Const.DOMAIN)
            .setUserAgent(XP1024Const.USERAGENT)
            .setRetryTimes(1)
            .setTimeOut(120000)
            .setSleepTime(5000);

    public Site getSite() {
        return site;
    }

    public void process(Page page) {

        Html html = page.getHtml();

        //获取chap列表数据
        List<ChapDetailDO> chapDetailDOS = PageUtil.chapList(html);

        //获取当前页面  总页数
        int nextPageNum = PageUtil.nextPageLink(html);
        if (nextPageNum != 0) {
            page.addTargetRequest(XP1024Const.DOMAIN + "/thread.php?fid=111&page=" + nextPageNum);
        }

        page.putField("chapDetailDOS",chapDetailDOS);
    }

}
