package com.lsl.blog.dao;

import com.lsl.blog.po.Tag;
import com.lsl.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRespository extends JpaRepository<Type,Long> {
    Type findTypeByName(String name);


    @Query("select t from Type t")
    List<Type> findTop6(Pageable pageable);
}
