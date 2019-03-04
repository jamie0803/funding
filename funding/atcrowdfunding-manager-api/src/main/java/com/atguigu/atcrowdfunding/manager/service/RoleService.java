package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.vo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    Page<Role> queryPage(Integer pageNo, Integer pageSize, String text);

    boolean insert(Role role);

    Role getRoleById(String id);

    boolean update(Role role);

    List<Role> queryAllRole();

    boolean assignRole(Integer userId, Integer[] ids);

    boolean assignRolePermission(Integer roleid, List<Integer> ids);

    int queryIsAssigned(Integer roleid, List<Integer> ids);
}
