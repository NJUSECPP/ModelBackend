package org.njuse.cpp.dao;

import org.apache.ibatis.annotations.*;
import org.njuse.cpp.dao.po.ChatSessionPO;

import java.util.List;

@Mapper
public interface ChatSessionMapper {
    @Insert("INSERT INTO chat_session(session_id,type,content,user_name,user_id) values (#{sessionId}," +
            "#{type},#{content},#{userName},#{userId})")
    void insertChatMessage(ChatSessionPO chatSession);

    @Select("SELECT * from chat_session where session_id=#{sessionId} order by id ASC")
    List<ChatSessionPO> getChatMessageBySession(@Param("sessionId")String sessionId);

    @Delete("DELETE from chat_session where session_id=#{sessionId}")
    void deleteBySession(String sessionId);



}
