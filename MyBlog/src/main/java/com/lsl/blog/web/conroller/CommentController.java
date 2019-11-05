package com.lsl.blog.web.conroller;

import com.lsl.blog.po.Comment;
import com.lsl.blog.servers.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    @Autowired
    CommentServiceImp commentServiceImp;
    @GetMapping("/comments/{blogId}")
    public String blogs(@PathVariable Long blogId , Model model){
        model.addAttribute("comments",commentServiceImp.findeCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String save(Comment comment){
       // System.out.println(comment.getBlog());
        commentServiceImp.saveComment(comment);
        Long id = comment.getBlog().getId();
        return "redirect:/comments/"+id;
    }

}
