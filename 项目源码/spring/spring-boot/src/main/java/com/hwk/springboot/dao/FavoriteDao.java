package com.hwk.springboot.dao;

import com.hwk.springboot.entity.Favorite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface FavoriteDao extends Mapper<Favorite> {

    void addFavorite(@Param("favorite") Favorite favorite);

    void deleteFavorite(@Param("favorite") Favorite favorite);

    List<Integer> getFavoriteUser(@Param("favoriteid") Integer favoriteid);

    List<Integer> getFavoritePost(@Param("userid") Integer userid);
}
