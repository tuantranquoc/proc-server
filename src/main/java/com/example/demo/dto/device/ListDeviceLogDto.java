package com.example.demo.dto.device;

import java.util.ArrayList;
import java.util.List;

public class ListDeviceLogDto {
    Long totalCount;
    int pageCount;

    public ListDeviceLogDto(Long totalCount, int pageCount, List<DeviceLogDto> data) {
        this.totalCount = totalCount;
        this.pageCount = pageCount;
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

    List<DeviceLogDto> data = new ArrayList<>();

    public ListDeviceLogDto(List<DeviceLogDto> data) {
        this.data = data;
    }

    public ListDeviceLogDto(Long count) {
        this.totalCount = count;
    }

    public ListDeviceLogDto(Long count, List<DeviceLogDto> data) {
        this.totalCount = count;
        this.data = data;
    }



    public List<DeviceLogDto> getData() {
        return data;
    }

    public void setData(List<DeviceLogDto> data) {
        this.data = data;
    }
}
