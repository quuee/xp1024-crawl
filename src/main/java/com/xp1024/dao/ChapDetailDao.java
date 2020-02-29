package com.xp1024.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xp1024.domain.ChapDetailDO;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.dao
 * @date 2020/2/28 14:35
 */
public interface ChapDetailDao extends BaseMapper<ChapDetailDO> {

    /**
     * 获取最近的发布时间
     * @return date
     */
    @Select("select publish_date from chap_detail order by publish_date desc limit 1")
    public Date lastTime();
}
