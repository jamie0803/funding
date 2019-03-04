package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Permission {
    private Integer id;

    private Integer pid;

    private String name;

    private String icon;

    private String url;

    private boolean open;

    private boolean checked;

    private List<Permission> children = new ArrayList<Permission>();
}