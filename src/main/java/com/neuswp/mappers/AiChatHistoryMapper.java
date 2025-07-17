package com.neuswp.mappers;


import com.neuswp.entity.AiChatHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AiChatHistoryMapper {

    @Insert("insert into ai_chat_history(user_id,question,reply) values(#{userId},#{question},#{reply})")
    void save(@Param("userId") Integer userId, @Param("question") String question, @Param("reply") String reply);

    @Select("select * from ai_chat_history where user_id=#{userId} ORDER BY id DESC")
    List<AiChatHistory> getHistoryByUserId(@Param("userId") Integer userId);
}
