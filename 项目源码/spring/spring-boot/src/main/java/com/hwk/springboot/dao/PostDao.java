package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Params;
import com.hwk.springboot.entity.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


@Repository
public interface PostDao extends Mapper<Post> {


    void addPost(@Param("post") Post post);

    Post getStaging(@Param("userid") Integer userid);

    void deleteStaging(@Param("userid") String userid);

    void updatePost(@Param("post") Post post);

    List<Post> getPostsByUserid(@Param("userid") Integer userid);

    List<Post> getPosts(@Param("params") Params param);

    Post getPostById(@Param("id") Integer id);

    void deletePost(@Param("id") Integer id);

    List<Post> getPostsByIdList(@Param("params") Params params);

    List<Map<String,Long>> getEchartsPie();
}
