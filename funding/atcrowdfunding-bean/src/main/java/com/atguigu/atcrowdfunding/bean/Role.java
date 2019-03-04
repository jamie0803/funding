package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer id;

    private String name;

    private String code;

    private String mark;

    private Date createtime;
}