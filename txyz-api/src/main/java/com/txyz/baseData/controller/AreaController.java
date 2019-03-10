package com.txyz.baseData.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.txyz.baseData.model.Area;
import com.txyz.baseData.request.AreaRequest;
import com.txyz.baseData.service.AreaService;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.constant.Cons;
import com.txyz.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "area")
@Slf4j
public class AreaController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 省市区查询
     * @param request
     * @return
     */
    @RequestMapping(value = "listArea",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R listArea(@RequestBody AreaRequest request){
        List<Area> listArea =null;
        String search="0";
        if(StringUtils.isNotEmpty(request.getAreaCode())){
            search =request.getAreaCode();
        }
        String value =redisUtils.get(search);
        if(StringUtils.isNotEmpty(value)){
            return R.ok().put("list",JSONArray.parseArray(value,Area.class));
        }else {
            EntityWrapper<Area> wrapper = new EntityWrapper<>();
            wrapper.where("p_id={0}",search);
            listArea = areaService.selectList(wrapper);
            if(listArea!=null&&listArea.size()>0){
                redisUtils.set(search, JSONArray.toJSONString(listArea));
                return R.ok().put("list",listArea);
            }else {
                return R.noData();
            }
        }
    }
}
