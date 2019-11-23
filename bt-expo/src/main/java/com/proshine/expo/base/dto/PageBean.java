package com.proshine.expo.base.dto;

import java.util.List;

public class PageBean<T> {
    /**
     * page 需要显示的页码
     */
    private int page = 1;
    /**
     * totalPages 总页数
     */
    private int totalPages = 1;
    /**
     * row 每页记录数
     */
    private int row = 10;
    /**
     * totalRecords 总记录数
     */
    private long totalRecords = 0;
    /**
     * isHavePrePage 是否有上一页
     */
    private boolean isHavePrePage = false;
    /**
     * isHaveNextPage 是否有下一页
     */
    private boolean isHaveNextPage = false;
    /**
     * isLastPage 是否为最后一页
     */
    private boolean isLastPage;
    /**
     * isFirstPage 是否为第一页
     */
    private boolean isFirstPage;
    /**
     * data 查询出的分页数据
     */
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 需要显示的页码 当前页
     *
     * @return 当前页
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页
     *
     * @param page 当前页
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 分页大小
     *
     * @return 分页大小
     */
    public int getRow() {
        return row;
    }

    /**
     * 设置分页大小
     *
     * @param row 分页大小
     */
    public void setRow(int row) {
        this.row = row;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * 设置总记录数
     */
    public void setTotalRecords(long count) {
        if (count < 0) {
            throw new RuntimeException("总记录数不能小于0!");
        }
        // 设置总记录数
        this.totalRecords = count;
        // 计算总页数
        this.totalPages = (int) (this.totalRecords / this.row);
        if (this.totalRecords % this.row != 0) {
            this.totalPages++;
        }
        // 计算是否有上一页
        this.isHavePrePage = this.page > 1;
        // 计算是否有下一页
        this.isHaveNextPage = this.page < this.totalPages;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean getIsHavePrePage() {
        return isHavePrePage;
    }

    public boolean getIsHaveNextPage() {
        return isHaveNextPage;
    }
}