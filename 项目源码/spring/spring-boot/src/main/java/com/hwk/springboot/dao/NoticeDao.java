package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Notice;
import com.hwk.springboot.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface NoticeDao extends Mapper<Notice> {

    List<Notice> getBySearch(@Param("params") Params params);

    Notice getBySearchName(@Param("name") String name);

    void addNotice(@Param("notice") Notice notice);

    void updateNotice(@Param("notice") Notice notice);

    void deleteNotice(Integer id);

    List<Notice> getNewestNotice();
}
