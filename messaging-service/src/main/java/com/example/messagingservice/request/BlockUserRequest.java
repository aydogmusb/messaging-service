package com.example.messagingservice.request;

public class BlockUserRequest {
    private String username;
    private Long blockedUserId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(Long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }
}
