package com.lsl.blog.servers;

import com.lsl.blog.dao.UserRespository;
import com.lsl.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

@Service
public class UserserversImp implements UserServers {
    @Autowired
    private UserRespository userRespository;
    @Override
    public User CheckUser(String userName, String password) {
        User  user = userRespository.findByUsernameAndPassword(userName,password);
        return user;
    }
}
