package com.atguigu.atcrowdfunding.manager.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.vo.Data;
import com.atguigu.atcrowdfunding.vo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    @ResponseBody
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public Object queryPage(Integer pageNo, Integer pageSize, String text) {
        logger.info("查询每页显示数据开始，参数{}:", pageNo, pageSize, text);
        start();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("pageNo", pageNo);
            paramMap.put("pageSize", pageSize);
            paramMap.put("text", text);

            Page<User> page = userService.queryList(paramMap);
            data(page);
            success(true);

        } catch (Exception e) {

            e.printStackTrace();
            message(Const.QUERY_USER_FAIL);
            success(false);
        }
        return end();
    }

    @RequestMapping(value = "/add")
    public Object add() {
        return "user/add";
    }

    @ResponseBody
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    public Object doAdd(@RequestBody User user) {
        logger.info("前端入参user对象为：{}", JSON.toJSON(user));
        start();
        if (user == null) {
            message("入参为空");
            return end();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreatetime(sdf.format(new Date()));
        boolean flag = userService.insert(user);
        if (flag) {
            message("新增成功！");
        } else {
            message("用户已存在！");
        }
        success(flag);
        return end();
    }

    //单个删除
    @ResponseBody
    @RequestMapping(value = "/doDelete", method = RequestMethod.POST)
    public Object doDelete(Integer id) {
        logger.info("用户入参id{}", id);
        start();
        if (id == null) {
            message("入参id为空");
            return end();
        }
        boolean flag = userService.delete(id);
        if (flag) {
            message("删除成功！");
        } else {
            message("删除失败！");
        }
        success(flag);
        return end();
    }

    //批量删除
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public Object batchDelete(Integer[] id) {       //前端拼串ids="id=1&id=2&id=3"   后端以id来接收
        logger.info("要删除的用户id入参{}", id);
        start();
        boolean flag = false;
        try {
            if (id == null) {
                message("要删除的用户id入参空！");
                success(flag);
                return end();
            }
            flag = userService.batchDelete(id);
            message("删除用户信息成功！");
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            message("删除用户信息失败！");
        }
        success(flag);
        return end();
    }

    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    public String toUpdate(Integer id, Model model) {
        logger.info("用户入参id{}", id);
        start();
        if (id == null) {
            message("入参为空！");
            data(null);
            return JSON.toJSONString(end());
        }
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public Object doUpdate(User user) {
        logger.info("修改用户信息入参user{}", user);
        start();
        if (user == null) {
            message("入参为空！");
            data(null);
            return end();
        }
        try {
            boolean flag = userService.update(user);
            if (flag) {
                message("修改用户信息成功！");
            } else {
                message("修改用户信息失败！");
            }

            success(flag);
        } catch (Exception e) {
            message(e.getMessage());
            e.getStackTrace();
        }

        return end();
    }

    @RequestMapping(value = "/toAssignRole", method = RequestMethod.GET)
    public Object toAssignRole(Integer userId, Model model) {
        logger.info("入参id{}", userId);
        //查询所有角色
//        List<Role> roleList = roleService.queryAllRole();

        //查询当前用户已分配的角色
        List<Role> assignedList = userService.queryAssignedRole(userId);
        //查询当前用户未分配的角色
        List<Role> unassignedList = userService.queryUnassignedRole(assignedList);

        model.addAttribute("assignedList", assignedList);
        model.addAttribute("unassignedList", unassignedList);
        return "user/assignRole";
    }

    @ResponseBody
    @RequestMapping(value = "assign", method = RequestMethod.POST)
    public Object assign(Integer userId, Data data) {
        logger.info("入参{},用户userId, 用户roleids", userId, data.getIds());
        start();
        if (userId == null || (data == null && data.getIds().size() == 0)) {
            message("入参空!");
            success(false);
            return end();
        }
        List<Integer> ids = data.getIds();
        boolean flag = false;
        try {
            flag = userService.assignRole(userId, ids);
            success(flag);
        } catch (Exception e) {
            e.printStackTrace();
            success(flag);
            message("分配失败!");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping(value = "unassign", method = RequestMethod.POST)
    public Object unassign(Integer userId, Data data){
        start();
        List<Integer> ids = data.getIds();
        //判断入参是否为空
        try {
            Boolean flag = userService.unassign(userId, ids);
            success(true);
        } catch (Exception e) {
            e.printStackTrace();
            message("取消分配失败!");
            success(false);
        }
        return end();
    }
}