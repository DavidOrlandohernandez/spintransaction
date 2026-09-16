package com.spin.transaction.wraper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    private int page;
    private int limit;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public Meta(int page, int limit, long totalItems) {
        this.page = page;
        this.limit = limit;
        this.totalItems = totalItems;

        this.totalPages = (int) Math.ceil((double) totalItems / limit);
        this.hasNext = page < totalPages - 1;
        this.hasPrevious = page > 0;
    }


}