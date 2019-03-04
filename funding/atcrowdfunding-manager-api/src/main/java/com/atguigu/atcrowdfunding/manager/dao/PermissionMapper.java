package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PermissionMapper {

    @Select("select * from t_permission where pid is null")
    Permission selectParentPermission();

    @Select("select * from t_permission where pid=#{id}")
    List<Permission> selectChildPermission(Integer id);

    @Select("select * from t_permission where pid is null")
    List<Permission> selectRootList();

    @Select("select * from t_permission")
    List<Permission> selectAllPermissions();

    int insertSelective(Permission permission);

    boolean updateByPrimaryKeySelective(Permission permission);

    boolean deleteByPrimaryKey(Integer id);

    int selectCount(Permission permission);

    List<Integer> queryAssignPermission(Integer roleid);

    List<Permission> selectPermissionForLoginacct(String loginacct);
}
