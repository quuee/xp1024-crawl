package com.xp1024.pipeline;

import com.xp1024.dao.ChapDetailDao;
import com.xp1024.domain.ChapDetailDO;
import com.xp1024.service.ChapDetailServiceImpl;
import com.xp1024.service.IChapDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.List;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.pipeline
 * @date 2020/2/28 14:08
 */
@Component
public class XP1024PagePipeline implements Pipeline {

    @Autowired
    private IChapDetailService iChapDetailService;

    public void process(ResultItems resultItems, Task task) {
        List<ChapDetailDO> chapDetailDOS = resultItems.get("chapDetailDOS");
        System.out.println("执行插入");
        iChapDetailService.saveBatch(chapDetailDOS);
    }
}
