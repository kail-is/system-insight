package com.example.mapper;

import com.example.entity.PostAssignment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostAssignmentMapper {
    List<PostAssignment> selectByPostId(String postId);
    void update(PostAssignment postAssignment);
    void insert(PostAssignment postAssignment);
} 