package com.lsl.blog.web.admin;

import com.lsl.blog.po.Tag;
import com.lsl.blog.servers.TageService;
import com.lsl.blog.servers.TageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class tagController {
    @Autowired
    private TageServiceImp tageServiceImp;
    @GetMapping("/tags")
    public String tag(@PageableDefault Pageable pageable, Model model){

        model.addAttribute("page",tageServiceImp.ListTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(){
        return "admin/tags-input";
    }
    @PostMapping("/tagsAdd")
    public String addTags(Tag tag){
        tageServiceImp.save(tag);
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id){
        tageServiceImp.delete(id);
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/input")
    public String input(@PathVariable Long id,Model model){
        Tag tag = tageServiceImp.getTag(id);
        model.addAttribute(tag);
        return "admin/tags-edit";
    }
    @PostMapping ("/tagsedit")
    public String tagsedit(Tag tag,String name){
        System.out.println(tag);
        tageServiceImp.update(tag.getId(),tag);
        return "redirect:/admin/tags";

    }
}
