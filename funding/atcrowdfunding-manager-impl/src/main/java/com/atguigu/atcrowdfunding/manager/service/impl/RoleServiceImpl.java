package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Page<Role> queryPage(Integer pageNo, Integer pageSize, String text) {
        Page<Role> page = new Page<Role>(pageNo, pageSize);
        int startIndex = page.startIndex();
        List<Role> list = roleMapper.queryPage(startIndex, pageSize, text);
        Integer count = roleMapper.count(startIndex, pageSize, text);

        //封装数据到page类中
        page.setDatas(list);
        page.setTotalSize(count);
        return page;
    }

    public boolean insert(Role role) {
        int count = roleMapper.insertSelective(role);
        if (count == 1) {
            return true;
        }
        return false;
    }

    public Role getRoleById(String id) {
        Role role = roleMapper.getRole(id);
        return role;
    }

    public boolean update(Role role) {
        int count = roleMapper.update(role);
        if (count == 0) {
            return false;
        }
        return true;
    }

    public List<Role> queryAllRole() {
        return roleMapper.selectAllRole();
    }

    public boolean assignRole(Integer userId, Integer[] ids) {
        if (userId == null || ids == null) {
            return false;
        }
        int count = roleMapper.assign(userId, ids);
        if (count != ids.length) {
            return false;
        }
        return true;
    }

    public boolean assignRolePermission(Integer roleid, List<Integer> ids) {
         //删除旧的分配
         roleMapper.cancelAssignByRoleid(roleid);
         int count = roleMapper.assignRolePermission(roleid, ids);
         if (count == ids.size()){
             return true;
         }
         return false;
    }

    public int queryIsAssigned(Integer roleid, List<Integer> ids) {
        return roleMapper.selectCount(ids,roleid);
    }

}
