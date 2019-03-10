package com.txyz.baseData.request;

import lombok.Data;

/**
 * Created by alex on 2019/3/7.
 */
@Data
public class AreaRequest {
    private String areaCode;
    private Integer currentPage;
    private Integer pageSize;
}
