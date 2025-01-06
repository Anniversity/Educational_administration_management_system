package cn.zjw.edubackend.service;

import cn.zjw.edubackend.pojo.bbs.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostService {

    public void createPost(Post post);

    public void updatePost(Post post);

    public void updatePostbyTitle(Post post);

    public Post getPostById(Long id) ;

    public Post getPostByTitle(String title) ;

    public List<Post> getAllPosts() ;
}
