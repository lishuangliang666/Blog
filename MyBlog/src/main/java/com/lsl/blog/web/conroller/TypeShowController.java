package com.lsl.blog.web.conroller;

import com.lsl.blog.po.Blog;
import com.lsl.blog.po.BlogQuery;
import com.lsl.blog.po.Type;
import com.lsl.blog.servers.BlogServiceImp;
import com.lsl.blog.servers.TypeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {
@Autowired
    TypeServiceImp typeServiceImp;
@Autowired
    BlogServiceImp blogServiceImp;
    /*http://localhost:8080/types/-1*/
@GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 4,sort ={"updateTime"},direction = Sort.Direction.DESC)
        
                                Pageable pageable,@PathVariable Long id, Model model){
    Long id1;
    List<Type> types =typeServiceImp.ListTopType(100);
    if (id==-1){
         id1 = types.get(0).getId();
    }else {
        id1= id;
    }
    BlogQuery blogQuery = new BlogQuery();
    blogQuery.setTypeId(id1);
    Page<Blog> blogs = blogServiceImp.listBlog(pageable, blogQuery);
    model.addAttribute("types",types);
    model.addAttribute("page",blogs);
    model.addAttribute("activeTypeId",id1 );
    return "types";

}
}
