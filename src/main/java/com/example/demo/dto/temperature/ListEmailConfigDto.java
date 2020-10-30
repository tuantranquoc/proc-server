package com.example.demo.dto.temperature;

import com.example.demo.model.conf.EmailConfig;

import java.util.ArrayList;
import java.util.List;

public class ListEmailConfigDto {
    Long totalCount;
    int pageCount;
    List<EmailConfig> data = new ArrayList<>();

    public ListEmailConfigDto(Long totalCount, int pageCount, List<EmailConfig> data) {
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.data = data;
    }

    public ListEmailConfigDto(List<EmailConfig> data) {
        this.data = data;
    }

    public ListEmailConfigDto(int pageCount) {
        this.pageCount = pageCount;
    }

    public ListEmailConfigDto(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<EmailConfig> getData() {
        return data;
    }

    public void setData(List<EmailConfig> data) {
        this.data = data;
    }
}
