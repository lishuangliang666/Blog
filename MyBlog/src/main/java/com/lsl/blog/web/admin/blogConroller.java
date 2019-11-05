package com.lsl.blog.web.admin;

import com.lsl.blog.NotFoundException;
import com.lsl.blog.po.Blog;
import com.lsl.blog.po.BlogQuery;
import com.lsl.blog.po.User;
import com.lsl.blog.servers.BlogService;
import com.lsl.blog.servers.BlogServiceImp;
import com.lsl.blog.servers.TageServiceImp;
import com.lsl.blog.servers.TypeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class blogConroller {
    private final String INPUT = "admin/blogs-input";
    private final String EDIT = "admin/blogs-edit";
    private final String LIST = "admin/blogs";
    private final String REDIRECT = "redirect:/admin/blogs";
    @Autowired
    private TageServiceImp tageServiceImp;
    @Autowired
    private BlogServiceImp blogServiceImp;
    @Autowired
    TypeServiceImp typeServiceImp;
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = {"updateTime"}) Pageable pageable, Model model, BlogQuery blog){
       // model.addAttribute("types",typeServiceImp.listType());
        model.addAttribute("page",blogServiceImp.listBlog(pageable,blog));
    return LIST;
}

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3,sort = {"updateTime"}) Pageable pageable, Model model, BlogQuery blog){
        model.addAttribute("page",blogServiceImp.listBlog(pageable,blog));
        System.out.println(blogServiceImp.listBlog(pageable,blog));
        return "admin/blogs :: blogList";

    }
        @GetMapping("/blogs/input")
        public String input(Model model){
        model.addAttribute("types",typeServiceImp.listType());
        model.addAttribute("tags",tageServiceImp.ListTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
        }

        @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
            blog.setUser((User) session.getAttribute("user"));
            blog.setType(typeServiceImp.getType(blog.getType().getId()));
            blog.setTags(tageServiceImp.ListTag(blog.getTagIds()));
            Blog blog1 = blogServiceImp.saveBlog(blog);

            if (blog1 == null) {
            attributes.addFlashAttribute("message","增加失败");
            }else {
                attributes.addFlashAttribute("message","增加成功");
            }
            return REDIRECT;
    }
    /*http://localhost:8080/admin/blogs/35/delete*/
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id){
        blogServiceImp.deleteBlog(id);
        return REDIRECT;
    }
    @GetMapping("/blogs/{id}/input")
    public String input(@PathVariable Long id,Model model){
        //blogServiceImp.deleteBlog(id);
        Blog blog=blogServiceImp.getBlog(id);
        model.addAttribute("blog",blog);
        model.addAttribute("id",id);
        return INPUT;
    }

    @PostMapping("/blogs/edit")
    public String edit(Blog blog){
        blogServiceImp.updateBlog(blog.getId(),blog);
        return REDIRECT;
    }

}
