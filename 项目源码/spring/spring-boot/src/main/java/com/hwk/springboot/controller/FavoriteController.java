package com.hwk.springboot.controller;

import com.hwk.springboot.common.Result;
import com.hwk.springboot.entity.Favorite;
import com.hwk.springboot.service.FavoriteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private static final Logger log = LoggerFactory.getLogger(FavoriteController.class);
    @Resource
    private FavoriteService favoriteService;
    @PostMapping()
    private Result addFavorite(@RequestBody Favorite favorite){
        log.info(favorite.getFavoriteid().toString());
        favoriteService.addFavorite(favorite);
        return Result.success();
    }
    @DeleteMapping()
    private Result deleteFavorite(@RequestBody Favorite favorite){
        favoriteService.deleteFavorite(favorite);
        return Result.success();
    }
    @GetMapping("/user/{favoriteid}")
    private Result getFavoriteUser(@PathVariable Integer favoriteid){
        List<Integer> favorite = favoriteService.getFavoriteUser(favoriteid);
        return Result.success(favorite);
    }
    @GetMapping("/post/{userid}")
    private Result getFavoritePost(@PathVariable Integer userid){
        List<Integer> favorite = favoriteService.getFavoritePost(userid);
        return Result.success(favorite);
    }
}
