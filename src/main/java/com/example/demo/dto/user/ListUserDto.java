package com.example.demo.dto.user;

import java.util.ArrayList;
import java.util.List;

public class ListUserDto {
    long totalCount;
    int pageCount;

    public ListUserDto(long totalCount, int pageCount, List<UserDto> data) {
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.data = data;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    List<UserDto> data = new ArrayList<>();

    public ListUserDto(long count, List<UserDto> userDtoList) {
        this.totalCount = count;
        this.data = userDtoList;
    }

    public ListUserDto(List<UserDto> userDtoList) {
        this.data = userDtoList;
    }

    public ListUserDto(long count) {
        this.totalCount = count;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserDto> getData() {
        return data;
    }

    public void setData(List<UserDto> data) {
        this.data = data;
    }
}
