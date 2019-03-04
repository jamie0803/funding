package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper mapper;

    public Permission queryParentPermission() {
        return mapper.selectParentPermission();
    }

    public List<Permission> queryChildPermissions(Integer id) {
        return mapper.selectChildPermission(id);
    }

    public List<Permission> getRootList() {
        return mapper.selectRootList();
    }

    public List<Permission> queryAllPermissions() {
        return mapper.selectAllPermissions();
    }

    public boolean addPermission(Permission permission) {
        int count = mapper.selectCount(permission);
        if (count > 0){
            return false;
        }
        int res = mapper.insertSelective(permission);
        if (res == 1){
            return true;
        }
        return false;
    }

    public boolean updatePermission(Permission permission) {
        return mapper.updateByPrimaryKeySelective(permission);
    }

    public boolean deletePermission(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public List<Integer> queryAssignPermission(Integer roleid) {
        return mapper.queryAssignPermission(roleid);
    }

    public List<Permission> queryPermissionForLoginacct(String loginacct) {
        return mapper.selectPermissionForLoginacct(loginacct);
    }
}
