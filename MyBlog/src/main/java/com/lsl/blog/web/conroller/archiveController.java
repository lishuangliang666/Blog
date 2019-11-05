package com.lsl.blog.web.conroller;

import com.lsl.blog.po.Blog;
import com.lsl.blog.servers.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class archiveController {

    @Autowired
    BlogServiceImp blogServiceImp;

    @GetMapping("/archives")
    public String archive(Model model){
        Map<String, List<Blog>> stringListMap = blogServiceImp.listBlogByYear();
        Long allBlog = blogServiceImp.findAllBlog();
        model.addAttribute("archiveMap",stringListMap);
        model.addAttribute("blogCount",allBlog);
        return "archives";
    }
}
