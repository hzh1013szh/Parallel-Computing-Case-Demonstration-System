package com.hwk.springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "post")
public class Post {
    @Id
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "userid")
    private Integer userid;
    @Column(name = "content")
    private String content;
    @Column(name = "goods")
    private Integer goods;
    @Column(name = "comments")
    private Integer comments;
    @Column(name = "favorites")
    private Integer favorites;
    @Column(name = "status")
    private String status;
    @Column(name = "time")
    private String time;
    @Column(name = "description")
    @JsonProperty("description")
    private String description;
    @Column(name = "img")
    private String img;
    @Column(name = "role")
    private String role;
    @Column(name = "username")
    private String username;
    @Column(name = "postcategoryid")
    private Integer postCategoryId;
    @Transient
    private String postCategoryName;

    public String getPostCategoryName() {
        return postCategoryName;
    }

    public void setPostCategoryName(String postCategoryName) {
        this.postCategoryName = postCategoryName;
    }

    public Integer getPostCategoryId() {
        return postCategoryId;
    }

    public void setPostCategory(Integer postCategoryId) {
        this.postCategoryId = postCategoryId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSummary() {
        return description;
    }

    public void setSummary(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
