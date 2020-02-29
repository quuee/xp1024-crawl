package com.xp1024.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xp1024.dao.ChapDetailDao;
import com.xp1024.domain.ChapDetailDO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.service
 * @date 2020/2/28 15:30
 */
@Service
public class ChapDetailServiceImpl extends ServiceImpl<ChapDetailDao, ChapDetailDO> implements IChapDetailService {

    @Override
    public Date lastTime() {
        return baseMapper.lastTime();
    }
}
