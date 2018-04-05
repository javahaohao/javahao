package com.github.javahao.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.javahao.util.IDUtil;
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
    @JSONField(serializeUsing= ToStringSerializer.class)
    protected Long id;
    protected String idStr;
    protected List<Long> idList;
    @JSONField(serializeUsing= ToStringSerializer.class)
    protected Long creatorId;
    @JSONField(serializeUsing= ToStringSerializer.class)
    protected Long modifierId;
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
        if(null==this.id)
            this.id = IDUtil.snkwflakeId();
        this.creatorId= UserSpace.getId();
        this.createdAt=new Date();
    }
    public void preUpdate(){
        this.modifierId= UserSpace.getId();
        this.modifiedAt=new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
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

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
}
