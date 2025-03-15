package com.hwk.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Notice;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.service.NoticeService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
    @Resource
    private NoticeService noticeService;

    @GetMapping("/search")
    public Result getBySearch(Params params){
        PageInfo<Notice> info = noticeService.getBySearch(params);
        return Result.success(info);
    }
    @GetMapping
    public Result getNewestNotice(){
        List<Notice> list = noticeService.getNewestNotice();
        return Result.success(list);
    }
    @PostMapping
    public Result saveNotice(@RequestBody Notice notice){
        log.info(notice.getContent());
        if(notice.getId() == null){
            noticeService.addNotice(notice);
        }
        else{
            noticeService.updateNotice(notice);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result deleteNotice(@PathVariable Integer id){
        noticeService.deleteNotice(id);
        return Result.success();
    }
}
