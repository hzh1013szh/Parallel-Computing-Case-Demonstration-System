package com.hwk.springboot.controller;

import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Good;
import com.hwk.springboot.service.GoodService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodController {
    private static final Logger log = LoggerFactory.getLogger(GoodController.class);
    @Resource
    private GoodService goodService;
    @PostMapping()
    public Result addGood(@RequestBody Good good){
        log.info(good.getGoodid().toString());
        goodService.addGood(good);
        return Result.success();
    }
    @GetMapping("/{goodid}")
    public Result getGoods(@PathVariable Integer goodid){
        List<Integer> list = goodService.getGoods(goodid);
        log.info(list.toString());
        return Result.success(list);
    }
    @DeleteMapping()
    public Result deleteGood(@RequestBody Good good){
        goodService.deleteGood(good);
        return Result.success();
    }
}
