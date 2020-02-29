package com.xp1024.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xp1024.domain.ChapDetailDO;

import java.util.Date;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.service
 * @date 2020/2/28 15:30
 */
public interface IChapDetailService extends IService<ChapDetailDO> {

    Date lastTime();
}
