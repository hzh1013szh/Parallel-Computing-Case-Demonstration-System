package com.hwk.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.entity.PostCategory;
import com.hwk.springboot.service.PostCategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/category")
public class PostCategotyController {
    @Resource
    private PostCategoryService postCategoryService;
    @PostMapping()
    public Result savePostCategory(@RequestBody PostCategory postCategory) {
        if(postCategory.getId() == null){
            postCategoryService.addPostCategory(postCategory);
        }
        else{
            postCategoryService.updatePostCategory(postCategory);
        }
        return Result.success();
    }
    @GetMapping()
    public Result getAll() {
        List<PostCategory> list = postCategoryService.getAll();
        return Result.success(list);
    }
    @GetMapping("/search")
    public Result getBySearch(Params params) {
        PageInfo<PostCategory> info = postCategoryService.getBySearch(params);
        return Result.success(info);
    }
}
