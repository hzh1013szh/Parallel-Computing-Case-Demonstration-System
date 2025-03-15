package com.hwk.springboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwk.springboot.dao.PostCategoryDao;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.entity.PostCategory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCategoryService {
    @Resource
    private PostCategoryDao postCategoryDao;

    public void addPostCategory(PostCategory postCategory) {
        postCategoryDao.addPostCategory(postCategory);
    }

    public void updatePostCategory(PostCategory postCategory) {
        postCategoryDao.updatePostCategory(postCategory);
    }

    public PageInfo<PostCategory> getBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<PostCategory> list = postCategoryDao.getBySearch(params);
        return PageInfo.of(list);
    }

    public List<PostCategory> getAll() {
        return postCategoryDao.getAll();
    }
}
