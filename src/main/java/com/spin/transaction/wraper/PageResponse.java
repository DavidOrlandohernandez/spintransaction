package com.spin.transaction.wraper;


import lombok.Getter;

import java.util.List;


@Getter
public class PageResponse<T> {

    private final List<T> data;

    private final Meta meta;

    public PageResponse(List<T> data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

}