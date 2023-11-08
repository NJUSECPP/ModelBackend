package org.njuse.cpp.dao.po;

import lombok.Data;

@Data
public class ChatSessionPO {
    private Long id;
    private String sessionId;
    private String type;
    private String content;
    private String userName;
    private int userId;
}
