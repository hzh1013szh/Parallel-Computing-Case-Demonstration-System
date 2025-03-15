package com.hwk.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.entity.Post;
import com.hwk.springboot.service.PostService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    @Resource
    private PostService postService;
    @PostMapping
    public Result savePost(@RequestBody Post post) {
        log.info(post.getDescription());
        if(post.getId() == null){
            postService.addPost(post);
        }
        else{
            postService.updatePost(post);
        }
        return Result.success();
    }
    @GetMapping("/staging")
    public Result getStaging(@RequestParam("userid") Integer userid) {
        Post post = postService.getStaging(userid);
        if(post.getContent() == null){
            post.setContent("");
        }
        return Result.success(post);
    }
    @GetMapping()
    public Result getPosts(Params params){
        PageInfo<Post> info = postService.getPosts(params);
        return Result.success(info);
    }
    @GetMapping("/{id}")
    public Result getPostById(@PathVariable("id") Integer id){
        Post post = postService.getPostById(id);
        return Result.success(post);
    }
    @PostMapping("/idlist")
    public Result getPostsByIdList(@RequestBody Params params){
        PageInfo<Post> info = postService.getPostsByIdlist(params);
        return Result.success(info);
    }
    @GetMapping("/user/{id}")
    public Result getPostsByUserid(@PathVariable("id") Integer userid,Params params){
        PageInfo<Post> info = postService.getPostsByUserid(userid,params);
        return Result.success(info);
    }
    @DeleteMapping("/{id}")
    public Result deletePost(@PathVariable("id") Integer id){
        postService.deletePost(id);
        return Result.success();
    }
    @GetMapping("/echarts/pie")
    public Result getEchartsPie(){
        List<Map<String,Long>> list = postService.getEchartsPie();
        return Result.success(list);
    }
}
