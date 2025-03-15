package com.hwk.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.List;

@Table(name = "comment")
public class Comment {
    @Id
    private Integer id;
    @Column(name = "userid")
    private Integer userid;
    @Column(name = "postid")
    private Integer postid;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
    private String time;
    @Column(name = "rootcommentid")
    private Integer rootCommentId;
    @Column(name = "touserid")
    private Integer toUserId;
    @Transient
    private Admin admin;
    @Transient
    private List<Comment> children;
    @Transient
    private Admin toUser;

    public Admin getToUser() {
        return toUser;
    }

    public void setToUser(Admin toUser) {
        this.toUser = toUser;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRootCommentId() {
        return rootCommentId;
    }

    public void setRootCommentId(Integer rootCommentId) {
        this.rootCommentId = rootCommentId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }
}
