package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface CommentDao extends Mapper<Comment> {


    void addComment(@Param("comment") Comment comment);

    List<Comment> getRootCommentByPostid(@Param("postid") Integer postid);

    List<Comment> getChildrenComment(@Param("id") Integer id);
}
