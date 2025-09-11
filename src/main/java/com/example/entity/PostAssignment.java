package com.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostAssignment {
    private String postId;
    private int sequence;
    private String name;
    private String isManager;
    private String isDeleted;
} 