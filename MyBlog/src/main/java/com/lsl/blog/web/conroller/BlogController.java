package com.lsl.blog.web.conroller;

import com.lsl.blog.po.Blog;
import com.lsl.blog.po.Tag;
import com.lsl.blog.po.Type;
import com.lsl.blog.servers.BlogServiceImp;
import com.lsl.blog.servers.TageServiceImp;
import com.lsl.blog.servers.TypeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class BlogController {
    @Autowired
    BlogServiceImp blogServiceImp;
    @Autowired
    TageServiceImp tageServiceImp;
    @Autowired
    TypeServiceImp typeServiceImp;
    @RequestMapping("/")
    public String index(@PageableDefault Pageable pageable, Model model){
        model.addAttribute("page",blogServiceImp.listBlog(pageable));
        model.addAttribute("types", typeServiceImp.ListTopType(6));
        model.addAttribute("tags",tageServiceImp.ListTop(6));
        model.addAttribute("recommendBlogs",blogServiceImp.listBlogIsCommend(2));
        List<Blog> blogs = blogServiceImp.listBlogIsCommend(2);
       // System.out.println(blogs);

        return "index";
    }
    /*http://localhost:8080/blog/36*/
    @GetMapping("/blog/{id}")
        public String getBlog(@PathVariable Long id,Model model){
            Blog blog = blogServiceImp.getBlogandConvent(id);
            model.addAttribute("blog",blog);
            return "blog";
        }
    /*http://localhost:8080/types/17*/
  /*  @GetMapping("/types/{id}")
    public String getTypes(@PathVariable Long id,Model model){
        Type type = typeServiceImp.getType(id);
        model.addAttribute("type",type);
        return "types";
    }*/
    @PostMapping("/search")
    public String search(@PageableDefault(size = 4,sort ={"updateTime"},direction = Sort.Direction.DESC)
                         Pageable pageable, @RequestParam String query,Model model){
        Page<Blog> blogs = blogServiceImp.listBlogBySearch(query, pageable);
        model.addAttribute("page",blogs);
        model.addAttribute("query",query);
        return "search";
    }
}
