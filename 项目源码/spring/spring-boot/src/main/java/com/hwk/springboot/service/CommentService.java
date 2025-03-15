package com.hwk.springboot.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwk.springboot.dao.CommentDao;
import com.hwk.springboot.entity.Admin;
import com.hwk.springboot.entity.Comment;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private AdminService adminService;
    public void addComment(Comment comment) {
        if(comment.getContent() == null || comment.getContent().equals("")){
            throw new CustomException("评论内容不能为空");
        }
        comment.setTime(DateUtil.now());
        commentDao.addComment(comment);
    }

    public PageInfo<Comment> getRootCommentByPostid(Integer postid, Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Comment> list = commentDao.getRootCommentByPostid(postid);
        for(Comment comment:list){
            Admin admin = adminService.getById(comment.getUserid());
            comment.setAdmin(admin);
            comment.setChildren(getChildrenComment(comment.getId()));
        }
        return PageInfo.of(list);
    }
    public List<Comment> getChildrenComment(Integer id) {
        List<Comment> children = commentDao.getChildrenComment(id);
        for(Comment comment:children){
            Admin admin = adminService.getById(comment.getUserid());
            comment.setAdmin(admin);
            if(comment.getToUserId() != null){
                Admin toUser = adminService.getById(comment.getToUserId());
                comment.setToUser(toUser);
            }
        }
        return children;
    }
}
