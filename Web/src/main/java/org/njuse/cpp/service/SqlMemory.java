package org.njuse.cpp.service;

import org.njuse.cpp.dao.ChatSessionMapper;
import org.njuse.cpp.dao.po.ChatSessionPO;
import org.njuse.cpp.memory.AiMessage;
import org.njuse.cpp.memory.BaseChatMessageHistory;
import org.njuse.cpp.memory.BaseMessage;
import org.njuse.cpp.memory.HumanMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SqlMemory extends BaseChatMessageHistory {
    private String sessionId;

    private String userName;

    private Integer userId;

    ChatSessionMapper chatSessionMapper;

    public SqlMemory(String sessionId,String userName,Integer userId, ChatSessionMapper chatSessionMapper){
        this.sessionId=sessionId;
        this.userName=userName;
        this.userId=userId;
        this.chatSessionMapper = chatSessionMapper;
    }

    @Override
    public void clear() {
        chatSessionMapper.deleteBySession(this.sessionId);
    }

    @Override
    public List<BaseMessage> loadMessages() {
        List<ChatSessionPO> chatSessionPOS=chatSessionMapper.getChatMessageBySession(sessionId);
        List<BaseMessage> baseMessages=chatSessionPOS.stream().map(this::chatSession2BaseMessage)
                .filter((baseMessage -> baseMessage!=null)).collect(Collectors.toList());
        return baseMessages;
    }

    @Override
    public void addMessage(BaseMessage message) {
        if(message.type().equals("ai")){
            ChatSessionPO chatSessionPO=new ChatSessionPO();
            chatSessionPO.setSessionId(this.sessionId);
            chatSessionPO.setType("ai");
            chatSessionPO.setUserName(this.userName);
            chatSessionPO.setUserId(this.userId);
            chatSessionPO.setContent(message.getContent());
            chatSessionMapper.insertChatMessage(chatSessionPO);
        }
        else if(message.type().equals("human")){
            ChatSessionPO chatSessionPO=new ChatSessionPO();
            chatSessionPO.setSessionId(this.sessionId);
            chatSessionPO.setType("human");
            chatSessionPO.setUserName(this.userName);
            chatSessionPO.setUserId(this.userId);
            chatSessionPO.setContent(message.getContent());
            chatSessionMapper.insertChatMessage(chatSessionPO);
        }
    }

    private BaseMessage chatSession2BaseMessage(ChatSessionPO chatSessionPO){
        if ("human".equals(chatSessionPO.getType())){
            Map<String,Object> extendParams=new HashMap<>();
            HumanMessage humanMessage=new HumanMessage(chatSessionPO.getContent(),extendParams);
            return humanMessage;
        }
        else if("ai".equals(chatSessionPO.getType())){
            Map<String,Object> extendParams=new HashMap<>();
            AiMessage aiMessage=new AiMessage(chatSessionPO.getContent(),extendParams);
            return aiMessage;
        }
        else{
            return null;
        }
    }
}
