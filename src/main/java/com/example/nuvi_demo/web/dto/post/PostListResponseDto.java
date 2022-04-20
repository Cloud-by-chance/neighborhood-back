package com.example.nuvi_demo.web.dto.post;

import com.example.nuvi_demo.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long post_id;
    private String post_name;
    private String user_id;
    private LocalDateTime update_dt;

    public PostListResponseDto(Post entity) {
        this.post_id = entity.getPost_id();
        this.post_name = entity.getPost_name();
        this.user_id = entity.getUser_id();
        this.update_dt = entity.getUpdate_dt();
    }
}