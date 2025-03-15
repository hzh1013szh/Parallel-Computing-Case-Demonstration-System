package com.hwk.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.service.AdminService;
import com.hwk.springboot.service.CommentService;
import com.hwk.springboot.entity.Comment;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private AdminService adminService;
    @PostMapping()
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return Result.success();
    }
    @GetMapping("/search/{postid}")
    public Result getRootCommentByPostid(@PathVariable Integer postid, Params params){
        PageInfo<Comment> info = commentService.getRootCommentByPostid(postid,params);
        return Result.success(info);
    }

}
