package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advert;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController extends BaseController {
    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index() {
        return "advert/index";
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(HttpSession session, @RequestParam MultipartFile file) throws IOException {
        start();
        if (file.isEmpty()) {
            message("请选择要上传的内容");
            return end();
        }
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        String originalFilename = file.getOriginalFilename();   //a.txt
        String extFilename = originalFilename.substring(originalFilename.lastIndexOf("."));     // .txt
        String filePath = UUID.randomUUID().toString().replaceAll("-", "") + extFilename;

        ServletContext servletContext = session.getServletContext();
//        String contextPath = servletContext.getContextPath();
        String realPath = servletContext.getRealPath("/pic/ad") + "\\";
        String path = realPath + filePath;
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        file.transferTo(dest);
        Advert advert = new Advert();
        advert.setIconpath(filePath);
        advert.setUrl(path);
        advert.setUserid(user.getId());
        advert.setStatus("1");
        advert.setName(file.getName());

        boolean flag = advertService.insertAdvert(advert);
        if (flag) {
            success(flag);
            return end();
        }
        success(flag);
        message("上传失败!");
        return end();
    }

}
