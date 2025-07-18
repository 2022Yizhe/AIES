package com.neuswp.mappers;

import com.neuswp.entity.EasNotice;
import com.neuswp.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface EasNoticeMapper {
    int getCountByType(@Param("type") int type,@Param("searchKey") String searchKey);

    List<EasNotice> getNoticeListByType(@Param("type")int type, @Param("searchKey") String searchKey, @Param("pageUtil") PageUtil pageUtil);

    int addNotice(EasNotice easNotice);

    int updateNotice(EasNotice easNotice);

    int deleteNotice(EasNotice easNotice);

    int deleteNoticeList(List<Integer> list);

    int getCountByTypeAndEasNotice(@Param("type") int type,@Param("easNotice") EasNotice easNotice);

    List<EasNotice> getNoticeListByTypeAndEasNotice(@Param("type") int type,@Param("easNotice") EasNotice easNotice,@Param("pageUtil") PageUtil pageUtil);

    List<EasNotice> getNoticeById(Integer id);
}
