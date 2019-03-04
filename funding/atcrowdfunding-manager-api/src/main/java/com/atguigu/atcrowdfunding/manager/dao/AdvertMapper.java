package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Advert;
import org.springframework.stereotype.Component;

@Component
public interface AdvertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advert record);

    int insertSelective(Advert record);

    Advert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advert record);

    int updateByPrimaryKey(Advert record);
}
