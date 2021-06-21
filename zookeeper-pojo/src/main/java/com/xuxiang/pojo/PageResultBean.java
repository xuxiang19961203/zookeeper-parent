package com.xuxiang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.pojo
 * @create: 2021/6/13
 * @FileName: PageResultBean
 * @Description:
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResultBean<T> implements Serializable {
    public static final int DEFAULT_NAVIGATE_PAGES = 8;
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;

    public void setTotal(long total) {
        this.total = total;
        //传入总数后自动计算分页数
        int pages = (int) (total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        this.pages = pages;

    }

    //总记录数
    protected long total;
    //结果集
    protected List<T> list;

    //由于startRow和endRow不常用，这里说个具体的用法
    //可以在页面中"显示startRow到endRow 共size条数据"

    //当前页面第一个元素在数据库中的行号
    private long startRow;
    //当前页面最后一个元素在数据库中的行号
    private long endRow;

    //总页数
    private int pages;

    //前一页
    private int prePage;
    //下一页
    private int nextPage;

    public int getPrePage() {
        return pageNum - 1;
    }

    public int getNextPage() {
        return pageNum + 1;
    }

    public boolean isFirstPage() {
        return pageNum == 1;
    }

    public boolean isLastPage() {
        return pageNum == pages;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    //是否为第一页
    private boolean isFirstPage = false;
    //是否为最后一页
    private boolean isLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
    //导航页码数
    private int navigatePages;
    //所有导航页号
    private int[] navigatepageNums;
    //导航条上的第一页
    private int navigateFirstPage;
    //导航条上的最后一页
    private int navigateLastPage;

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
        this.calcNavigatepageNums();
    }

    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }
}
