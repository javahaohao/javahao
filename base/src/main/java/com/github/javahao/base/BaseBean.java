package com.github.javahao.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.javahao.util.IDUtil;
import com.github.javahao.util.StringUtils;
import com.github.javahao.util.UserSpace;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * usedfor：所有Bean的基类，封装了一些公用的字段
 * Created by javahao on 2017/6/19.
 * auth：JavaHao
 */
public abstract class BaseBean<T> implements Serializable {
    protected String id;
    protected List<String> idList;
    protected String creatorId;
    protected String modifierId;
    @JSONField(
        format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    protected Date createdAt;
    @JSONField(
        format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    protected Date modifiedAt;
    public void preSave(){
        if(StringUtils.isBlank(this.id))
            this.id= IDUtil.createUUID();
        this.creatorId= UserSpace.getId();
        this.createdAt=new Date();
    }
    public void preUpdate(){
        this.modifierId= UserSpace.getId();
        this.modifiedAt=new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
