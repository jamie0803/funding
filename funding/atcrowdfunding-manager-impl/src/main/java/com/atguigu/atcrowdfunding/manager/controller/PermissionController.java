package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    @ResponseBody
    @RequestMapping("/loadData2")
    public Object loadData2() {
        List<Permission> list = new ArrayList<Permission>();
        Map<Integer, Permission> map = new HashMap<Integer, Permission>();
        List<Permission> permissions = permissionService.queryAllPermissions();
        for (Permission permission : permissions) {
            //将所有权限存储在map中
            map.put(permission.getId(), permission);
            //设每个节点都是子节点
            Permission child = permission;
            //组装根节点
            if (child.getPid() == null) {
                list.add(permission);
            } else {
                //找到父节点, 进行组装
                Permission parent = map.get(child.getPid());
                parent.getChildren().add(child);
            }
        }

        return list;
    }

    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        List<Permission> list = new ArrayList<Permission>();
        //找到根节点
        List<Permission> root = permissionService.getRootList();
        //遍历  递归的查询子节点及子节点的子节点...
        for (Permission permission : root) {
            queryChildPermissions(permission);
            list.add(permission);
        }
        return list;
    }

    /**
     * 递归查询子节点      效率不够高
     *
     * @param parent
     */
    private void queryChildPermissions(Permission parent) {
        List<Permission> ChildPermissions = permissionService.queryChildPermissions(parent.getId());
        for (Permission childPermission : ChildPermissions) {
            queryChildPermissions(childPermission);
        }
        parent.setChildren(ChildPermissions);
    }

    @RequestMapping("add")
    public Object add() {
        return "permission/add";
    }


    @ResponseBody
    @RequestMapping("doAdd")
    public Object doAdd(Permission permission) {
        if (permission == null) {
            return null;
        }
        start();
        try {
            boolean flag = permissionService.addPermission(permission);
            success(flag);
        } catch (Exception e) {
            e.printStackTrace();
            success(false);
            message(e.getMessage());
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("update")
    public Object update(Permission permission){
        start();
        try {
            boolean flag = permissionService.updatePermission(permission);
            success(true);
        } catch (Exception e) {
            success(false);
            message(e.getMessage());
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Integer id){
        start();
        boolean flag = false;
        try {
            flag = permissionService.deletePermission(id);
            success(flag);
        } catch (Exception e) {
            success(flag);
            message("删除失败");
            e.printStackTrace();
        }
        return end();
    }


    public static void main(String[] args) {
    }
}
