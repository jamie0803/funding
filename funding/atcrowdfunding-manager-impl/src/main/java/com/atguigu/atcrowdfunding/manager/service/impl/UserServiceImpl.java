package com.atguigu.atcrowdfunding.manager.service.impl;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Message;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.exception.LoginFailureException;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    /**
     *  用户登录
     * @param paramMap  登录账号
     * @return
     */
    public User login(Map<String, Object> paramMap) {

        //1.密码加密
        String userpswd = (String) paramMap.get("userpswd");

        //String newUserpswd = MD5Util.digest(userpswd);

        //paramMap.put("userpswd", userpswd);

        //2.调用dao做数据库用户查询
        User user = userMapper.login(paramMap); //只根据用户名称查询,用户名称是唯一的.

        //3.判断用户为null
        if (user == null) {
            throw new LoginFailureException(Const.LOGIN_LOGINACCT_ERROR);
        }

        //4.判断用户不为null,判断密码是否正确.
        if (!user.getUserpswd().equals(userpswd)) {
            throw new LoginFailureException(Const.LOGIN_USERPSWD_ERROR);
        }

        //5.将用户对象返回
        return user;
    }

    /**
     *  分页查询
     * @param paramMap pageNo,pageSize,text
     * @return
     */
    public Page<User> queryList(Map<String, Object> paramMap) {
        Integer pageSize = (Integer) paramMap.get("pageSize");
        Integer pageNo = (Integer) paramMap.get("pageNo");
        Page<User> page = new Page<User>(pageNo, pageSize);
        int startIndex = page.startIndex();
        paramMap.put("startIndex", startIndex);

        List<User> list = userMapper.getAllUser(paramMap);
        Integer count = userMapper.count(paramMap);

        //封装数据
        page.setDatas(list);
        page.setTotalSize(count);

        return page;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    public boolean insert(User user) {
        //判断是否存在登录名、邮箱一样
        int res = userMapper.queryUserCount(user.getLoginacct(),user.getEmail());
        if (res != 0){
            logger.error("用户信息已存在！");
            return false;
        }
        int count = userMapper.insertSelective(user);
        if (count != 0){
            logger.info("插入用户信息成功！");
            return true;
        }
        return false;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public boolean delete(Integer id) {
        int count = userMapper.deleteUser(id);
        if (count == 1){
            logger.info("删除成功！");
            return true;
        }
        return false;
    }

    /**
     * 更新用户时获取用户信息展示在前端页面
     * @param id
     * @return  user对象
     */
    public User getById(Integer id) {
        User user = userMapper.getUserById(id);
        if (user == null){
            logger.error("查询用户信息为空！");
            return null;
        }
        return user;
    }

    public boolean update(User user) {
        if (user == null){
            logger.error("要修改的用户信息为空！");
        }
        int count = userMapper.update(user);
        if (count != 0){
            logger.info("修改的用户信息成功！");
            return true;
        }
        return false;
    }

    public boolean batchDelete(Integer[] id) {
        int count = userMapper.batchDelete(id);
        if (count == 0) {
            return false;
        }
        return true;
    }

    public List<Role> queryAssignedRole(Integer userId) {
        List<Role> list = userMapper.selectAllRole(userId);
        return list;
    }

    public List<Role> queryUnassignedRole(List<Role> assignedList) {
        List<Role> unRole = userMapper.selectUnassignedRole(assignedList);
        return unRole;
    }

    public boolean assignRole(Integer userId, List<Integer> ids) {
        //判断角色是否分配过
        Integer count = userMapper.assignExist(userId, ids);
        if (count > 0){
            return false;
        }
        Integer res = userMapper.assign(userId, ids);
        if (ids.size() == res){
            return true;
        }
        return false;
    }

    public Boolean unassign(Integer userId, List<Integer> ids) {
        Integer count = userMapper.unassign(userId, ids);
        if (count == 0){
            return false;
        }
        return true;
    }


}
