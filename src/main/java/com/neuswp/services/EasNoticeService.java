package com.neuswp.services;

import com.neuswp.entity.EasNotice;
import com.neuswp.utils.PageUtil;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/28 12:09
 */
public interface EasNoticeService {
    int getCountByType(int type,String searchKey) throws Exception;

    List<EasNotice> getNoticeListByType(int type, String searchKey, PageUtil pageUtil) throws Exception;

    int addNotice(EasNotice easNotice) throws Exception;

    int updateNotice(EasNotice easNotice) throws Exception;

    int deleteNotice(EasNotice easNotice);

    int deleteNoticeList(List<Integer> list);

    int getCountByTypeAndEasNotice(int type, EasNotice easNotice);

    List<EasNotice> getNoticeListByTypeAndEasNotice(int type, EasNotice easNotice, PageUtil pageUtil);

    List<EasNotice> getNoticeById(Integer id);
}
