package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DispatcherController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); //可以销毁session对象;也可以不销毁,把session属性删除;
        return "redirect:/toLogin.htm";
    }

    @ResponseBody
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Object doLogin(String loginacct, String userpswd, String usertype, HttpSession session) {

        start();

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", userpswd);
            paramMap.put("usertype", usertype);

            User loginUser = userService.login(paramMap);

            //查询用户拥有的权限
            List<Permission> list = new ArrayList<Permission>();
            List<Permission> permissions = permissionService.queryPermissionForLoginacct(loginacct);
            //组装权限间父子关系
            Map<Integer, Permission> map = new HashMap<Integer, Permission>();
            for (Permission permission : permissions) {
                map.put(permission.getId(), permission);

                Permission child = permission;
                if (permission.getPid() == null) {
                    Permission root = permission;
                    list.add(root);
                } else {
                    Permission parent = map.get(permission.getPid());
                    parent.getChildren().add(permission);
                }

            }
            session.setAttribute(Const.PERMISSION_TREE, list);
            session.setAttribute(Const.LOGIN_USER, loginUser);

            success(true);
        } catch (Exception e) {
            message(e.getMessage());
            success(false);
            e.printStackTrace();
        }

        return end(); // {"success":true}     {"success":false,"message":"用户名称不存在"}
    }

    @ResponseBody
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public Object queryPage(Integer pageNo, Integer pageSize) {
        start();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("pageNo", pageNo);
            paramMap.put("pageSize", pageSize);

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


    //异步请求处理
    //@ResponseBody //采用HttpMessageConverter 将对象转换为相应的类型返回.
    //如果返回的结果类型为Object,采用MappingJackson2HttpMessageConverter消息转换器,将对象转换为json格式字符串,以流的形式返回.
    //如果返回的结果类型为String,那么会采用StringHttpMessageConverter消息转换器,将字符串以流的形式返回
    /*@RequestMapping("/doLogin")
    public Object doLogin(String loginacct,String userpswd,String usertype,HttpSession session) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("loginacct", loginacct);
			paramMap.put("userpswd", userpswd);
			paramMap.put("usertype", usertype);
			
			User loginUser = userService.login(paramMap);
			session.setAttribute(Const.LOGIN_USER, loginUser);
			
			result.put("success", true);
		} catch (Exception e) {
			result.put("message", e.getMessage());
			result.put("success", false);
			e.printStackTrace();
		}
		
		return result; // {"success":true}     {"success":false,"message":"用户名称不存在"}
	}*/

	/*//异步请求处理
    @ResponseBody //采用HttpMessageConverter 将对象转换为相应的类型返回.
	//如果返回的结果类型为Object,采用MappingJackson2HttpMessageConverter消息转换器,将对象转换为json格式字符串,以流的形式返回.
	//如果返回的结果类型为String,那么会采用StringHttpMessageConverter消息转换器,将字符串以流的形式返回
	@RequestMapping("/doLogin")
	public Object doLogin(String loginacct,String userpswd,String usertype,HttpSession session) {
		
		AjaxResult result = new AjaxResult();
		
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("loginacct", loginacct);
			paramMap.put("userpswd", userpswd);
			paramMap.put("usertype", usertype);
			
			User loginUser = userService.login(paramMap);
			session.setAttribute(Const.LOGIN_USER, loginUser);
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			e.printStackTrace();
		}
		
		return result; // {"success":true}     {"success":false,"message":"用户名称不存在"}
	}*/

    //同步请求处理
/*	@RequestMapping("/doLogin")
    public String doLogin(String loginacct,String userpswd,String usertype,HttpSession session) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);
		paramMap.put("usertype", usertype);
		
		User loginUser = userService.login(paramMap);
		session.setAttribute(Const.LOGIN_USER, loginUser);
		
		return "main";
	}*/


}
