package com.lsl.blog.web.conroller;

import com.lsl.blog.po.Blog;
import com.lsl.blog.po.BlogQuery;
import com.lsl.blog.po.Tag;
import com.lsl.blog.po.Type;
import com.lsl.blog.servers.BlogServiceImp;
import com.lsl.blog.servers.TageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
@Autowired
    TageServiceImp tageServiceImp;
@Autowired
    BlogServiceImp blogServiceImp;
    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 4,sort ={"updateTime"},direction = Sort.Direction.DESC)

                                Pageable pageable, @PathVariable Long id, Model model) {
        Long id1;
        List<Tag> tags = tageServiceImp.ListTop(100);
        if (id==-1){
             id1 = tags.get(0).getId();
        }else {
            id1=id;
        }
        /*BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTagId(id1);*/
        Page<Blog> blogs = blogServiceImp.listBlog(id1,pageable);
        model.addAttribute("page",blogs);
        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId",id1);
        return "tags";
    }
}
