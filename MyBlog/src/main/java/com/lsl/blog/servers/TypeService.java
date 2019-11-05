package com.lsl.blog.servers;

import com.lsl.blog.po.Tag;
import com.lsl.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);
    void delectType(Long id);
    Type findTypeByName(String name);
    List<Type> ListTopType(Integer num);
    List<Type> listType();
}
