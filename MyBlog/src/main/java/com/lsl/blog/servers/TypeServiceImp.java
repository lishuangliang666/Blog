package com.lsl.blog.servers;

import com.lsl.blog.NotFoundException;
import com.lsl.blog.dao.TypeRespository;
import com.lsl.blog.po.Tag;
import com.lsl.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImp implements TypeService {
    @Autowired
    TypeRespository typeRespository;
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRespository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {

        return typeRespository.getOne(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRespository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {

        Type t = typeRespository.findById(id).get();
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRespository.save(t);
    }
    @Transactional
    @Override
    public void delectType(Long id) {
            typeRespository.deleteById(id);
    }

    @Override
    public Type findTypeByName(String name) {
        return typeRespository.findTypeByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeRespository.findAll();
    }
    @Override
    public List<Type> ListTopType(Integer num) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,num,sort);
        return typeRespository.findTop6(pageable);
    }

}
