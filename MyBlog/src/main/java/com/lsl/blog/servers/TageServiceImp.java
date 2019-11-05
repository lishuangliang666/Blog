package com.lsl.blog.servers;

import com.lsl.blog.NotFoundException;
import com.lsl.blog.dao.TagRespository;
import com.lsl.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TageServiceImp implements TageService{
    @Autowired
    private TagRespository tagRespository;


    public Page<Tag> ListTag(Pageable pageable) {
        return tagRespository.findAll(pageable);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRespository.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagRespository.deleteById(id);
    }

    @Override
    public void update(Long id, Tag tag) {
        Tag tag1 = tagRespository.findById(id).get();
        if (tag1==null){
            throw new NotFoundException("类型不匹配");
        }
        BeanUtils.copyProperties(tag,tag1);
        save(tag1);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRespository.findById(id).get();
    }

    @Override
    public List<Tag> ListTag() {
        return tagRespository.findAll();
    }

    @Override
    public List<Tag> ListTag(String ids) {

        return tagRespository.findAllById(tolist(ids));
    }

    @Override
    public List<Long> tolist(String s) {
        List<Long> list = new ArrayList<>();
        if(!"".equals(s)&&s!=null){
            String[] ids = s.split(",");
            for (int i=0;i<ids.length;i++){
                list.add(new Long(ids[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> ListTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return tagRespository.findTop(pageable);
    }


}
