package com.lsl.blog.servers;

import com.lsl.blog.dao.CommentRespository;
import com.lsl.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    CommentRespository commentRespository;
    @Override
    public List<Comment> findeCommentByBlogId(Long id) {
        List<Comment> allByBlogId = commentRespository.findByBlogIdAndParentCommentNull(id);
        return eachComment(allByBlogId) ;
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long prentId = comment.getParentComment().getId();
        if (prentId!=-1){
            comment.setParentComment(commentRespository.findById(prentId).get());
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRespository.save(comment);
    }
    /*
     * 循环每个顶级的评论节点
     * @param comments
     * @return
             */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
