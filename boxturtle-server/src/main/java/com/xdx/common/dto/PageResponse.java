package com.xdx.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageResponse<T extends List<?>> extends BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = -1771705466572683521L;
	
	/**
	 *  当前页
	 */
    private int pageNum;
    
    /**
     *  每页的数量
     */
    private int pageSize;
    
    /**
     *  总记录数
     */
    private long total;
    
    /**
     *  总页数
     */
    private int pages;
    
    /**
     * 页大小
     */
    private int size;

    public PageResponse() {
    }

    /**
     * 包装page对象
     * @param list
     * @param code
     * @param msg
     */
    public PageResponse(T list,int code,String msg) {
        this(list);
        setCode(code);
        setMsg(msg);

    }

    /**
     * 包装Page对象 
     * @param list page结果
     */
    public PageResponse(T list) {
        if (list instanceof Page) {
            super.setCode(0);
            super.setMsg("success");
            @SuppressWarnings("rawtypes")
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.size = page.size();
            this.pageSize = page.getPageSize();
            this.setData(list);
        }else{
            super.setCode(0);
            this.pageNum =1;
            this.total =list.size();
            this.pages =1;
            this.size =list.size();
            this.pageSize =list.size();
            this.setData(list);
        }
    }

    public void setData(T data) {
        super.setData(data);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", code=").append(getCode());
        sb.append(", msg=").append(getMsg());
        sb.append(", data=").append(getData());
        sb.append('}');
        return sb.toString();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /***
     * success:操作成功返回 <br/>
     * @param data 返回到前端的数据对象
     * @return PageResponse
     * @since JDK 1.7
     */
    public static <E extends List<?>> PageResponse<E> successPage(E data) {
        PageResponse<E> result = new PageResponse<E>(data,0,"success");
        return result;
    }
}
