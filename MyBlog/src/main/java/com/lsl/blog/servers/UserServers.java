package com.lsl.blog.servers;

import com.lsl.blog.po.User;

public interface UserServers {
    User CheckUser(String userName,String password);
}
