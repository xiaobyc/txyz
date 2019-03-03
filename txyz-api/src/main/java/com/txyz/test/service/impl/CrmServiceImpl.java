package com.txyz.test.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.test.dao.CrmUserDao;
import com.txyz.test.model.CrmUser;
import com.txyz.test.service.CrmService;
import org.springframework.stereotype.Service;

@Service
public class CrmServiceImpl extends ServiceImpl<CrmUserDao,CrmUser> implements CrmService {
}
