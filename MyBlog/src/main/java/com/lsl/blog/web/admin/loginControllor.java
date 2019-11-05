package com.lsl.blog.web.admin;

import com.lsl.blog.po.User;
import com.lsl.blog.servers.UserServers;
import com.lsl.blog.utile.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Contended;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class loginControllor {
    @Autowired
    private UserServers servers;
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,@RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        User user  = servers.CheckUser(username,MD5Utils.code(password));
        if (user!=null){
            user.setPassword(null);
           session.setAttribute("user",user);
           return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String loginout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
