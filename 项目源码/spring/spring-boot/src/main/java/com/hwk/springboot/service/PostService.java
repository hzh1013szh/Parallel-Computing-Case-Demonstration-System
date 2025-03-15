package com.hwk.springboot.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwk.springboot.dao.PostDao;
import com.hwk.springboot.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.entity.Post;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    @Resource
    private PostDao postDao;
    public void addPost(Post post) {
        if(post.getTitle() == null || post.getTitle().equals("")){
            throw new CustomException("请输入标题");
        }
        if(post.getContent() == null || post.getContent().equals("")){
            throw new CustomException("请输入内容");
        }
        if(post.getDescription() == null || post.getDescription().equals("")){
            throw new CustomException("请输入描述");
        }
        if(post.getPostCategoryId() == null){
            throw new CustomException("请选择分类");
        }
        post.setGoods(0);
        post.setComments(0);
        post.setFavorites(0);
        post.setTime(DateUtil.now());
        postDao.addPost(post);
    }

    public Post getStaging(Integer userid) {
        Post post = postDao.getStaging(userid);
        if(post == null){
            post = new Post();
            post.setContent("");
        }
        return post;
    }

    public void deleteStaging(String userid) {
        postDao.deleteStaging(userid);
    }

    public void updatePost(Post post) {
        post.setTime(DateUtil.now());
        postDao.updatePost(post);
    }

    public PageInfo<Post> getPostsByUserid(Integer userid,Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Post> list = postDao.getPostsByUserid(userid);
        return PageInfo.of(list);
    }

    public PageInfo<Post> getPosts(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        // 接下来的查询会自动分页
        List<Post> list = postDao.getPosts(params);
        return PageInfo.of(list);
    }

    public Post getPostById(Integer id) {
        return postDao.getPostById(id);
    }

    public void deletePost(Integer id) {
        postDao.deletePost(id);
    }

    public PageInfo<Post> getPostsByIdlist(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Post> list = postDao.getPostsByIdList(params);
        return PageInfo.of(list);
    }

    public List<Map<String,Long>> getEchartsPie() {
        return postDao.getEchartsPie();
    }
}
