package com.xuxiang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods_category implements Serializable {
    private Short id;
    private String name;
    private String mobileName;
    private Short parentId;
    private String parentIdPath;
    private Byte level;
    private Byte sortOrder;
    private Byte isShow;
    private String image;
    private Byte isHot;
    private Byte catGroup;
    private Byte commissionRate;
    private static final long serialVersionUID = 1L;

}