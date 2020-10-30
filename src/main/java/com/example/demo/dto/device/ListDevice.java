package com.example.demo.dto.device;

import com.example.demo.model.device.Device;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ListDevice {
    Long totalCount;
    int pageCount;
    List<Device> data = new ArrayList<>();


    public List<Device> getData() {
        return data;
    }

    public ListDevice(Long totalCount, int pageCount, List<Device> data) {
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.data = data;
    }

    public void setData(List<Device> data) {
        this.data = data;
    }

    public ListDevice(Long count, List<Device> data) {
        this.totalCount = count;
        this.data = data;
    }

    public ListDevice(List<Device> data) {
        this.data = data;
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

    public ListDevice(Long count) {
        this.totalCount = count;
    }
}
