package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Good;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface GoodDao extends Mapper<Good> {


    void addGood(@Param("good") Good good);

    List<Integer> getGoods(@Param("goodid") Integer goodid);

    void deleteGood(@Param("good") Good good);
}
