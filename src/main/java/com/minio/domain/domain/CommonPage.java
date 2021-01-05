package com.minio.domain.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuelimin
 * @since 1.8
 */
public class CommonPage<T> implements Serializable {
    private long total;
    private List<T> rows;

    public CommonPage() {
    }

    public CommonPage(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

