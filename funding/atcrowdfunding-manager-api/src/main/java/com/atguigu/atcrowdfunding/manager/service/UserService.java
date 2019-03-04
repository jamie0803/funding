package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {

	User login(Map<String, Object> paramMap);

	Page<User> queryList(Map<String, Object> paramMap);

	boolean insert(User user);

    boolean delete(Integer id);

	User getById(Integer id);

	boolean update(User user);

	boolean batchDelete(Integer[] id);

    List<Role> queryAssignedRole(Integer userId);

	List<Role> queryUnassignedRole(List<Role> assignedList);

    boolean assignRole(Integer userId, List<Integer> ids);

	Boolean unassign(Integer userId, List<Integer> ids);
}
