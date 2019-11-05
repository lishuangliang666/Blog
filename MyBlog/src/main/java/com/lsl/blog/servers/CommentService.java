package com.lsl.blog.servers;

import com.lsl.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findeCommentByBlogId(Long id);
    Comment saveComment(Comment comment);
}
