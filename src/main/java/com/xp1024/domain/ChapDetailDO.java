package com.xp1024.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.dao
 * @date 2020/2/28 14:24
 */
@TableName("chap_detail")
public class ChapDetailDO {

    /**
     * 流水ID号
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 发布日期
     */
    private Date publishDate;
    /**
     * 数据库生成时间
     */
    private Date createDate;
    /**
     * 目标页面
     */
    private String detailUrl;
    /**
     * 目标媒体地址
     */
    private String m3u8Url;
    /**
     * 是否下载过
     */
    private Boolean hasDownload;

    public ChapDetailDO() {
    }

    public ChapDetailDO(Long id, String title, Date publishDate, Date createDate, String detailUrl, String m3u8Url, Boolean hasDownload) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.createDate = createDate;
        this.detailUrl = detailUrl;
        this.m3u8Url = m3u8Url;
        this.hasDownload = hasDownload;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getM3u8Url() {
        return m3u8Url;
    }

    public void setM3u8Url(String m3u8Url) {
        this.m3u8Url = m3u8Url;
    }

    public Boolean getHasDownload() {
        return hasDownload;
    }

    public void setHasDownload(Boolean hasDownload) {
        this.hasDownload = hasDownload;
    }
}
