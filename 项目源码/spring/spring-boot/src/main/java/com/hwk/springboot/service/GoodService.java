package com.hwk.springboot.service;

import com.hwk.springboot.dao.GoodDao;
import com.hwk.springboot.entity.Good;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {
    @Resource
    private GoodDao goodDao;

    public void addGood(Good good) {
        goodDao.addGood(good);
    }

    public List<Integer> getGoods(Integer goodid) {
        return goodDao.getGoods(goodid);
    }

    public void deleteGood(Good good) {
        goodDao.deleteGood(good);
    }
}
