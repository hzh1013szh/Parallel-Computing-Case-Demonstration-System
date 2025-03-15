package com.hwk.springboot.service;

import com.hwk.springboot.dao.FollowDao;
import com.hwk.springboot.entity.Follow;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Resource
    private FollowDao followDao;

    public void addFollow(Follow follow) {
        followDao.addFollow(follow);
    }

    public List<Integer> getMyFollow(Integer userid) {
        return followDao.getMyFollow(userid);
    }

    public List<Integer> getMyFan(Integer followid) {
        return followDao.getMyFan(followid);
    }

    public void deleteFollow(Follow follow) {
        followDao.deleteFollow(follow);
    }
}
