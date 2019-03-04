package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {
    List<Role> queryPage(@Param("startIndex") int startIndex, @Param("pageSize") Integer pageSize, @Param("text") String text);

    Integer count(@Param("startIndex") int startIndex, @Param("pageSize") Integer pageSize, @Param("text") String text);

    int insertSelective(Role role);

    @Select("select * from t_role where id=#{id}")
    Role getRole(String id);

    int update(Role role);

    @Select("select * from t_role")
    List<Role> selectAllRole();

    int assign(@Param("userId") Integer userId, @Param("ids") Integer[] ids);

    int assignRolePermission(@Param("roleid") Integer roleid, @Param("ids") List<Integer> ids);

    int selectCount(@Param("ids") List<Integer> ids, @Param("roleid") Integer roleid);

    void cancelAssignByRoleid(Integer roleid);
}
