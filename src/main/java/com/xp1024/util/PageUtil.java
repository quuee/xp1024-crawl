package com.xp1024.util;

import com.xp1024.domain.ChapDetailDO;
import com.xp1024.tools.DateUtil;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.util
 * @date 2020/2/28 16:37
 */
public class PageUtil {

    /**
     * 获取下一页的链接
     * 逻辑:
     * 获取当前页页面,如果当前也是最后一页,不返回.
     * 如果当前也不是最后一页,获取n+1页
     */
    public static int nextPageLink(Html html) {
        String pageNum = html.xpath("//div[@class='pages cc']/b/text()").get();
        String lastLink = html.xpath("//div[@class='pages cc']/a[6]").links().get();
        String lastNum = lastLink.substring(lastLink.lastIndexOf("=")+1);
        int pageNumInt = Integer.parseInt(pageNum);
        int lastNumInt = Integer.parseInt(lastNum);
        if (pageNumInt < lastNumInt) {
            return pageNumInt + 1 ;
        }
        return 0;
    }

    /**
     * 获取chap列表数据
     * @param html
     * @return
     */
    public static List<ChapDetailDO> chapList(Html html){
        Selectable tbody = html.xpath("//table[@id='ajaxtable']/tbody[2]");
        Selectable trs = tbody.xpath("//tr[@align='center' and @class='tr3 t_one']");
        List<ChapDetailDO> chapDetailDOS = new ArrayList<>(trs.nodes().size());

        List<Selectable> childTrs = trs.nodes();
        for (Selectable childTr : childTrs) {
            Selectable td = childTr.xpath("//td[@id]");
            String title = td.xpath("//h3/a/text()").get();
            Selectable links = td.xpath("//h3/a").links();
            String publishDate = childTr.xpath("//td[5]/a/text()").get();

            ChapDetailDO chapDetailDO = new ChapDetailDO();
            chapDetailDO.setTitle(title);
            chapDetailDO.setDetailUrl(links.get());
            try {
                chapDetailDO.setPublishDate(DateUtil.toDate(publishDate,DateUtil.DATE_PATTERN_MIN));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chapDetailDO.setCreateDate(new Date());
            chapDetailDOS.add(chapDetailDO);

        }
        return chapDetailDOS;
    }

    /**
     * 获取chap内容信息
     * @return
     */
//    public static ChapDetailDO chapDetail(){
//        return null;
//    }
}
