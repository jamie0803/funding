package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {

	//@Select("select * from t_user where loginacct=#{loginacct}")
	User login(Map<String, Object> paramMap);

	List<User> getAllUser(Map<String, Object> paramMap);

	Integer count(Map<String, Object> paramMap);

	int insertSelective(User user);

	int queryUserCount(@Param("loginacct") String loginacct, @Param("email") String email);

	int deleteUser(Integer id);

	User getUserById(Integer id);

	int update(User user);

	int batchDelete(Integer[] id);

    List<Role> selectAllRole(Integer userId);

    List<Role> selectUnassignedRole(List<Role> assignedList);

    Integer assignExist(@Param("userId") Integer userId, @Param("ids") List<Integer> ids);

	Integer assign(@Param("userId") Integer userId, @Param("ids") List<Integer> ids);

	Integer unassign(@Param("userId") Integer userId, @Param("ids") List<Integer> ids);
}
