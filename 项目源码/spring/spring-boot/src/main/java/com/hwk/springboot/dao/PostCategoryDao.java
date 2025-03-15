package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Params;
import com.hwk.springboot.entity.PostCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface PostCategoryDao extends Mapper<PostCategory> {

    void addPostCategory(@Param("postCategory") PostCategory postCategory);

    void updatePostCategory(@Param("postCategory") PostCategory postCategory);

    List<PostCategory> getBySearch(@Param("params") Params params);
    @Select("select * from postcategory")
    List<PostCategory> getAll();
}
