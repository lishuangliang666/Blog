package com.lsl.blog.servers;

import com.lsl.blog.po.Blog;
import com.lsl.blog.po.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    Blog saveBlog(Blog blog);
    Page<Blog> listBlog(Pageable pageable);
    List<Blog> liseBlogByTypeId(Long id);
    List<Blog> listBlogIsCommend(Integer size);
    Page<Blog>listBlogBySearch(String query,Pageable pageable);
    Blog getBlogandConvent(Long id);
    public Page<Blog> listBlog(Long tagId, Pageable pageable);

    Map<String,List<Blog>> listBlogByYear();
    public Long findAllBlog();
}
