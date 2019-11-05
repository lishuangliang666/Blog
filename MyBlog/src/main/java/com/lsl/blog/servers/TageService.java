package com.lsl.blog.servers;

import com.lsl.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TageService {
    public Page<Tag> ListTag(Pageable pageable);
    Tag save(Tag tag);

    void delete(Long id);

    void update(Long id, Tag tag);

    Tag getTag(Long id);

    List<Tag> ListTag();
    List<Tag> ListTag(String ids);
    List<Long> tolist(String s);
    List<Tag> ListTop(Integer size);
}
