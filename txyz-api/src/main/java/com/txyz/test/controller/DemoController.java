package com.txyz.test.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.constant.Cons;
import com.txyz.common.result.R;
import com.txyz.test.model.CrmUser;
import com.txyz.test.service.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "demo")
public class DemoController {
    @Autowired
    private CrmService crmService;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "demoList",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R demoList(){
        String value = redisUtils.get("demo");
        Page<CrmUser> page = new Page<CrmUser>();
        page.setSize(10);
        page.setCurrent(2);
        page =   crmService.selectPage(page,null);
        return  R.ok().put("page",page);
    }
}
