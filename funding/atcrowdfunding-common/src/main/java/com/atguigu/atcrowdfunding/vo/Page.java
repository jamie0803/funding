package com.atguigu.atcrowdfunding.vo;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class Page<T> {
    private List<T> datas;
    private int totalNo;    //总页码数
    private int totalSize;  //总条数
    private int pageNo;
    private int pageSize;

    public Page() {
        pageNo = 1;
        pageSize = 8;
    }

    public Page(int pageNo, int pageSize) {
        if (pageNo <= 0 ){
            this.pageNo = 1;
        }else {
            this.pageNo = pageNo;
        }
        if (pageSize >= 5){
            this.pageSize = 5;
        }else {
            this.pageSize = pageSize;
        }
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(int totalNo) {
        this.totalNo = totalNo;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        this.totalNo = totalSize % pageSize == 0 ? (totalSize/pageSize) : (totalSize/pageSize+1);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    //每页的开始索引       0，10； 10，10
    public int startIndex(){
        return (pageNo-1)*pageSize;
    }
}
