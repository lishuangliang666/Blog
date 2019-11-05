package com.lsl.blog.servers;

import com.lsl.blog.NotFoundException;
import com.lsl.blog.dao.BlogRespository;
import com.lsl.blog.po.Blog;
import com.lsl.blog.po.BlogQuery;
import com.lsl.blog.po.Type;
import com.lsl.blog.utile.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImp implements BlogService {

    @Autowired
    BlogRespository blogRespository;
    @Override
    public Blog getBlog(Long id) {
        return blogRespository.findById(id).get();
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRespository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cr) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cr.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId()!= null) {
                    predicates.add(cr.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommen()) {
                    predicates.add(cr.equal(root.<Boolean>get("recommend"), blog.isRecommen()));
                }
                if (blog.getTagId()!=null) {
                    predicates.add(cr.equal(root.<Type>get("type").get("id"), blog.getTagId()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);


    }
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRespository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String,List<Blog>> listBlogByYear() {
        Map <String,List<Blog>> blogs = new HashMap<>();
        List<String> year = blogRespository.findYear();
        for (String s:year){
            List<Blog> byYear = blogRespository.findByYear(s);
            blogs.put("s",byYear);

        }
        return blogs;
    }


    /* public Page<Blog> ListBlog(Pageable pageable, BlogQuery blog) {
         return blogRespository.findAll(new Specification<Blog>() {
             @Override
             public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cr) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                     predicates.add(cr.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                 }
                 if (blog.getTypeId()!= null) {
                     predicates.add(cr.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                 }
                 if (blog.isRecommen()) {
                     predicates.add(cr.equal(root.<Boolean>get("recommend"), blog.isRecommen()));
                 }
                 query.where(predicates.toArray(new Predicate[predicates.size()]));
                 return null;
             }
         },pageable);
     }
 */
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRespository.findById(id).get();
        if(blog1==null){
            throw new NotFoundException("更改失败");
        }
        BeanUtils.copyProperties(blog,blog1);
        blog1.setUpdateTime(new Date());
       return blogRespository.save(blog1);
    }

    @Override
    public void deleteBlog(Long id) {
            blogRespository.deleteById(id);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        blog.setUpdateTime(new Date());
            blog.setCreateTime(new Date());
            blog.setViews(0);
           return blogRespository.save(blog);

    }
    public Page<Blog> listBlog(Pageable pageable){
        return blogRespository.findAll(pageable);
    }

    @Override
    public List<Blog> liseBlogByTypeId(Long id) {

        return  null;//blogRespository.findBlogByTypeId(id);
    }

    @Override
    public List<Blog> listBlogIsCommend(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return blogRespository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlogBySearch(String query, Pageable pageable) {
        return blogRespository.listBlogBySearch(query,pageable);
    }

    @Override
    public Blog getBlogandConvent(Long id) {
        Blog b = new Blog();
        Blog blog = blogRespository.getOne(id);
        if (blog==null){
            throw new NotFoundException("未找到博客");
        }
        int i = blogRespository.updateViews(id);
        blog.setViews(i);
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }


    public Long findAllBlog(){

        return blogRespository.count();
    }

}
