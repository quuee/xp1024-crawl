package com.xp1024.processor;

import com.xp1024.constants.XP1024Const;
import com.xp1024.domain.ChapDetailDO;
import com.xp1024.service.IChapDetailService;
import com.xp1024.tools.DateUtil;
import com.xp1024.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每日爬取最新的
 * TODO 存在的问题: 1 只能抓取隔天,超过2天也只能抓取最新的一天. 2 只能单线程运行
 * 解决 获取最近的日期, 日期每次加一天,抓取相同日期的,抓取完再加一天去匹配. 但是这样如果日期间隔太多,重复且效率低
 *
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.processor
 * @date 2020/2/28 14:59
 */
@Component
public class XP1024DailyPageProcessor implements PageProcessor {
    private final static Logger log = LoggerFactory.getLogger(XP1024DailyPageProcessor.class);
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

    @Autowired
    private IChapDetailService iChapDetailService;

    @Override
    public void process(Page page) {

        Html html = page.getHtml();

        Date lastTime = iChapDetailService.lastTime();

        Selectable tbody = html.xpath("//table[@id='ajaxtable']/tbody[2]");
        Selectable trs = tbody.xpath("//tr[@align='center' and @class='tr3 t_one']");
        List<ChapDetailDO> chapDetailDOS = new ArrayList<>(trs.nodes().size());

        boolean next = false;
        List<Selectable> childTrs = trs.nodes();
        for (int i = 0; i < childTrs.size(); i++) {
            Selectable td = childTrs.get(i).xpath("//td[@id]");
            String title = td.xpath("//h3/a/text()").get();
            Selectable links = td.xpath("//h3/a").links();
            String publishDateString = childTrs.get(i).xpath("//td[5]/a/text()").get();
            Date publishDate = null;
            try {
                publishDate = DateUtil.toDate(publishDateString, DateUtil.DATE_PATTERN_MIN);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int n = lastTime.compareTo(publishDate);
            if (n < 0) {
                ChapDetailDO chapDetailDO = new ChapDetailDO();
                chapDetailDO.setTitle(title);
                chapDetailDO.setDetailUrl(links.get());
                chapDetailDO.setPublishDate(publishDate);

                chapDetailDO.setCreateDate(new Date());
                chapDetailDOS.add(chapDetailDO);
            }

            //如果当前也最后一条记录的时间也是大于
            if (i == childTrs.size() - 1) {
                int m = lastTime.compareTo(publishDate);
                if (m < 0) {
                    next = true;
                }
            }
        }

        if (next) {
            int nextPageNum = PageUtil.nextPageLink(html);
            if (nextPageNum != 0) {
                page.addTargetRequest(XP1024Const.DOMAIN + "/thread.php?fid=111&page=" + nextPageNum);
            }
        }

        page.putField("chapDetailDOS", chapDetailDOS);
    }


}
