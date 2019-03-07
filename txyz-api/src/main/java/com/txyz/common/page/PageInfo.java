package com.txyz.common.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo<T> implements Serializable {
    private Integer totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currentPage;
    private List<T> list;
    public PageInfo(){}



}
