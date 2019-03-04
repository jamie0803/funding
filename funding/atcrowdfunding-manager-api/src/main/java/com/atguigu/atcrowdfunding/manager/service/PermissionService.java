package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> queryChildPermissions(Integer id);

    List<Permission> getRootList();

    List<Permission> queryAllPermissions();

    boolean addPermission(Permission permission);

    boolean updatePermission(Permission permission);

    boolean deletePermission(Integer id);

    List<Integer> queryAssignPermission(Integer roleid);

    List<Permission> queryPermissionForLoginacct(String loginacct);
}
