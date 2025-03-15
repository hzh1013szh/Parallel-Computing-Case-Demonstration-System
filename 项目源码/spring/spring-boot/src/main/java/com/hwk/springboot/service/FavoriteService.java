package com.hwk.springboot.service;

import com.hwk.springboot.dao.FavoriteDao;
import com.hwk.springboot.entity.Favorite;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Resource
    private FavoriteDao favoriteDao;

    public void addFavorite(Favorite favorite) {
        favoriteDao.addFavorite(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteDao.deleteFavorite(favorite);
    }

    public List<Integer> getFavoriteUser(Integer favoriteid) {
        return favoriteDao.getFavoriteUser(favoriteid);
    }

    public List<Integer> getFavoritePost(Integer userid) {
        return favoriteDao.getFavoritePost(userid);
    }
}
