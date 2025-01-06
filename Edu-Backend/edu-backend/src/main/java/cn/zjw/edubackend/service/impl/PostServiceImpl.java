package cn.zjw.edubackend.service.impl;


import cn.zjw.edubackend.mapper.PostMapper;
import cn.zjw.edubackend.pojo.bbs.Post;
import cn.zjw.edubackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public void createPost(Post post) {
        postMapper.createPost(post);
    }

    @Override
    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }

    @Override
    public void updatePostbyTitle(Post post) {
        postMapper.updatePostbyTitle(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postMapper.findById((long) Math.toIntExact(id));
    }

    @Override
    public Post getPostByTitle(String title) {
        return postMapper.findByTitle(title);
    }

    @Override
    public List<Post> getAllPosts() {
        return postMapper.findAll();
    }
}
