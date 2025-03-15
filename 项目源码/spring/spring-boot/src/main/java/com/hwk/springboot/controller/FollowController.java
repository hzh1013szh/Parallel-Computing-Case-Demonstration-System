package com.hwk.springboot.controller;

import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Follow;
import com.hwk.springboot.service.FollowService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
//    @Resource
//    private static final Logger log = LoggerFactory.getLogger(FollowController.class);
    @Resource
    private FollowService followService;
    @PostMapping()
    public Result addFollow(@RequestBody Follow follow) {
        followService.addFollow(follow);
        return Result.success(follow);
    }
    @GetMapping("/myfollow/{userid}")
    public Result getMyFollow(@PathVariable Integer userid) {
        List<Integer> list = followService.getMyFollow(userid);
        return Result.success(list);
    }
    @GetMapping("/myfan/{followid}")
    public Result getMyFan(@PathVariable Integer followid) {
        List<Integer> list = followService.getMyFan(followid);
        return Result.success(list);
    }
    @DeleteMapping()
    public Result deleteFollow(@RequestBody Follow follow) {
        followService.deleteFollow(follow);
        return Result.success();
    }
}
