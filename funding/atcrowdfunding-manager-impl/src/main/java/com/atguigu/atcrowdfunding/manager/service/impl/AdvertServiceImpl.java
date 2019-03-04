package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advert;
import com.atguigu.atcrowdfunding.manager.dao.AdvertMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertServiceImpl implements AdvertService{

    @Autowired
    private AdvertMapper advertMapper;

    public boolean insertAdvert(Advert advert) {
        int i = advertMapper.insertSelective(advert);
        if (i != 0){
            return true;
        }
        return false;
    }
}
