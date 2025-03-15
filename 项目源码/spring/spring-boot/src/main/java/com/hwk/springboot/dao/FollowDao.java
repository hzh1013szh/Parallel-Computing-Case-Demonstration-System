package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FollowDao extends Mapper<Follow> {

    void addFollow(@Param("follow") Follow follow);

    List<Integer> getMyFollow(@Param("userid") Integer userid);

    List<Integer> getMyFan(@Param("followid") Integer followid);

    void deleteFollow(@Param("follow") Follow follow);
}
