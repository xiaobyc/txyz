package com.txyz.baseData.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.txyz.baseData.dao.AreaDao;
import com.txyz.baseData.model.Area;
import com.txyz.baseData.service.AreaService;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaDao,Area> implements AreaService {
}
