package com.lsl.blog.web.admin;

import com.lsl.blog.po.Type;
import com.lsl.blog.servers.TypeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
public class typeController {
    @Autowired
    private TypeServiceImp typeService;
    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.ASC)
                        Pageable pageable, Model model){
       model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String save(Type type, RedirectAttributes attributes){
        Type typeByName = typeService.findTypeByName(type.getName());
        if (typeByName==null) {
            Type type1 = typeService.saveType(type);
            if (type1 == null) {
                attributes.addFlashAttribute("message","增加失败");
            } else {
                attributes.addFlashAttribute("message","增加成功");

            }
        }else {
            attributes.addFlashAttribute("message","重复增加");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delect(@PathVariable Long id){
        typeService.delectType(id);
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/input")
    public String input(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "/admin/types-edit";
    }
    @PostMapping("/edit")
    public String edit(Type type){
        System.out.println(type.toString());
        typeService.updateType(type.getId(),type);
        return "redirect:/admin/types";
    }
}
