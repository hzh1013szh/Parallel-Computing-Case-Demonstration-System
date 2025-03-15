package com.hwk.springboot.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwk.springboot.dao.NoticeDao;
import com.hwk.springboot.entity.Notice;
import com.hwk.springboot.entity.Params;
import com.hwk.springboot.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoticeService {
    @Resource
    private NoticeDao noticeDao;

    public PageInfo<Notice> getBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        // 接下来的查询会自动分页
        List<Notice> list = noticeDao.getBySearch(params);
        return PageInfo.of(list);
    }

    public void addNotice(Notice notice) {

        if(notice.getName() == null || notice.getName().equals("")){
            throw new CustomException("公告标题不能为空");
        }
        if(notice.getContent() == null || notice.getContent().equals("")){
            throw new CustomException("公告内容不能为空");
        }
        Notice new_notice = noticeDao.getBySearchName(notice.getName());
        if(new_notice != null){
            throw new CustomException("该公告已经存在");
        }
        notice.setTime(DateUtil.now());
        noticeDao.addNotice(notice);
    }

    public void updateNotice(Notice notice) {
        Notice new_notice = noticeDao.getBySearchName(notice.getName());
        if(new_notice != null && !new_notice.getId().equals(notice.getId())){
            throw new CustomException("该公告已经存在");
        }
        notice.setTime(DateUtil.now());
        noticeDao.updateNotice(notice);
    }

    public void deleteNotice(Integer id) {
        noticeDao.deleteNotice(id);
    }

    public List<Notice> getNewestNotice() {
        return noticeDao.getNewestNotice();
    }
}
