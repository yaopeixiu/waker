package com.spring.cloud.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {
    private Long total = 0L;
    private long limit = 10L;
    private long page = 1L;
    private List<T> list;
}