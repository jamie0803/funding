package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.vo.Data;
import com.atguigu.atcrowdfunding.vo.Page;
import org.activiti.engine.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "role/index";
    }

    @ResponseBody
    @RequestMapping("/queryPage")
    public Object queryPage(Integer pageNo, Integer pageSize, String text) {
        logger.info("入参pageNo{},pageSize{}", pageNo, pageSize, text);
        start();
        if (pageNo == null && pageSize == null) {
            message("入参为空!");
            return end();
        }
        try {
            Page<Role> page = roleService.queryPage(pageNo, pageSize, text);
            if (page == null) {
                data(null);
                message("nulldata!");
            }
            data(page);
            success(true);
        } catch (Exception e) {
            e.printStackTrace();
            success(false);
            message("查询数据失败！");
        }
        return end();
    }

    @RequestMapping(value = "/toAdd")
    public String toAdd() {
        return "role/add";
    }

    @ResponseBody
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Object addRole(Role role) {
        logger.info("入参roleName{},roleCode{},mark{}", role.getName(), role.getCode());
        start();
        if (role.getName() == null && role.getCode() == null) {
            success(false);
            message("入参为空!");
            return end();
        }
        try {
            role.setCreatetime(new Date());
            boolean flag = roleService.insert(role);
            success(true);
            return end();
        } catch (Exception e) {
            e.printStackTrace();
            message("插入失败!");
            success(false);
        }
        return end();
    }

    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    public Object toUpdate(String id, Model model) {
        logger.info("入参角色id{}", id);
        AjaxResult result = new AjaxResult();
        /*ModelAndView mv = new ModelAndView();
        Role role = roleService.getRoleById(id);
        mv.addObject("role", role);
        mv.setViewName("role/edit");
        return mv;*/
        Role role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "role/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public Object doUpdate(Role role) {
        logger.info("入参{}");
        start();
        try {
            boolean flag = roleService.update(role);
            if (flag) {
                success(true);
            } else {
                success(false);
                message("修改失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message("系统异常!");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/doAssign")
    public Object doAssign(Integer userId, Integer[] ids) {
        start();
        try {
            boolean flag = roleService.assignRole(userId, ids);
            success(true);
        } catch (Exception e) {
            e.printStackTrace();
            message("分配角色失败!");
            success(false);
        }
        return end();
    }

    @RequestMapping(value = "/toAssignPermission", method = RequestMethod.GET)
    public Object toAssignPermission(Integer roleId) {
        return "role/assignPermission";
    }

    @ResponseBody
    @RequestMapping("/loadDataAsync")
    public Object loadDataAsync(Integer roleid){
        //查询已分配给角色的权限
        List<Integer> permissionids = permissionService.queryAssignPermission(roleid);
        //加载树结构
        List<Permission> list = new ArrayList<Permission>();
        List<Permission> permissions = permissionService.queryAllPermissions();
        Map<Integer,Permission> map = new HashMap<Integer, Permission>();
        for (Permission permission : permissions) {
            map.put(permission.getId(), permission);
            if (permissionids.contains(permission.getId())){
                permission.setChecked(true);
            }
        }
        for (Permission permission : permissions) {
            Permission child = permission;
            if (child.getPid() == null){     //根节点
                list.add(permission);
            }else {                         //子节点  找父节点
                Permission parent = map.get(permission.getPid());
                parent.getChildren().add(permission);
            }
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/assignRolePermission")
    public Object assignRolePermission(Integer roleid, Data data){
        start();
        boolean flag = false;

        int count = roleService.queryIsAssigned(roleid, data.getIds());
        if (count==data.getIds().size()){
            success(flag);
            message("已经分配过权限");
            return end();
        }

        try {
            flag = roleService.assignRolePermission(roleid,data.getIds());
            success(flag);
        } catch (Exception e) {
            e.printStackTrace();
            success(flag);
            message("分配失败");
        }
        return end();
    }
}
